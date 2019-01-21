package ru.geekbrains.pocket.backend.service.impl;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.pocket.backend.domain.User;
import ru.geekbrains.pocket.backend.domain.UserMessage;
import ru.geekbrains.pocket.backend.exception.UserMessageNotFoundException;
import ru.geekbrains.pocket.backend.repository.UserMessageRepository;
import ru.geekbrains.pocket.backend.service.UserMessageService;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserMessageServiceImpl implements UserMessageService {
    @Autowired
    private UserMessageRepository repository;

    @Override
    public UserMessage createMessage(UserMessage message) {
        message.setSent_at(new Date());
        message.setRead(false);
        return repository.insert(message);
    }

    public UserMessage createMessage(User sender, User recepient, String text) {
        UserMessage messageNew = new UserMessage(sender, recepient, text);
        messageNew.setSent_at(new Date());
        messageNew.setRead(false);
        return repository.insert(messageNew);
    }

    @Override
    public void deleteMessage(UserMessage message) {
        repository.delete(message);
    }

    @Override
    public UserMessage getMessage(ObjectId id) {
        Optional<UserMessage> userMessage = Optional.of(repository.findById(id).orElseThrow(
                () -> new UserMessageNotFoundException("User message with id = " + id + " not found")));
        return userMessage.get();
    }

    @Override
    public List<UserMessage> getMessagesBySender(User sender) {
        return repository.findBySender(sender);
    }

    @Override
    public List<UserMessage> getMessagesByRecepient(User recepient) {
        return repository.findByRecepient(recepient);
    }

    @Override
    public List<UserMessage> getUnreadMessagesFromUser(User sender) {
        return repository.findBySenderAndReadFalse(sender);
    }

    @Override
    public List<UserMessage> getUnreadMessagesToUser(User recepient) {
        return repository.findByRecepientAndReadFalse(recepient);
    }

    public UserMessage sendMessageFromTo(User sender, User recepient, String message) {
        UserMessage messageNew = new UserMessage(sender, recepient, message);
        messageNew.setSent_at(new Date());
        messageNew.setRead(false);
        return repository.save(messageNew);
    }

    @Override
    public UserMessage updateMessage(UserMessage message) {
        return null;
    }

}
