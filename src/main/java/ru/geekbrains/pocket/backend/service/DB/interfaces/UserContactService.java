package ru.geekbrains.pocket.backend.service.DB.interfaces;

import ru.geekbrains.pocket.backend.domain.entitiesDB.Users;
import ru.geekbrains.pocket.backend.domain.entitiesDB.UsersContacts;

import java.util.List;

public interface UserContactService {

    public String newUsersContact(UsersContacts newContact);

    public String deleteUsersContact(UsersContacts contactOnDelete);

    public List<UsersContacts> findContactBelongsToUser(Users user);

}
