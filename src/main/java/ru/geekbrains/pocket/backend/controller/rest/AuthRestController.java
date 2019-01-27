package ru.geekbrains.pocket.backend.controller.rest;

import org.springframework.web.bind.annotation.*;
import ru.geekbrains.pocket.backend.domain.pub.UserPub;

@RestController
@RequestMapping("/api/auth")
public class AuthRestController {
    //@PostMapping("/login")
    //@RequestBody
    public String login(String email, String password) {
        return "";
    }

    //test
    //@GetMapping("/l")
    public String lRest(@RequestBody UserPub userPub) {
        return "";
    }

    @PostMapping("/registration")
    public String registration(@RequestBody String email, String password, String name) {
        return "";
    }

}
