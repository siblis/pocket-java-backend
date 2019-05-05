package ru.geekbrains.pocket.backend.controller.rest;

import com.mongodb.MongoServerException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.pocket.backend.domain.db.User;
import ru.geekbrains.pocket.backend.domain.pub.UserPub;
import ru.geekbrains.pocket.backend.exception.UserAlreadyExistException;
import ru.geekbrains.pocket.backend.service.UserService;
import ru.geekbrains.pocket.backend.util.validation.ValidEmail;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Log4j2
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
@Api(tags = "Authentification", value = "Authentification сontroller")
public class AuthRestController {
    @Autowired
    private UserService userService;
//    @Autowired
//    private UserTokenService userTokenService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @ApiOperation(value = "Login user",
            notes = "User authentication using login and password",
            response = LoginResponse.class)
    @PostMapping(path = "/login", consumes = "application/json")// produces = "application/json;charset=UTF-8")
    public ResponseEntity login(@Valid @RequestBody LoginRequest loginRequest) {
        //TODO validate
        User user = userService.getUserByEmail(loginRequest.getEmail());
        if (user == null) {
            log.debug("User not exists.");
            return new ResponseEntity<>("Login or password is incorrect", HttpStatus.NOT_FOUND);
        }

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            log.debug("The current password specified is incorrect!");
            return new ResponseEntity<>("Login or password is incorrect", HttpStatus.NOT_FOUND);
        }

        //ищем есть ли токен у этого юзера
//        UserToken userToken = userTokenService.getValidToken(user, "0.0.0.0");

        String newToken = userService.getNewToken(user);

//        try {
//            AuthenticationUser.authWithoutPassword(user);
//        } catch (AuthenticationException ex){
//            log.error(ex.getMessage());
//            return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
//        }

        return new ResponseEntity<>(new LoginResponse(newToken, new UserPub(user)), HttpStatus.OK);
//        return new ResponseEntity<>(new LoginResponse(userToken.getToken(), new UserPub(userToken.getUser())), HttpStatus.OK);
    }

    @ApiOperation(value = "Registration new user",
            notes = "New user registration",
            response = LoginResponse.class)
    @PostMapping(path = "/registration", consumes = "application/json")
    public ResponseEntity<?> registration(@Valid @RequestBody RegistrationRequest registrationRequest,
                                          final BindingResult result, final HttpServletRequest request)
            throws AuthenticationException {
        //, WebRequest webRequest, Errors errors) {
        //аргумент класса BindingResult должен быть сразу после аргумента, помеченного аннотациями @RequestBody и valid,
        // в других случаях валидация работать не будет
        if (result.hasErrors()) {
            final Map<String, Object> response = new HashMap<>();
            response.put("message", "Your request contains errors");
            response.put("errors", result.getAllErrors()
                    .stream()
                    .map(x -> String.format("%s : %s", x.getCode(), x.getDefaultMessage()))
                    .collect(Collectors.toList()));
            log.debug(response);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        User user;
        try {
            user = userService.createUserAccount(registrationRequest.getEmail(),
                    registrationRequest.getPassword(),
                    registrationRequest.getName());
            if (user == null){
                log.debug("Error write to db:" + registrationRequest);
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (UserAlreadyExistException ex) {
            log.debug("Email already exists: " + registrationRequest);
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
        } catch (DuplicateKeyException | MongoServerException ex) {
            log.debug("Error write to db:" + registrationRequest);
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
        }

//        UserToken userToken = userTokenService.createOrUpdateToken(user, "0.0.0.0");
        String newToken = userService.getNewToken(user);

//        try {
//            AuthenticationUser.authWithoutPassword(user);
//        } catch (AuthenticationException ex){
//            return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
//        }

        //отправка электронного письма с запросом подтверждения email
//        try {
//        eventPublisher.publishEvent(new OnRegistrationCompleteEvent
//                (registered, request.getLocale(), getAppUrl(request)));
//        } catch (Exception me) {
//            return new ResponseEntity<>(HttpStatus.CONFLICT);
//        }
        log.debug("Successfully created user: " + user.getEmail() + " and token " + newToken);

        return new ResponseEntity<>(new LoginResponse(newToken, new UserPub(user)), HttpStatus.CREATED);
//        return new ResponseEntity<>(new LoginResponse(userToken.getToken(), new UserPub(userToken.getUser())), HttpStatus.CREATED);
    }

    //===== Request & Response =====

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ApiModel(value = "Login request",
            description="Тело запроса в формате JSON для аутентификации пользователя по логину и паролю.")
    public static class LoginRequest {

        @NotNull
        @ValidEmail
        @Size(min = 6, max = 32)
        @ApiModelProperty(value = "Email specified during registration. Size(min = 6, max = 32).",
                example = "petr@mail.ru", position = 0, required = true)
        private String email;
        @NotNull
        @Size(min = 8, max = 32)
        @ApiModelProperty(value = "Password size(min = 8, max = 32)",
                example = "Abc12345", position = 1, required = true)
        private String password;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ApiModel(value = "Registration request",
            description="Тело запроса в формате JSON для регистрации нового пользователя.")
    public static class RegistrationRequest {
        @NotNull
        @ValidEmail //(message = "email names must comply with the standard")
        @Size(min = 6, max = 32)
        @ApiModelProperty(value = "Email specified during registration. Size(min = 6, max = 32).",
                example = "petr@mail.ru", position = 0, required = true)
        private String email;
        @NotNull
        @Size(min = 8, max = 32)
        @ApiModelProperty(value = "Password size(min = 8, max = 32)",
                example = "Abc12345", position = 1, required = true)
        private String password;
        @NotNull
        @Size(min = 2, max = 32)
        @ApiModelProperty(value = "Username. Size(min = 2, max = 32)", position = 2, required = true)
        private String name;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @ApiModel(value = "Login response",
            description="Тело ответа, содержит token и информацию о пользователе в формате JSON.")
    public static class LoginResponse {
        private String token;
        private UserPub user;
    }

}
