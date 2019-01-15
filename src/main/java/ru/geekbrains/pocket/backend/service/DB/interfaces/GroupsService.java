package ru.geekbrains.pocket.backend.service.DB.interfaces;

import ru.geekbrains.pocket.backend.domain.entitiesDB.Group;
import ru.geekbrains.pocket.backend.domain.entitiesDB.Project;
import ru.geekbrains.pocket.backend.domain.entitiesDB.Users;

import java.util.List;

public interface GroupsService {

    public String createNewGroup(Group group);

    public String deleteGroup(Group group);

    public Group getGroupByID(String id);

    public List<Group> getGroupByName(String name);

    public List<Group> getOpenedGroups();

    public List<Group> getClosedGroups();

    public String updateGroupCreator(Group group, Users newCreator);

    public String updateGroupToBeloningForProject(Group group, Project project);

    public String updateGroupName(Group group,String newName);

    public String updateGroupDescription(Group group, String newDescription);

    public String setGroupToClose();

    public String setGroupToOpen();
}
