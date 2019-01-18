package ru.geekbrains.pocket.backend.controller.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.pocket.backend.domain.SystemUser;
import ru.geekbrains.pocket.backend.domain.User;
import ru.geekbrains.pocket.backend.service.UserService;

import javax.validation.Valid;

@Controller
@RequestMapping("/register")
public class RegistrationWebController {
    private final Logger logger = LoggerFactory.getLogger(RegistrationWebController.class);
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @InitBinder
    public void initBider(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("/showRegistrationForm")
    public String showMyLoginPage(Model model) {
        model.addAttribute("systemUser", new SystemUser());
        return "registration-form";
    }

    @PostMapping("/processRegistrationForm")
    public String processRegistrationForm(@Valid @ModelAttribute("systemUser") SystemUser systemUser, BindingResult bindingResult, Model model) {
        String userName = systemUser.getUsername();
        logger.debug("Processing registration form for: " + userName);
        if (bindingResult.hasErrors()) {
            return "registration-form";
        }
        User existing = userService.getUserByUsername(userName);
        if (existing != null) {
            model.addAttribute("systemUser", new SystemUser());
            model.addAttribute("registrationError", "User name already exists.");
            logger.debug("User name already exists.");
            return "registration-form";
        }

        User user = new User();
        user.setUsername(systemUser.getUsername());
        user.setPassword(passwordEncoder.encode(systemUser.getPassword()));
        user.setEmail(systemUser.getEmail());

        user = userService.insert(user);
        user.getProfile().setUsername(systemUser.getUsername());
        userService.update(user);

        logger.debug("Successfully created user: " + userName);
        return "registration-confirmation";
    }

}
