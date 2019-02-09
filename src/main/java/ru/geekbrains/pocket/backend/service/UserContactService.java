package ru.geekbrains.pocket.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import ru.geekbrains.pocket.backend.domain.db.User;
import ru.geekbrains.pocket.backend.domain.db.UserContacts;
import ru.geekbrains.pocket.backend.repository.UserContactsRepository;

import java.util.List;

public interface UserContactService {

    public String newUsersContact(UserContacts newContact);

    public String deleteUsersContact(UserContacts contactOnDelete);

    public List<UserContacts> findContactBelongsToUser(User user);

}
