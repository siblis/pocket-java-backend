package ru.geekbrains.pocket.backend.controller.rest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import ru.geekbrains.pocket.backend.domain.db.Privilege;
import ru.geekbrains.pocket.backend.domain.db.Role;
import ru.geekbrains.pocket.backend.domain.db.User;
import ru.geekbrains.pocket.backend.domain.db.VerificationToken;
import ru.geekbrains.pocket.backend.domain.pub.UserPub;
import ru.geekbrains.pocket.backend.exception.UserAlreadyExistException;
import ru.geekbrains.pocket.backend.response.GenericResponse;
import ru.geekbrains.pocket.backend.security.registration.OnRegistrationCompleteEvent;
import ru.geekbrains.pocket.backend.service.RoleService;
import ru.geekbrains.pocket.backend.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
//@Validated
@RestController
@RequestMapping("/v1/auth")
public class AuthRestController {
    private final static String ROLE_USER = "ROLE_USER";

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    ApplicationEventPublisher eventPublisher;
    @Autowired
    private MessageSource messages;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private Environment env;


    @PostMapping(path = "/login", consumes = "application/json")
    //@PostMapping(path = "/login", produces = "application/json;charset=UTF-8")
    public ResponseEntity login(@RequestBody LoginRequest loginRequest) {
        //TODO validate
        User user = userService.getUserByEmail(loginRequest.getEmail());
        if (user == null) {
            log.debug("User not exists.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        String token = ""; //TODO token

        return new ResponseEntity<>(new RegistrationResponse(token, new UserPub(user)), HttpStatus.OK);
    }

    @PostMapping(path = "/login3", consumes = "application/json")
    @ResponseBody
    public GenericResponse login3(HttpServletRequest request, @RequestParam("token") String existingToken) {
        VerificationToken newToken = userService.generateNewVerificationToken(existingToken);

        User user = userService.getUserByToken(newToken.getToken());
        String appUrl =
                "http://" + request.getServerName() +
                        ":" + request.getServerPort() +
                        request.getContextPath();
        SimpleMailMessage email =
                constructResendVerificationTokenEmail(appUrl, request.getLocale(), newToken, user);
        mailSender.send(email);

        return new GenericResponse(
                messages.getMessage("message.resendToken", null, request.getLocale()));
    }

    //Example
    @PostMapping("/login2")
    public ResponseEntity<?> login2(@RequestParam(name = "email") String email,
                                    @RequestParam(name = "password") String password) {
        User user = userService.getUserByEmail(email);
        if (user == null) {
            log.debug("User not exists.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        String token = "";

        return new ResponseEntity<>(new RegistrationResponse(token, new UserPub(user)), HttpStatus.OK);
    }

    @PostMapping(path = "/registration", consumes = "application/json")
    public ResponseEntity<?> registration(@Valid @RequestBody RegistrationRequest registrationRequest,
                                          final HttpServletRequest request,
                                          BindingResult result, WebRequest webRequest, Errors errors) {
        //TODO validate
        //If error, just return a 400 bad request, along with the error message
        if (result.hasErrors()) {
            final Map<String, Object> response = new HashMap<>();
            response.put("message", "Your request contains errors");
            response.put("errors", result.getAllErrors()
                    .stream()
                    .map(x -> String.format("%s : %s", x.getCode(), x.getDefaultMessage()))
                    .collect(Collectors.toList()));
            //return ResponseEntity.badRequest().body(response);
        }

        log.debug("Processing registration form for: " + registrationRequest);

        User registered;
        try {
            registered = userService.registerNewUserAccount(registrationRequest.getEmail(),
                    registrationRequest.getPassword(),
                    registrationRequest.getName());
        } catch (UserAlreadyExistException e) {
            log.debug("Email already exists.");
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        log.debug("Successfully created user: " + registered.getEmail());

        String token = ""; //TODO token

        //отправка электронного письма с запросом подтверждения email
//        try {
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent
                (registered, request.getLocale(), getAppUrl(request)));
//        } catch (Exception me) {
//            return new ResponseEntity<>(HttpStatus.CONFLICT);
//        }

        return new ResponseEntity<>(new RegistrationResponse(token, new UserPub(registered)), HttpStatus.CREATED);

    }

    //https://www.baeldung.com/registration-verify-user-by-email
    @GetMapping("/registrationConfirm")
    public ResponseEntity<?> confirmRegistration(final HttpServletRequest request, @RequestParam("token") final String token)
            throws UnsupportedEncodingException {
        Locale locale = request.getLocale();
        final String result = userService.validateVerificationToken(token);
        if (result.equals("valid")) {
            final User user = userService.getUserByToken(token);
            // if (user.isUsing2FA()) {
            // model.addAttribute("qr", userService.generateQRUrl(user));
            // return "redirect:/qrcode.html?lang=" + locale.getLanguage();
            // }
            authWithoutPassword(user);
            return new ResponseEntity<>(HttpStatus.OK);
        }

        //Пользователь будет перенаправлен на страницу ошибки с соответствующим сообщением, если:
        // - VerificationToken не существует по какой-либо причине или
        // - Срок действия VerificationToken истек
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    // ============== NON-API ============

    private SimpleMailMessage constructResendVerificationTokenEmail
            (final String contextPath, final Locale locale, final VerificationToken newToken, final User user) {
        String confirmationUrl =
                contextPath + "/regitrationConfirm.html?token=" + newToken.getToken();
        String message = messages.getMessage("message.resendToken", null, locale);
        SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject("Resend Registration Token");
        email.setText(message + " rn" + confirmationUrl);
        email.setFrom(env.getProperty("support.email"));
        email.setTo(user.getEmail());
        return constructEmail("Resend Registration Token", message + " \r\n" + confirmationUrl, user);
    }

    private SimpleMailMessage constructResetTokenEmail(final String contextPath, final Locale locale, final String token, final User user) {
        final String url = contextPath + "/user/changePassword?id=" + user.getId() + "&token=" + token;
        final String message = messages.getMessage("message.resetPassword", null, locale);
        return constructEmail("Reset Password", message + " \r\n" + url, user);
    }

    private SimpleMailMessage constructEmail(String subject, String body, User user) {
        final SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject(subject);
        email.setText(body);
        email.setTo(user.getEmail());
        email.setFrom(env.getProperty("support.email"));
        return email;
    }

    private String getAppUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }

    public void authWithoutPassword(User user) {
        List<Privilege> privileges = user.getRoles().stream()
                .map(Role::getPrivileges)
                .flatMap(Collection::stream).distinct().collect(Collectors.toList());
        List<GrantedAuthority> authorities = privileges.stream().map(p -> new SimpleGrantedAuthority(p.getName())).collect(Collectors.toList());

        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, authorities);

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    //===== Request & Response =====

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    private static class LoginRequest {

        //@Size(max = 32)//, message = "email should at most 32 characters long")
        private String email;
        //@Min(value = 8)
        //@Max(value = 32)//, message = "password should at most 32 characters long")
        private String password;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    private static class RegistrationRequest {
        //@ValidEmail(message = "email names must comply with the standard")
        //@Max(value = 32, message = "email should at most 32 characters long")
        private String email;
        //@Min(value = 8, message = "password must be more than 8 characters")
        @Size(min = 8, max = 32)//, message = "password 8 - 32")//"password should at most 32 characters long")
        //@Max(value = 32, message = "password should at most 32 characters long")
        private String password;
        @Min(value = 5)//, message = "name must be more than 8 characters")
        @Max(value = 32)// , message = "name should at most 32 characters long")
        private String name;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    private static class RegistrationResponse {
        private String token;
        private UserPub userPub;
    }

}
