package ru.geekbrains.pocket.backend.controller.web;

import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.pocket.backend.domain.Password;
import ru.geekbrains.pocket.backend.domain.SystemUser;
import ru.geekbrains.pocket.backend.domain.db.*;
import ru.geekbrains.pocket.backend.exception.InvalidOldPasswordException;
import ru.geekbrains.pocket.backend.response.GenericResponse;
import ru.geekbrains.pocket.backend.security.registration.OnRegistrationCompleteEvent;
import ru.geekbrains.pocket.backend.service.RoleService;
import ru.geekbrains.pocket.backend.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping
public class RegistrationWebController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private MessageSource messages;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private ApplicationEventPublisher eventPublisher;
    @Autowired
    private Environment env;

    @InitBinder
    public void initBider(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    //old
    @GetMapping("/reg")
    public String showRegistrationFormOLD(Model model) {
        model.addAttribute("systemUser", new SystemUser());
        return "registration-form";
    }

    //old
    @PostMapping("/processRegistrationForm")
    public String processRegistrationForm(@Valid @ModelAttribute("systemUser") SystemUser systemUser, BindingResult bindingResult, Model model) {
        String emailUser = systemUser.getEmail();
        log.debug("Processing registration form for: " + emailUser);
        if (bindingResult.hasErrors()) {
            return "registration-form";
        }
        User existing = userService.getUserByEmail(emailUser);
        if (existing != null) {
            model.addAttribute("systemUser", new SystemUser());
            model.addAttribute("registrationError", "Email already exists.");
            log.debug("Email already exists.");
            return "registration-form";
        }

        Role roleUser = roleService.getRole("ROLE_USER");
        if (roleUser == null)
            roleUser = roleService.insert(roleUser);

        User user = new User();
        user.setUsername(systemUser.getEmail());
        user.setPassword(passwordEncoder.encode(systemUser.getPassword()));
        user.setEmail(systemUser.getEmail());
        user.setProfile(new UserProfile(systemUser.getEmail()));
        user.setRoles(Arrays.asList(roleUser));

        user = userService.insert(user);

        log.debug("Successfully created user: " + user.getEmail());
        return "registration-confirmation";
    }


    // === NEW ===

    @GetMapping("/registration")
    public String showRegistrationForm(final Model model) {
        log.debug("Rendering registration page.");
        model.addAttribute("systemUser", new SystemUser());
        return "registration";
    }

    //https://www.baeldung.com/registration-verify-user-by-email
    @PostMapping("/registration")
    @ResponseBody
    public GenericResponse registerUserAccount(@Valid SystemUser account, final HttpServletRequest request) { //@ModelAttribute("user")
        log.debug("Registering user account with information: {}", account);

        final User registered = userService.registerNewUserAccount(account);
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registered, request.getLocale(), getAppUrl(request)));
        return new GenericResponse("success");
    }

    //https://www.baeldung.com/registration-verify-user-by-email
    @GetMapping("/registrationConfirm")
    public String confirmRegistration(final HttpServletRequest request, final Model model, @RequestParam("token") final String token)
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
            model.addAttribute("message", messages.getMessage("message.accountVerified", null, locale));
            return "redirect:/console.html?lang=" + locale.getLanguage();
        }

        //Пользователь будет перенаправлен на страницу ошибки с соответствующим сообщением, если:
        // - VerificationToken не существует по какой-либо причине или
        // - Срок действия VerificationToken истек
        model.addAttribute("message", messages.getMessage("auth.message." + result, null, locale));
        model.addAttribute("expired", "expired".equals(result));
        model.addAttribute("token", token);
        return "redirect:/badUser.html?lang=" + locale.getLanguage();
    }


    // user activation - verification

    @RequestMapping(value = "/resendRegistrationToken", method = RequestMethod.GET)
    @ResponseBody
    public GenericResponse resendRegistrationToken(final HttpServletRequest request, @RequestParam("token") final String existingToken) {
        final VerificationToken newToken = userService.generateNewVerificationToken(existingToken);
        final User user = userService.getUserByToken(newToken.getToken());
        mailSender.send(constructResendVerificationTokenEmail(getAppUrl(request), request.getLocale(), newToken, user));
        return new GenericResponse(messages.getMessage("message.resendToken", null, request.getLocale()));
    }

    // Reset password
    @RequestMapping(value = "/user/resetPassword", method = RequestMethod.POST)
    @ResponseBody
    public GenericResponse resetPassword(final HttpServletRequest request, @RequestParam("email") final String userEmail) {
        final User user = userService.getUserByEmail(userEmail);
        if (user != null) {
            final String token = UUID.randomUUID().toString();
            userService.createPasswordResetTokenForUser(user, token);
            mailSender.send(constructResetTokenEmail(getAppUrl(request), request.getLocale(), token, user));
        }
        return new GenericResponse(messages.getMessage("message.resetPasswordEmail", null, request.getLocale()));
    }

    @RequestMapping(value = "/user/changePassword", method = RequestMethod.GET)
    public String showChangePasswordPage(final Locale locale, final Model model, @RequestParam("id") final String id, @RequestParam("token") final String token) {
        final String result = userService.validatePasswordResetToken(new ObjectId(id), token);
        if (result != null) {
            model.addAttribute("message", messages.getMessage("auth.message." + result, null, locale));
            return "redirect:/login?lang=" + locale.getLanguage();
        }
        return "redirect:/updatePassword.html?lang=" + locale.getLanguage();
    }

    @RequestMapping(value = "/user/savePassword", method = RequestMethod.POST)
    @ResponseBody
    public GenericResponse savePassword(final Locale locale, @Valid Password password) {
        final User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userService.changeUserPassword(user, password.getNewPassword());
        return new GenericResponse(messages.getMessage("message.resetPasswordSuc", null, locale));
    }

    // change user password
    @RequestMapping(value = "/user/updatePassword", method = RequestMethod.POST)
    @ResponseBody
    public GenericResponse changeUserPassword(final Locale locale, @Valid Password password) {
        final User user = userService.getUserByEmail(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getEmail());
        if (!userService.checkIfValidOldPassword(user, password.getOldPassword())) {
            throw new InvalidOldPasswordException();
        }
        userService.changeUserPassword(user, password.getNewPassword());
        return new GenericResponse(messages.getMessage("message.updatePasswordSuc", null, locale));
    }

//    @RequestMapping(value = "/user/update/2fa", method = RequestMethod.POST)
//    @ResponseBody
//    public GenericResponse modifyUser2FA(@RequestParam("use2FA") final boolean use2FA) throws UnsupportedEncodingException {
//        final User user = userService.updateUser2FA(use2FA);
//        if (use2FA) {
//            return new GenericResponse(userService.generateQRUrl(user));
//        }
//        return null;
//    }

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
}
