package ru.geekbrains.pocket.backend.controller.rest;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.pocket.backend.controller.web.RegistrationWebController;
import ru.geekbrains.pocket.backend.domain.SystemUser;
import ru.geekbrains.pocket.backend.domain.User;
import ru.geekbrains.pocket.backend.resource.UserResource;
import ru.geekbrains.pocket.backend.service.UserService;

import java.net.URI;

@RestController
@RequestMapping("/api")
@Slf4j
public class RegisterRestController {
    private final Logger logger = LoggerFactory.getLogger(RegistrationWebController.class);
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @CrossOrigin(origins = "http://localhost:9000")
    @GetMapping("/register/{name}")
    public UserResource getUserByName(@PathVariable String name) {
        return new UserResource(userService.getUserByUsername(name));
    }

    @PostMapping("/register/")
    public ResponseEntity<?> processRegistrationForm(@RequestBody SystemUser systemUser) {
        String userName = systemUser.getUsername();
        logger.debug("Processing registration form for: " + userName);
        User existing = userService.getUserByUsername(userName);
        if (existing != null) {
            logger.debug("User name already exists.");
        }

        User user = new User();
        user.setUsername(systemUser.getUsername());
        user.setPassword(passwordEncoder.encode(systemUser.getPassword()));
        user.setEmail(systemUser.getEmail());

        user = userService.insert(user);
        user.getProfile().setUsername(systemUser.getUsername());
        userService.update(user);

        logger.debug("Successfully created user: " + userName);

        HttpHeaders httpHeaders = new HttpHeaders();

        Link forOneBookmark = new UserResource(user).getLink("self");
        httpHeaders.setLocation(URI.create(forOneBookmark.getHref()));

        log.info("register: " + user);

        return new ResponseEntity<User>(null, httpHeaders, HttpStatus.CREATED);
    }
}
