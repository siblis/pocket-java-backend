package ru.geekbrains.pocket.backend.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import ru.geekbrains.pocket.backend.domain.UserChat;

public interface UserChatsRepository extends MongoRepository<UserChat, ObjectId> {
}
