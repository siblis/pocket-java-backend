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
import ru.geekbrains.pocket.backend.domain.db.UserContacts;
import ru.geekbrains.pocket.backend.domain.pub.UserProfilePub;
import ru.geekbrains.pocket.backend.domain.pub.ValidationErrorCollection;
import ru.geekbrains.pocket.backend.repository.UserContactsRepository;
import ru.geekbrains.pocket.backend.service.UserContactService;
import ru.geekbrains.pocket.backend.service.UserService;

import javax.validation.Valid;
import java.awt.*;
import java.security.Principal;
@Slf4j
@RestController
@RequestMapping("/v1")
public class RestContactsController {
    @Autowired
    private UserContactService userContactService;
    @GetMapping("account/contacts")

    @GetMapping ("account/contacts/%id")
    public UserContacts getContactById(){
        return UserContactsRepository.findById;
    }

}
