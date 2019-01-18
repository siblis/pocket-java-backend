package ru.geekbrains.pocket.backend.service;

import ru.geekbrains.pocket.backend.domain.Group;
import ru.geekbrains.pocket.backend.domain.User;
import ru.geekbrains.pocket.backend.domain.UserChat;

public interface UserChatsService {

    public UserChat getUserChatByGroup(Group group);

    public UserChat getUserChatByUser(User user);


}
