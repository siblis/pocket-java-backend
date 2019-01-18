package ru.geekbrains.pocket.backend.service;

import ru.geekbrains.pocket.backend.domain.User;
import ru.geekbrains.pocket.backend.domain.UserMessages;

import java.util.List;

public interface UserMessagesService {

    public String sendMessageFromTo(User sender, User recepient, String message);

    public List<UserMessages> getAllMessagesFromUser(User user);

    public List<UserMessages> getAllMessagesToUser(User user);

    public List<UserMessages> getAllUnreadMessagesFromUser(User user);

    public List<UserMessages> getAllUnreadMessagesToUser(User user);


}
