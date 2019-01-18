package ru.geekbrains.pocket.backend.controller.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.geekbrains.pocket.backend.domain.User;

@RestController
@RequestMapping("/api")
public class LoginRestController {
    @GetMapping("/login")
    public String loginRest(@RequestBody User user) {
        return "";
    }

}
