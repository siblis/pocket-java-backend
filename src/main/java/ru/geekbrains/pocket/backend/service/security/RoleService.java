package ru.geekbrains.pocket.backend.service.security;

import ru.geekbrains.pocket.backend.domain.Security.Role;

import java.util.Collection;

public interface RoleService {
    Role getRoleByName(String roleName);

    Role save(Role role);
}
