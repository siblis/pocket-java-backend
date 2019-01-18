package ru.geekbrains.pocket.backend.service;

import ru.geekbrains.pocket.backend.domain.Message;
import ru.geekbrains.pocket.backend.domain.User;

import java.util.List;

public interface MessageService {

    public String sendMessageFromTo(User sender, User recepient, String message);

    public List<Message> getAllMessagesFromUser(User user);

    public List<Message> getAllMessagesToUser(User user);

    public List<Message> getAllUnreadMessagesFromUser(User user);

    public List<Message> getAllUnreadMessagesToUser(User user);


}
