package ru.geekbrains.pocket.backend.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import ru.geekbrains.pocket.backend.domain.db.User;
import ru.geekbrains.pocket.backend.domain.db.UserContacts;

import java.util.List;

@Repository
public interface UserContactsRepository extends MongoRepository<UserContacts, ObjectId> {
    List<UserContacts> findByUser(User user);

}
