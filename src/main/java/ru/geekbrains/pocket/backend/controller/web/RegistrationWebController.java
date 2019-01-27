package ru.geekbrains.pocket.backend.controller.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.pocket.backend.domain.SystemUser;
import ru.geekbrains.pocket.backend.domain.db.Role;
import ru.geekbrains.pocket.backend.domain.db.User;
import ru.geekbrains.pocket.backend.domain.db.UserProfile;
import ru.geekbrains.pocket.backend.service.RoleService;
import ru.geekbrains.pocket.backend.service.UserService;

import javax.validation.Valid;
import java.util.Arrays;

@Controller
@RequestMapping("/register")
@Slf4j
public class RegistrationWebController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @InitBinder
    public void initBider(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping
    public String showRegistrationForm(Model model) {
        model.addAttribute("systemUser", new SystemUser());
        return "registration-form";
    }

    @GetMapping("/showRegistrationForm")
    public String showMyRegistrationForm(Model model) {
        model.addAttribute("systemUser", new SystemUser());
        return "registration-form";
    }

    @PostMapping("/processRegistrationForm")
    public String processRegistrationForm(@Valid @ModelAttribute("systemUser") SystemUser systemUser, BindingResult bindingResult, Model model) {
        String emailUser = systemUser.getEmail();
        log.debug("Processing registration form for: " + emailUser);
        if (bindingResult.hasErrors()) {
            return "registration-form";
        }
        User existing = userService.getUserByEmail(emailUser);
        if (existing != null) {
            model.addAttribute("systemUser", new SystemUser());
            model.addAttribute("registrationError", "Email already exists.");
            log.debug("Email already exists.");
            return "registration-form";
        }

        Role roleUser = roleService.getRole("ROLE_USER");
        if (roleUser == null)
            roleUser = roleService.insert(roleUser);

        User user = new User();
        user.setUsername(systemUser.getUsername());
        user.setPassword(passwordEncoder.encode(systemUser.getPassword()));
        user.setEmail(systemUser.getEmail());
        user.setProfile(new UserProfile(systemUser.getUsername()));
        user.setRoles(Arrays.asList(roleUser));

        user = userService.insert(user);

        log.debug("Successfully created user: " + user.getEmail());
        return "registration-confirmation";
    }

}
