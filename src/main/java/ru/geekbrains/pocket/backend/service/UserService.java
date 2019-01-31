package ru.geekbrains.pocket.backend.service;

import org.bson.types.ObjectId;
import org.springframework.security.core.userdetails.UserDetailsService;
import ru.geekbrains.pocket.backend.domain.db.Role;
import ru.geekbrains.pocket.backend.domain.db.User;
import ru.geekbrains.pocket.backend.domain.db.UserProfile;
import ru.geekbrains.pocket.backend.resource.UserResource;

import java.util.Date;
import java.util.List;

public interface UserService extends UserDetailsService {
    void delete(ObjectId id);

    void delete(String email);

    List<User> getAllUsers();

    List<UserResource> getAllUserResources();

    User getUserById(ObjectId id);

    User getUserByEmail(String email);

    User getUserByUsername(String userName);

    List<Role> getRolesByUsername(String userName);

    User insert(User user);

    User update(User user);

    User validateUser(ObjectId id);

    User validateUser(String username);


    public String addNewUser(User user);

    public String updateUser(User user);

    public String updateUserProfile(User user, UserProfile userProfile);

    public String updateUserFullName(User user, String firstName);

    public String updateUserUsername(User user, String lastName);

    public String updateUsersLastSeen(User user, Date date);

    public String updateUsersPassword(User user, String password);

}
