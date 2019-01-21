package ru.geekbrains.pocket.backend.service;

import org.bson.types.ObjectId;
import ru.geekbrains.pocket.backend.domain.Group;
import ru.geekbrains.pocket.backend.domain.GroupMessage;
import ru.geekbrains.pocket.backend.domain.User;

import java.util.List;

public interface GroupMessageService {

    GroupMessage createMessage(GroupMessage groupMessage);

    void deleteMessage(GroupMessage groupMessage);

    GroupMessage getMessage(ObjectId id);

    List<GroupMessage> getMessagesBySender(User sender);

    List<GroupMessage> getMessagesByGroup(Group group);

    GroupMessage updateMessage(GroupMessage groupMessage);

}
