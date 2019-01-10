package ru.geekbrains.pocket.backend.controller.rest;

import java.security.Principal;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.pocket.backend.domain.User;
import ru.geekbrains.pocket.backend.exception.UserNotFoundException;
import ru.geekbrains.pocket.backend.resource.UserResource;
import ru.geekbrains.pocket.backend.response.UsersErrorResponse;
import ru.geekbrains.pocket.backend.service.UserService;

import java.net.URI;

@RestController
@RequestMapping("/api/users")
@Slf4j
public class RegisterRestController {
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public UserResource getUserById(@PathVariable Long id) {
        return new UserResource(userService.getUserById(id));
    }

    @GetMapping("/all")
    public Resources<UserResource> getAllUsers() {
        return new Resources<>(userService.getAllUserResources());
    }

    @PostMapping("/")
    public ResponseEntity<?> addUser(@RequestBody User user) {
        user.setId(0L);
        //TODO вывод ошибки если юзер уже есть
        user = userService.save(user);
        HttpHeaders httpHeaders = new HttpHeaders();

        Link forOneBookmark = new UserResource(user).getLink("self");
        httpHeaders.setLocation(URI.create(forOneBookmark.getHref()));

        log.info("addUser: " + user);

        return new ResponseEntity<User>(null, httpHeaders, HttpStatus.CREATED);
    }

    @PutMapping("/")
    public User updateUser(Principal principal, @RequestBody User user) {
        //обновление доступно только текущего авторизованного юзера
        String username = principal.getName();
        user.setId(userService.validateUser(username).getId());
        return userService.save(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
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
