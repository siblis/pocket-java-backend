package ru.geekbrains.pocket.backend.service.DB.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.pocket.backend.domain.entitiesDB.Users;
import ru.geekbrains.pocket.backend.domain.entitiesDB.UsersMessages;
import ru.geekbrains.pocket.backend.repository.DB.UserMessagesRepository;


import java.util.Date;

@Service
public class MessagesServiceImpl{

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




}
