package ru.geekbrains.pocket.backend.service;

import ru.geekbrains.pocket.backend.domain.Role;

public interface RoleService {
    Role findByName(String roleName);

    Role save(Role role);
}
