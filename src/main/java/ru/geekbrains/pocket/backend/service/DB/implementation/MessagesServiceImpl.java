package ru.geekbrains.pocket.backend.service.DB.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.pocket.backend.domain.entitiesDB.Users;
import ru.geekbrains.pocket.backend.domain.entitiesDB.UsersMessages;
import ru.geekbrains.pocket.backend.repository.DB.UserMessagesRepository;
import ru.geekbrains.pocket.backend.service.DB.interfaces.UserMessageService;


import java.util.Date;
import java.util.List;

@Service
public class MessagesServiceImpl implements UserMessageService {

    private UserMessagesRepository userMessagesRepository;

    @Autowired
    public MessagesServiceImpl(UserMessagesRepository userMessagesRepository) {
        this.userMessagesRepository = userMessagesRepository;
    }

    public String sendMessageFromTo(Users sender, Users recepient, String message){
        UsersMessages messageNew = new UsersMessages(sender,recepient,message);
        messageNew.setSend_at(new Date());
        messageNew.setRead(false);
        userMessagesRepository.save(messageNew);

        return "message added to db";
    }

    @Override
    public List<UsersMessages> getAllMessagesFromUser(Users user) {
        return userMessagesRepository.findAllBySender(user);
    }

    @Override
    public List<UsersMessages> getAllMessagesToUser(Users user) {
        return userMessagesRepository.findAllByRecepient(user);
    }

    @Override
    public List<UsersMessages> getAllUnreadMessagesFromUser(Users user) {
        return userMessagesRepository.findAllBySenderAndReadFalse(user);
    }

    @Override
    public List<UsersMessages> getAllUnreadMessagesToUser(Users user) {
        return userMessagesRepository.findAllByRecepientAndReadFalse(user);
    }


}
