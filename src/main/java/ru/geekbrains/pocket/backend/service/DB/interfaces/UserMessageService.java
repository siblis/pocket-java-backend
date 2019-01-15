package ru.geekbrains.pocket.backend.service.DB.interfaces;

import ru.geekbrains.pocket.backend.domain.entitiesDB.Users;
import ru.geekbrains.pocket.backend.domain.entitiesDB.UsersMessages;

import java.util.List;

public interface UserMessageService{

    public String sendMessageFromTo(Users sender, Users recepient, String message);

    public List<UsersMessages> getAllMessagesFromUser(Users user);

    public List<UsersMessages> getAllMessagesToUser(Users user);

    public List<UsersMessages> getAllUnreadMessagesFromUser(Users user);

    public List<UsersMessages> getAllUnreadMessagesToUser(Users user);


}
