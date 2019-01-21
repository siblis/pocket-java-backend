package ru.geekbrains.pocket.backend.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.geekbrains.pocket.backend.domain.User;
import ru.geekbrains.pocket.backend.domain.UserMessage;

import java.util.List;

@Repository
public interface UserMessageRepository extends MongoRepository<UserMessage, ObjectId> {

    List<UserMessage> findBySender(User sender);

    List<UserMessage> findByRecepient(User recepient);

    List<UserMessage> findBySenderAndReadFalse(User recepient);

    List<UserMessage> findByRecepientAndReadFalse(User recepient);

}
