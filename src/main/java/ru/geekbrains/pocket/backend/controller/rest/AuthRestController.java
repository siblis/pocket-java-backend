package ru.geekbrains.pocket.backend.controller.rest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody String email, String password) {
        String s = "";//HttpServletResponse response
        //response.sendRedirect("some-url");
        //return "redirect:/authenticateTheUser";
        //response.sendRedirect(request.getContextPath() + "/web");
        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY)
                .header(HttpHeaders.LOCATION, "/authenticateTheUser").build();
//        HttpHeaders headers = new HttpHeaders();
//        headers.setLocation(URI.create("/authenticateTheUser"));
//        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }


    @PostMapping("/registration")
    public ResponseEntity<?> registration(@RequestBody @Max(value = 32) String email, @Max(value = 32) String password, @Max(value = 32) String name) {
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
        user.setPassword(passwordEncoder.encode(password));
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
    @AllArgsConstructor
    private static class RegistrationResponse {
        private String token;
        private UserPub userPub;
    }

}
