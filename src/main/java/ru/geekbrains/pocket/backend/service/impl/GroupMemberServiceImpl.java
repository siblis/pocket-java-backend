package ru.geekbrains.pocket.backend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.pocket.backend.domain.db.Group;
import ru.geekbrains.pocket.backend.domain.db.GroupMember;
import ru.geekbrains.pocket.backend.domain.db.User;
import ru.geekbrains.pocket.backend.enumeration.RoleGroupMember;
import ru.geekbrains.pocket.backend.repository.GroupMemberRepository;
import ru.geekbrains.pocket.backend.service.GroupMemberService;

import java.util.List;

@Service
public class GroupMemberServiceImpl implements GroupMemberService {
    @Autowired
    private GroupMemberRepository repository;

    @Override
    public GroupMember createGroupMember(GroupMember groupMember) {
        return repository.insert(groupMember);
    }

    @Override
    public GroupMember createGroupMember(Group group, User member, RoleGroupMember role) {
        GroupMember groupMember = new GroupMember(group, member, role);
        return repository.insert(groupMember);
    }

    @Override
    public void deleteAllGroupMembers() {
        repository.deleteAll();
    }

    @Override
    public void deleteGroupMember(GroupMember groupMember) {
        repository.delete(groupMember);
    }

    @Override
    public GroupMember getGroupMember(Group group, User member) {
        return repository.findFirstByGroupAndMember(group, member);
    }

    @Override
    public List<GroupMember> getGroupMembers(Group group) {
        return repository.findByGroup(group);
    }

    @Override
    public List<GroupMember> getGroupMembers(User member) {
        return repository.findByMember(member);
    }

    @Override
    public GroupMember updateGroupMember(GroupMember groupMember) {
        return repository.save(groupMember);
    }
}
