package ru.geekbrains.pocket.backend.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.geekbrains.pocket.backend.domain.User;
import ru.geekbrains.pocket.backend.exception.UserNotFoundException;
import ru.geekbrains.pocket.backend.resource.UserResource;
import ru.geekbrains.pocket.backend.response.UsersErrorResponse;
import ru.geekbrains.pocket.backend.service.UserService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
public class RegisterRestController {
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/{userId}")
    public UserResource getUserById(@PathVariable Long userId) {
        return new UserResource(userService.getUserById(userId));
    }

    @GetMapping("/users")
    public Resources<UserResource> getAllUsers() {
        return new Resources<>(userService.getAllUserResources());
    }

    @PostMapping("/user")
    public ResponseEntity<?> addUser(@RequestBody User user) {
        user.setId(0L);
        //TODO вывод ошибки если юзер уже есть
        user = userService.save(user);
        HttpHeaders httpHeaders = new HttpHeaders();

        Link forOneBookmark = new UserResource(user).getLink("self");
        httpHeaders.setLocation(URI.create(forOneBookmark.getHref()));

        return new ResponseEntity<User>(null, httpHeaders, HttpStatus.CREATED);
    }

    @PutMapping("/user")
    public User updateUser(@RequestBody User user) {
        userService.validateUser(user.getUsername());
        return userService.save(user);
    }

    @DeleteMapping("/user/{userId}")
    public int deleteUser(@PathVariable Long userId) {
        userService.delete(userId);
        return HttpStatus.OK.value();
    }

    @ExceptionHandler
    public ResponseEntity<UsersErrorResponse> handleException(UserNotFoundException exc) {
        UsersErrorResponse usersErrorResponse = new UsersErrorResponse();
        usersErrorResponse.setStatus(HttpStatus.NOT_FOUND.value());
        usersErrorResponse.setMessage(exc.getMessage());
        usersErrorResponse.setTimestamp(System.currentTimeMillis());
        return new ResponseEntity<>(usersErrorResponse, HttpStatus.NOT_FOUND);
    }
}
