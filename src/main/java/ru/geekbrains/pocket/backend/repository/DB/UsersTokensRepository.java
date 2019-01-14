package ru.geekbrains.pocket.backend.repository.DB;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.geekbrains.pocket.backend.domain.entitiesDB.UsersTokens;


public interface UsersTokensRepository extends MongoRepository<UsersTokens,String> {
}
