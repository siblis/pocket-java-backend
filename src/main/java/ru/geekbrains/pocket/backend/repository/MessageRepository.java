package ru.geekbrains.pocket.backend.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.geekbrains.pocket.backend.domain.User;
import ru.geekbrains.pocket.backend.domain.UserMessage;

import java.util.List;

@Repository
public interface MessageRepository extends MongoRepository<UserMessage, ObjectId> {

    List<UserMessage> findAllByRecepient(User recepient);

    List<UserMessage> findAllBySender(User recepient);

    List<UserMessage> findAllBySenderAndReadFalse(User recepient);

    List<UserMessage> findAllByRecepientAndReadFalse(User recepient);


}
