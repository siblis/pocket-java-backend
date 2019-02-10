package ru.geekbrains.pocket.backend.controller.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.pocket.backend.domain.db.UserContact;
import ru.geekbrains.pocket.backend.service.UserContactService;

@Slf4j
@RestController
@RequestMapping("/account")
public class ContactRestController {
    @Autowired
    private UserContactService userContactService;

    @GetMapping("/contacts") //Получить список контактов
    public UserContact getContacts(){
        return null;
    }

    @GetMapping ("/contacts/%id") //Получить контакт по id пользователья (если такой пользователь есть в списке контактов)
    public UserContact getContact(){
        return null;
    }

    @PostMapping("/contacts") //Добавить новый контакт
    //Особенность: не возвращает ошибки, если такой пользователь в списке контактов уже есть, но и не добавляет его еще раз
    public UserContact addNewContact(){
        return null;
    }

    @PutMapping("/contacts/%id") //Редактировать контакт
    public UserContact editContact(){
        return null;
    }

    @DeleteMapping("/contacts/%id") //Удалить контакт
    //Особенность: не возвращает ошибки, даже если такого пользователя в контактах нет
    public UserContact deleteContact(){
        return null;
    }

}
