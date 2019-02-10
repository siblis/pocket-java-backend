package ru.geekbrains.pocket.backend.service.impl;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.pocket.backend.domain.db.User;
import ru.geekbrains.pocket.backend.domain.db.UserContact;
import ru.geekbrains.pocket.backend.repository.UserContactRepository;
import ru.geekbrains.pocket.backend.service.UserContactService;

import java.util.List;

@Service
public class UserContactServiselmpl implements UserContactService {
    @Autowired
    private UserContactRepository userContactRepository;

    @Override
    public UserContact createUserContact(UserContact userContact) {
        return userContactRepository.insert(userContact);
    }

    @Override
    public UserContact createUserContact(User user, User contact) {
        UserContact userContact = userContactRepository.findFirstByUserAndContact(user, contact);
        if (userContact == null) {
            userContact = new UserContact(user, contact);
            return userContactRepository.insert(userContact);
        } else
            return userContact;
    }

    @Override
    public UserContact createUserContact(User user, User contact, String byname) {
        UserContact userContact = userContactRepository.findFirstByUserAndContact(user, contact);
        if (userContact == null) {
            userContact = new UserContact(user, contact, byname);
            return userContactRepository.insert(userContact);
        } else
            return userContact;
    }

    @Override
    public void deleteUsersContact(UserContact userContact) {
        userContactRepository.delete(userContact);
    }

    @Override
    public UserContact getUserContact(User user, User contact) {
        return userContactRepository.findFirstByUserAndContact(user, contact);
    }

    @Override
    public UserContact getUserContact(User user, ObjectId idContact) {
        return userContactRepository.findFirstByUserAndContactId(user, idContact);
    }

    @Override
    public List<UserContact> getUserContacts(User user) {
        return userContactRepository.findByUser(user);
    }

    @Override
    public UserContact updateUserContact(UserContact userContact) {
        return userContactRepository.save(userContact);
    }
}
