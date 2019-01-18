package ru.geekbrains.pocket.backend.repository.DB;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.geekbrains.pocket.backend.domain.entitiesDB.GroupsMessages;


public interface GroupsMessagesRepository extends MongoRepository<GroupsMessages,String> {
}
