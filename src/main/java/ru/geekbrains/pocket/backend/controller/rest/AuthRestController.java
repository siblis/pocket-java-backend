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
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.pocket.backend.domain.db.Role;
import ru.geekbrains.pocket.backend.domain.db.User;
import ru.geekbrains.pocket.backend.domain.db.UserProfile;
import ru.geekbrains.pocket.backend.domain.pub.UserPub;
import ru.geekbrains.pocket.backend.service.RoleService;
import ru.geekbrains.pocket.backend.service.UserService;
import ru.geekbrains.pocket.backend.util.validation.ValidEmail;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Arrays;

@Slf4j
//@Validated
@RestController
@RequestMapping("/api/auth")
public class AuthRestController {
    private final static String ROLE_USER = "ROLE_USER";

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam(name = "email") String email,
                                   @RequestParam(name = "password") String password) {
        //   public ResponseEntity login(@Valid @ModelAttribute LoginRequest loginRequest) {
//    public ResponseEntity<?> login(@Valid @Size(max = 10, message = "name should at most 10 characters long") @RequestParam("email") String email,
//                                   @Valid @Max(value = 32) @RequestParam("password") String password) {
        //TODO validate
        User user = userService.getUserByEmail(email);
        if (user == null) {
            log.debug("User not exists.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        String token = ""; //TODO token

        return new ResponseEntity<>(new RegistrationResponse(token, new UserPub(user)), HttpStatus.OK);
    }

    @PostMapping("/login2")
    public ResponseEntity login2(@RequestBody LoginRequest loginRequest) {
        //TODO validate
        User user = userService.getUserByEmail(loginRequest.getEmail());
        if (user == null) {
            log.debug("User not exists.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        String token = ""; //TODO token

        return new ResponseEntity<>(new RegistrationResponse(token, new UserPub(user)), HttpStatus.OK);
    }

    @PostMapping("/registration")
    public ResponseEntity<?> registration(@RequestParam("email") @Max(value = 32) @ValidEmail String email,
                                          @RequestParam("password") @Min(value = 8) @Max(value = 32) String password,
                                          @RequestParam("name") @Min(value = 2) @Max(value = 32) String name) {
        //public ResponseEntity<?> registration(@RequestBody RegistrationRequest registrationRequest) {
        //public ResponseEntity<?> registration(@RequestBody @Max(value = 32) String email, @Max(value = 32) String password, @Max(value = 32) String name) {
//    public ResponseEntity<?> registration(@RequestBody RegistrationRequest registrationRequest) {
        //TODO validate
        log.debug("Processing registration form for: " + email);
        User existing = userService.getUserByEmail(email);
        if (existing != null) {
            log.debug("Email already exists.");
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        Role roleUser = roleService.getRole(ROLE_USER);
        if (roleUser == null)
            roleUser = roleService.insert(new Role(ROLE_USER));

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        //user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        user.setUsername(name);
        user.setProfile(new UserProfile(name));
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
    class LoginRequest {
        //@Size(max = 10, message = "name should at most 10 characters long")
        private String email;
        //@Max(value = 32)
        private String password;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    private static class RegistrationRequest {
        private String email;
        private String password;
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
