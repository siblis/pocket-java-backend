package ru.geekbrains.pocket.backend.repository.DB;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.geekbrains.pocket.backend.domain.entitiesDB.UsersMessages;


public interface UserMessagesRepository extends MongoRepository<UsersMessages,String> {


}
