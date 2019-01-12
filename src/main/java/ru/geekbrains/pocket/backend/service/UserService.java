package ru.geekbrains.pocket.backend.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.geekbrains.pocket.backend.domain.Role;
import ru.geekbrains.pocket.backend.domain.SystemUser;
import ru.geekbrains.pocket.backend.domain.User;
import ru.geekbrains.pocket.backend.resource.UserResource;

import java.util.Collection;
import java.util.List;

public interface UserService extends UserDetailsService {
    void delete(Long id);

    Iterable<User> getAllUsers();

    List<UserResource> getAllUserResources();

    User getUserById(Long id);

    User getUserByUsername(String userName);

    List<Role> getRolesByUsername(String userName);

    User insert(User user);

    User update(User user);

    User validateUser(Long id);

    User validateUser(String username);
}
