package ru.geekbrains.pocket.backend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.pocket.backend.domain.User;
import ru.geekbrains.pocket.backend.domain.Message;
import ru.geekbrains.pocket.backend.repository.MessageRepository;
import ru.geekbrains.pocket.backend.service.MessageService;

import java.util.Date;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public String sendMessageFromTo(User sender, User recepient, String message) {
        Message messageNew = new Message(sender, recepient, message);
        messageNew.setSend_at(new Date());
        messageNew.setRead(false);
        messageRepository.save(messageNew);

        return "message added to db";
    }

    @Override
    public List<Message> getAllMessagesFromUser(User user) {
        return messageRepository.findAllBySender(user);
    }

    @Override
    public List<Message> getAllMessagesToUser(User user) {
        return messageRepository.findAllByRecepient(user);
    }

    @Override
    public List<Message> getAllUnreadMessagesFromUser(User user) {
        return messageRepository.findAllBySenderAndReadFalse(user);
    }

    @Override
    public List<Message> getAllUnreadMessagesToUser(User user) {
        return messageRepository.findAllByRecepientAndReadFalse(user);
    }

}
