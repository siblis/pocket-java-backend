package ru.geekbrains.pocket.backend.service;

import org.bson.types.ObjectId;
import ru.geekbrains.pocket.backend.domain.Group;
import ru.geekbrains.pocket.backend.domain.Project;
import ru.geekbrains.pocket.backend.domain.User;

import java.util.List;

public interface GroupService {

    public String createNewGroup(Group group);

    public String deleteGroup(Group group);

    public Group getGroupByID(ObjectId id);

    public List<Group> getGroupByName(String name);

    public List<Group> getOpenedGroups();

    public List<Group> getClosedGroups();

    public String updateGroupCreator(Group group, User newCreator);

    public String updateGroupToBeloningForProject(Group group, Project project);

    public String updateGroupName(Group group,String newName);

    public String updateGroupDescription(Group group, String newDescription);

    public String setGroupToClose();

    public String setGroupToOpen();
}
