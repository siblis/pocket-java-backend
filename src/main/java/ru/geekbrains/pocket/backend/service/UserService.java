package ru.geekbrains.pocket.backend.service;

import org.bson.types.ObjectId;
import org.springframework.security.core.userdetails.UserDetailsService;
import ru.geekbrains.pocket.backend.domain.Role;
import ru.geekbrains.pocket.backend.domain.SystemUser;
import ru.geekbrains.pocket.backend.domain.User;
import ru.geekbrains.pocket.backend.resource.UserResource;

import java.util.Collection;
import java.util.List;

public interface UserService extends UserDetailsService {
    void delete(ObjectId id);

    List<User> getAllUsers();

    List<UserResource> getAllUserResources();

    User getUserById(ObjectId id);

    User getUserByUsername(String userName);

    List<Role> getRolesByUsername(String userName);

    User insert(User user);

    User update(User user);

    User validateUser(ObjectId id);

    User validateUser(String username);
}
