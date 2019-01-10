package ru.geekbrains.pocket.backend.repository;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.geekbrains.pocket.backend.domain.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Cacheable()
    Optional<User> findByUsername(String userName);

    @Cacheable()
    @Query(value = "SELECT u from User u join fetch u.roles where u.username = :username")
    Optional<User> findUserByUsernameWithRoles(@Param("username") String username);

}
