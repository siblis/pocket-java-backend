package ru.geekbrains.pocket.backend.service.DB.interfaces;

import ru.geekbrains.pocket.backend.domain.entitiesDB.Group;
import ru.geekbrains.pocket.backend.domain.entitiesDB.Message;
import ru.geekbrains.pocket.backend.domain.entitiesDB.Users;
import ru.geekbrains.pocket.backend.domain.entitiesDB.UsersChats;

public interface UserChatsService {

    public UsersChats getUserChatByGroup(Group group);

    public UsersChats getUserChatByUser(Users user);



}
