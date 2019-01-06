package ru.geekbrains.pocket.backend.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.geekbrains.pocket.backend.domain.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
    Role findByName(String roleName);
}
