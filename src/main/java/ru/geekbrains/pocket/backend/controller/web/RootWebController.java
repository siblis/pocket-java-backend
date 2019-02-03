package ru.geekbrains.pocket.backend.controller.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
public class RootWebController {

    //@RequestMapping("/")
    public String showHomePage() {
        return "index";
    }

    @RequestMapping("/web")
    public String showMainPage() {
        return "main";
    }

    @Secured({"ROLE_ADMIN"})
    @RequestMapping("/onlyYou")
    @ResponseBody
    public String onlyYou() {
        return "main";
    }

    @RequestMapping("/favicon.ico")
    String favicon() {
        return "forward:/resources/favicon.ico";
    }
}
