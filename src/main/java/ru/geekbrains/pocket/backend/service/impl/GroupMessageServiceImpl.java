package ru.geekbrains.pocket.backend.service.impl;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import ru.geekbrains.pocket.backend.domain.Group;
import ru.geekbrains.pocket.backend.domain.GroupMessage;
import ru.geekbrains.pocket.backend.domain.User;
import ru.geekbrains.pocket.backend.exception.GroupMessageNotFoundException;
import ru.geekbrains.pocket.backend.repository.GroupMessageRepository;
import ru.geekbrains.pocket.backend.service.GroupMessageService;

import java.util.List;
import java.util.Optional;

public class GroupMessageServiceImpl implements GroupMessageService {
    @Autowired
    private GroupMessageRepository repository;

    @Override
    public GroupMessage createMessage(GroupMessage groupMessage) {
        return repository.insert(groupMessage);
    }

    @Override
    public void deleteMessage(GroupMessage groupMessage) {
        repository.delete(groupMessage);
    }

    @Override
    public GroupMessage getMessage(ObjectId id) {
        Optional<GroupMessage> groupMessage = Optional.of(repository.findById(id).orElseThrow(
                () -> new GroupMessageNotFoundException("Group message with id = " + id + " not found")));
        return groupMessage.get();
    }

    @Override
    public List<GroupMessage> getMessagesBySender(User sender) {
        return repository.findBySender(sender);
    }

    @Override
    public List<GroupMessage> getMessagesByGroup(Group group) {
        return repository.findByGroup(group);
    }

    @Override
    public GroupMessage updateMessage(GroupMessage groupMessage) {
        return repository.save(groupMessage);
    }

}
