package ru.geekbrains.pocket.backend.repository.DB;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.geekbrains.pocket.backend.domain.entitiesDB.Users;

import java.util.List;
import java.util.Optional;


public interface UsersRepository extends MongoRepository<Users,String> {

    Optional<Users> findByEmailMatches(String email);

    List<Users> findByProfile_Username(String _Username);



}
