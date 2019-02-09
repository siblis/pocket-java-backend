package ru.geekbrains.pocket.backend.service.impl;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.pocket.backend.domain.db.Group;
import ru.geekbrains.pocket.backend.domain.db.User;
import ru.geekbrains.pocket.backend.exception.GroupNotFoundException;
import ru.geekbrains.pocket.backend.repository.GroupRepository;
import ru.geekbrains.pocket.backend.service.GroupService;

import java.util.List;
import java.util.Optional;

@Service
public class GroupServiceImpl implements GroupService {
    @Autowired
    private GroupRepository repository;

    @Override
    public Group createGroup(Group group) {
        return repository.insert(group);
    }

    @Override
    public void deleteGroup(Group group) {
        repository.delete(group);
    }

    @Override
    public void deleteAllGroups() {
        repository.deleteAll();
    }

    @Override
    public Group getGroup(ObjectId id) {
        Optional<Group> group = Optional.of(repository.findById(id).orElseThrow(
                () -> new GroupNotFoundException("Group with id = " + id + " not found")));
        return group.get();
    }

    @Override
    public List<Group> getGroups(User creator) {
        return repository.findByCreator(creator);
    }

    @Override
    public List<Group> getGroups(String name) {
        return repository.findByName(name);
    }

    @Override
    public List<Group> getOpenedGroups() {
        return null;
    }

    @Override
    public List<Group> getClosedGroups() {
        return null;
    }

    @Override
    public String setGroupToClose() {
        return null;
    }

    @Override
    public String setGroupToOpen() {
        return null;
    }

    @Override
    public Group updateGroup(Group group) {
        return repository.save(group);
    }

}
