package ru.geekbrains.pocket.backend.service.DB.interfaces;

import org.springframework.stereotype.Service;
import ru.geekbrains.pocket.backend.domain.entitiesDB.Profile;
import ru.geekbrains.pocket.backend.domain.entitiesDB.Users;


import java.util.Date;

@Service
public interface UserService {

    public String addNewUser(Users user);

    public String deleteUser(Users user);

    public Users findUserByID(String id);

    public Users findByEmail(String email);

    public String updateUser(Users user);

    public String updateUserProfile(Users user, Profile profile);

    public String updateUserFirstName(Users user, String firstName);

    public String updateUserLastName(Users user, String lastName);

    public String updateUsersLastSeen(Users user, Date date);

    public String updateUsersPassword(Users user, String password);




}
