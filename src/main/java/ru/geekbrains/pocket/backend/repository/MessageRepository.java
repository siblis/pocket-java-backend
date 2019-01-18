package ru.geekbrains.pocket.backend.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.geekbrains.pocket.backend.domain.Message;
import ru.geekbrains.pocket.backend.domain.User;

import java.util.List;

@Repository
public interface MessageRepository extends MongoRepository<Message, ObjectId> {

    List<Message> findAllByRecepient(User recepient);

    List<Message> findAllBySender(User recepient);

    List<Message> findAllBySenderAndReadFalse(User recepient);

    List<Message> findAllByRecepientAndReadFalse(User recepient);


}
