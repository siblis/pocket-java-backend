package ru.geekbrains.pocket.backend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.pocket.backend.domain.Role;
import ru.geekbrains.pocket.backend.repository.RoleRepository;
import ru.geekbrains.pocket.backend.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {
    private RoleRepository repository;

    @Autowired
    public RoleServiceImpl(RoleRepository repository) {
        this.repository = repository;
    }

    @Override
    public Role findByName(String roleName) {
        return repository.findByName(roleName);
    }

    @Override
    public Role save(Role role) {
        return repository.save(role);
    }
}
