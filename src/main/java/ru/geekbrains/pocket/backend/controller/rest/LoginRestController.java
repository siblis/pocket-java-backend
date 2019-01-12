package ru.geekbrains.pocket.backend.controller.rest;

import org.springframework.web.bind.annotation.*;
import ru.geekbrains.pocket.backend.domain.User;

@RestController
@RequestMapping("/api")
public class LoginRestController {
    @GetMapping("/login")
    public String loginRest(@RequestBody User user) {
        return "";
    }

}
