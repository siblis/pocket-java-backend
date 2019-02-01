package ru.geekbrains.pocket.backend.controller.rest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.pocket.backend.domain.db.Role;
import ru.geekbrains.pocket.backend.domain.db.User;
import ru.geekbrains.pocket.backend.domain.db.UserProfile;
import ru.geekbrains.pocket.backend.domain.pub.UserPub;
import ru.geekbrains.pocket.backend.service.RoleService;
import ru.geekbrains.pocket.backend.service.UserService;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
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

    @PostMapping(path = "/login", consumes = "application/json")
    //@PostMapping(path = "/login", produces = "application/json;charset=UTF-8")
    public ResponseEntity login(@RequestBody LoginRequest loginRequest) {
//    public ResponseEntity login(@Valid @RequestBody UserInput loginRequest) {
        //TODO validate
        User user = userService.getUserByEmail(loginRequest.getEmail());
        if (user == null) {
            log.debug("User not exists.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        String token = ""; //TODO token

        return new ResponseEntity<>(new RegistrationResponse(token, new UserPub(user)), HttpStatus.OK);
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
    public ResponseEntity<?> registration(@Valid @RequestBody RegistrationRequest registrationRequest, BindingResult errors) {//}, Errors errors) {
//    public ResponseEntity<?> registration(@RequestParam("email") @Max(value = 32) @ValidEmail String email,
//                                          @RequestParam("password") @Min(value = 8) @Max(value = 32) String password,
//                                          @RequestParam("name") @Min(value = 2) @Max(value = 32) String name) {
        //TODO validate
        //If error, just return a 400 bad request, along with the error message
        if (errors.hasErrors()) {
            final Map<String, Object> response = new HashMap<>();
            response.put("message", "Your request contains errors");
            response.put("errors", errors.getAllErrors()
                    .stream()
                    .map(x -> String.format("%s : %s", x.getCode(), x.getDefaultMessage()))
                    .collect(Collectors.toList()));
            //return ResponseEntity.badRequest().body(response);
        }

        log.debug("Processing registration form for: " + registrationRequest.getEmail());
        User existing = userService.getUserByEmail(registrationRequest.getEmail());
        if (existing != null) {
            log.debug("Email already exists.");
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        Role roleUser = roleService.getRole(ROLE_USER);
        if (roleUser == null)
            roleUser = roleService.insert(new Role(ROLE_USER));

        User user = new User();
        user.setEmail(registrationRequest.getEmail());
        user.setPassword(registrationRequest.getPassword());
        //user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        user.setUsername(registrationRequest.getName());
        user.setProfile(new UserProfile(registrationRequest.getName()));
        user.setRoles(Arrays.asList(roleUser));
        user = userService.insert(user);

        log.debug("Successfully created user: " + user.getEmail());

        String token = ""; //TODO token

        return new ResponseEntity<>(new RegistrationResponse(token, new UserPub(user)), HttpStatus.CREATED);

    }

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
