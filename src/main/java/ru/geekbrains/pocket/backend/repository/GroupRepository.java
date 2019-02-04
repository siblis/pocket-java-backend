package ru.geekbrains.pocket.backend.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.geekbrains.pocket.backend.domain.db.Group;
import ru.geekbrains.pocket.backend.domain.db.User;

import java.util.List;

@Repository
public interface GroupRepository extends MongoRepository<Group, ObjectId> {

    List<Group> findByName(String name);

    List<Group> findByCreator(User creator);

}
