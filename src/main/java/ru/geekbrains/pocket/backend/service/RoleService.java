package ru.geekbrains.pocket.backend.service;

import ru.geekbrains.pocket.backend.domain.Role;

//this class for Spring Security

public interface RoleService {
    Role getRoleByName(String roleName);

    Role save(Role role);
}
