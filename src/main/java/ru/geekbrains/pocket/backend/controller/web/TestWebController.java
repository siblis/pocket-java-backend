package ru.geekbrains.pocket.backend.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class TestWebController {

    @RequestMapping("/test")
    public String index(HttpServletRequest request, Model model) {
        String username = (String) request.getSession().getAttribute("username");
        username = "bob";
        model.addAttribute("username", username);

        return "test";
    }

}
