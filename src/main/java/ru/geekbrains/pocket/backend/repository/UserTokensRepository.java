package ru.geekbrains.pocket.backend.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import ru.geekbrains.pocket.backend.domain.UserTokens;

public interface UserTokensRepository extends MongoRepository<UserTokens, ObjectId> {
}
