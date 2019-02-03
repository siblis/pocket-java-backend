package ru.geekbrains.pocket.backend.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginWebController {
    //@GetMapping("/login")
    public String showMyLoginPage() {
        return "modern-login";
    }

    //@GetMapping("/logout")
    public String showLogout() {
        return "modern-login";
    }

    //@PostMapping("/logout")
    public String showMyLogout() {
        return "modern-login";
    }

    @GetMapping("/accessDenied")
    public String showAccessDeniedPage() {
        return "access-denied";
    }
}
