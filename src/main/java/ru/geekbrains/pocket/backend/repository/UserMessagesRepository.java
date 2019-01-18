package ru.geekbrains.pocket.backend.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.geekbrains.pocket.backend.domain.User;
import ru.geekbrains.pocket.backend.domain.UserMessages;

import java.util.List;

@Repository
public interface UserMessagesRepository extends MongoRepository<UserMessages, ObjectId> {

    List<UserMessages> findAllByRecepient(User recepient);

    List<UserMessages> findAllBySender(User recepient);

    List<UserMessages> findAllBySenderAndReadFalse(User recepient);

    List<UserMessages> findAllByRecepientAndReadFalse(User recepient);


}
