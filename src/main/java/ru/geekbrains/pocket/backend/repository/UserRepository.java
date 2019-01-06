package ru.geekbrains.pocket.backend.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.geekbrains.pocket.backend.domain.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String userName);
}
