package ru.geekbrains.pocket.backend.service;

import ru.geekbrains.pocket.backend.domain.db.Role;

//this class for Spring Security

public interface RoleService {
    Role getRole(String roleName);

    Role insert(Role role);

    Role save(Role role);
}
