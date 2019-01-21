package ru.geekbrains.pocket.backend.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.geekbrains.pocket.backend.domain.Group;
import ru.geekbrains.pocket.backend.domain.GroupMessage;
import ru.geekbrains.pocket.backend.domain.User;

import java.util.List;

@Repository
public interface GroupMessageRepository extends MongoRepository<GroupMessage, ObjectId> {

    List<GroupMessage> findBySender(User sender);

    List<GroupMessage> findByGroup(Group group);

}
