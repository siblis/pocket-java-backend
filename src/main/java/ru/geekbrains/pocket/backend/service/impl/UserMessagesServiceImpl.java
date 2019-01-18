package ru.geekbrains.pocket.backend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.pocket.backend.domain.User;
import ru.geekbrains.pocket.backend.domain.UserMessages;
import ru.geekbrains.pocket.backend.repository.UserMessagesRepository;
import ru.geekbrains.pocket.backend.service.UserMessagesService;

import java.util.Date;
import java.util.List;

@Service
public class UserMessagesServiceImpl implements UserMessagesService {

    @Autowired
    private UserMessagesRepository userMessagesRepository;

    public String sendMessageFromTo(User sender, User recepient, String message) {
        UserMessages messageNew = new UserMessages(sender, recepient, message);
        messageNew.setSend_at(new Date());
        messageNew.setRead(false);
        userMessagesRepository.save(messageNew);

        return "message added to db";
    }

    @Override
    public List<UserMessages> getAllMessagesFromUser(User user) {
        return userMessagesRepository.findAllBySender(user);
    }

    @Override
    public List<UserMessages> getAllMessagesToUser(User user) {
        return userMessagesRepository.findAllByRecepient(user);
    }

    @Override
    public List<UserMessages> getAllUnreadMessagesFromUser(User user) {
        return userMessagesRepository.findAllBySenderAndReadFalse(user);
    }

    @Override
    public List<UserMessages> getAllUnreadMessagesToUser(User user) {
        return userMessagesRepository.findAllByRecepientAndReadFalse(user);
    }

}
