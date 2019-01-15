package ru.geekbrains.pocket.backend.repository.DB;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.geekbrains.pocket.backend.domain.entitiesDB.Users;
import ru.geekbrains.pocket.backend.domain.entitiesDB.UsersMessages;

import java.util.List;


public interface UserMessagesRepository extends MongoRepository<UsersMessages,String> {

    List<UsersMessages> findAllByRecepient(Users recepient);

    List<UsersMessages> findAllBySender(Users recepient);

    List<UsersMessages> findAllBySenderAndReadFalse(Users recepient);

    List<UsersMessages> findAllByRecepientAndReadFalse(Users recepient);


}
