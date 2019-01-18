package ru.geekbrains.pocket.backend.repository.DB;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.geekbrains.pocket.backend.domain.entitiesDB.UsersContacts;


public interface UsersContactsRepository extends MongoRepository<UsersContacts,String> {
}
