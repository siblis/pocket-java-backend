package ru.geekbrains.pocket.backend.service;

import ru.geekbrains.pocket.backend.domain.User;
import ru.geekbrains.pocket.backend.domain.UserMessage;

import java.util.List;

public interface UserMessageService {

    UserMessage saveNewMessage(String text, User sender, User recepient);

    String sendMessageFromTo(User sender, User recepient, String message);

    List<UserMessage> getAllMessagesFromUser(User user);

    List<UserMessage> getAllMessagesToUser(User user);

    List<UserMessage> getAllUnreadMessagesFromUser(User user);

    List<UserMessage> getAllUnreadMessagesToUser(User user);

}
