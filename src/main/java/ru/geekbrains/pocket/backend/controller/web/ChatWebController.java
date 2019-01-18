package ru.geekbrains.pocket.backend.controller.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
//@RequestMapping("/chat")
public class ChatWebController {

    @RequestMapping("/chat")
    public String index(HttpServletRequest request, Model model) {
        String username = (String) request.getSession().getAttribute("username");
        username = "bob";
        if (username == null || username.isEmpty()) {
            return "redirect:/chat/logg";
        }
        model.addAttribute("username", username);

        return "chat";
    }

    @RequestMapping(path = "/chat/logg", method = RequestMethod.GET)
    public String showLoginPage() {
        return "chat-login";
    }

    @RequestMapping(path = "/chat/logg", method = RequestMethod.POST)
    public String doLogin(HttpServletRequest request, @RequestParam(defaultValue = "") String username) {
        username = username.trim();

        if (username.isEmpty()) {
            return "chat-login";
        }
        request.getSession().setAttribute("username", username);

        //return "chat";
        return "redirect:/chat";
    }

    @RequestMapping(path = "/chat/logout")
    public String logout(HttpServletRequest request) {
        request.getSession(true).invalidate();

        return "redirect:/chat/login";
    }

}
