package ru.geekbrains.pocket.backend.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {

    @RequestMapping("/")
    public String showHomePage() {
        return "index";
    }

    @RequestMapping("/hello")
    public String test() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "index";
    }

    @Secured({"ROLE_ADMIN"})
    @RequestMapping("/onlyYou")
    @ResponseBody
    public String onlyYou() {
        return "index";
    }

    @RequestMapping("favicon.ico")
    String favicon() {
        return "forward:/resources/favicon.ico";
    }
}
