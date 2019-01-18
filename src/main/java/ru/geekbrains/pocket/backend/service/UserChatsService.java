package ru.geekbrains.pocket.backend.service;

import ru.geekbrains.pocket.backend.domain.Group;
import ru.geekbrains.pocket.backend.domain.User;
import ru.geekbrains.pocket.backend.domain.UserChats;

public interface UserChatsService {

    public UserChats getUserChatByGroup(Group group);

    public UserChats getUserChatByUser(User user);


}
