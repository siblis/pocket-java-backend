package ru.geekbrains.pocket.backend.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.geekbrains.pocket.backend.domain.SystemUser;
import ru.geekbrains.pocket.backend.domain.User;

public interface UserService extends UserDetailsService {
    User findByUserName(String userName);

    User save(SystemUser systemUser);
}
