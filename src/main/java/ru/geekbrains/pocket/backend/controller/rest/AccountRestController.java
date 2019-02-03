package ru.geekbrains.pocket.backend.controller.rest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.pocket.backend.domain.db.User;
import ru.geekbrains.pocket.backend.domain.pub.UserProfilePub;
import ru.geekbrains.pocket.backend.domain.pub.ValidationErrorCollection;
import ru.geekbrains.pocket.backend.service.UserService;

import javax.validation.Valid;
import java.security.Principal;

@Slf4j
@RestController
@RequestMapping("/v1")
public class AccountRestController {
    @Autowired
    private UserService userService;

    @GetMapping("/account") //Получить информацию о своем аккаунте
    public ResponseEntity<?> getAccount(Principal principal) {
        User user = userService.getUserByEmail(principal.getName());
        if (user != null)
            return new ResponseEntity<>(new UserProfilePub(user), HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping(name = "/account", consumes = "application/json") //Изменить данные аккаунта
    public ResponseEntity<?> editAccount(@Valid @RequestBody EditAccountRequest editAccountRequest) {
        //TODO validate
        User user = userService.getUserByEmail(editAccountRequest.getName());
        if (user != null)
            if (user.getPassword().equals(editAccountRequest.getOldPassword())) {
                user.setPassword(editAccountRequest.getNewPassword());
                user = userService.update(user);
                return new ResponseEntity<>(new UserProfilePub(user), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        else {
            ValidationErrorCollection validationErrorCollection = new ValidationErrorCollection();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    private static class EditAccountRequest {
        private String name;
        private String oldPassword;
        private String newPassword;
    }
}
