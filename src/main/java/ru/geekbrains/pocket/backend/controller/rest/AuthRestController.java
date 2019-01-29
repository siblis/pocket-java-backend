package ru.geekbrains.pocket.backend.controller.rest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.geekbrains.pocket.backend.domain.db.Role;
import ru.geekbrains.pocket.backend.domain.db.User;
import ru.geekbrains.pocket.backend.domain.db.UserProfile;
import ru.geekbrains.pocket.backend.domain.pub.UserPub;
import ru.geekbrains.pocket.backend.service.RoleService;
import ru.geekbrains.pocket.backend.service.UserService;

import javax.validation.constraints.Max;
import java.util.Arrays;

@Slf4j
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
    public ResponseEntity login(@RequestBody String email, String password) {
        User user = userService.getUserByEmail(email);
        if (user == null) {
            log.debug("User not exists.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        String token = ""; //TODO token

        return new ResponseEntity<>(new RegistrationResponse(token, new UserPub(user)), HttpStatus.OK);
    }


    @PostMapping("/registration")
    public ResponseEntity<?> registration(@RequestBody RegistrationRequest registrationRequest) {
        //public ResponseEntity<?> registration(@RequestBody @Max(value = 32) String email, @Max(value = 32) String password, @Max(value = 32) String name) {
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
