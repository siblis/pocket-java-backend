package ru.geekbrains.pocket.backend.service;

import ru.geekbrains.pocket.backend.domain.db.Group;
import ru.geekbrains.pocket.backend.domain.db.User;
import ru.geekbrains.pocket.backend.domain.db.UserChat;

public interface UserChatsService {

    public UserChat getUserChatByGroup(Group group);

    public UserChat getUserChatByUser(User user);


}
