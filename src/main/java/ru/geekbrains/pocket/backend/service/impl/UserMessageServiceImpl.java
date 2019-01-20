package ru.geekbrains.pocket.backend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.pocket.backend.domain.User;
import ru.geekbrains.pocket.backend.domain.UserMessage;
import ru.geekbrains.pocket.backend.repository.MessageRepository;
import ru.geekbrains.pocket.backend.service.UserMessageService;

import java.util.Date;
import java.util.List;

@Service
public class UserMessageServiceImpl implements UserMessageService {

    @Autowired
    private MessageRepository messageRepository;

    public UserMessage saveNewMessage(String text, User sender, User recepient) {
        UserMessage messageNew = new UserMessage(sender, recepient, text);
        messageNew.setSend_at(new Date());
        messageNew.setRead(false);
        return messageRepository.insert(messageNew);
    }

    public String sendMessageFromTo(User sender, User recepient, String message) {
        UserMessage messageNew = new UserMessage(sender, recepient, message);
        messageNew.setSend_at(new Date());
        messageNew.setRead(false);
        messageRepository.save(messageNew);

        return "message added to db";
    }

    @Override
    public List<UserMessage> getAllMessagesFromUser(User user) {
        return messageRepository.findAllBySender(user);
    }

    @Override
    public List<UserMessage> getAllMessagesToUser(User user) {
        return messageRepository.findAllByRecepient(user);
    }

    @Override
    public List<UserMessage> getAllUnreadMessagesFromUser(User user) {
        return messageRepository.findAllBySenderAndReadFalse(user);
    }

    @Override
    public List<UserMessage> getAllUnreadMessagesToUser(User user) {
        return messageRepository.findAllByRecepientAndReadFalse(user);
    }

}
