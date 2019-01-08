package ru.geekbrains.pocket.backend.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminWebController {
    @GetMapping("")
    public String adminHome() {
        return "admin-panel";
    }
}
