package ru.geekbrains.pocket.backend.service;

import ru.geekbrains.pocket.backend.domain.db.User;
import ru.geekbrains.pocket.backend.domain.db.UserContact;

import java.util.List;

public interface UserContactService {

    UserContact createUserContact(UserContact newContact);

    UserContact createUserContact(User user, User contact);

    void deleteUsersContact(UserContact contactOnDelete);

    UserContact getContact(User user, User contact);

    List<UserContact> getContacts(User user);

}
