package ru.geekbrains.pocket.backend.service;

import org.bson.types.ObjectId;
import ru.geekbrains.pocket.backend.domain.SystemUser;
import ru.geekbrains.pocket.backend.domain.db.*;
import ru.geekbrains.pocket.backend.exception.UserAlreadyExistException;
import ru.geekbrains.pocket.backend.resource.UserResource;

import java.util.Date;
import java.util.List;

public interface UserService { //extends UserDetailsService {

    User changeUserPassword(User user, String password);

    boolean checkIfValidOldPassword(User user, String oldPassword);

    PasswordResetToken createPasswordResetTokenForUser(User user, String token);

    UserToken createVerificationTokenForUser(User user);

    UserToken createVerificationTokenForUser(User user, String token);

    void delete(ObjectId id);

    void delete(String email);

    List<User> getAllUsers();

    List<UserResource> getAllUserResources();

    User getUserById(ObjectId id);

    User getUserByEmail(String email);

    User getUserByToken(String token);

    User getUserByUsername(String userName);

    UserToken getVerificationToken(User user);

    UserToken getVerificationToken(String token);

    UserToken generateNewVerificationToken(String token);

    List<Role> getRolesByUsername(String userName);

    User insert(User user);

    User registerNewUserAccount(SystemUser account)
            throws UserAlreadyExistException;

    User registerNewUserAccount(String email, String password, String name)
            throws UserAlreadyExistException;

    User update(User user);

    public String updateUserProfile(User user, UserProfile userProfile);

    public String updateUserFullName(User user, String firstName);

    public String updateUserUsername(User user, String lastName);

    public String updateUsersLastSeen(User user, Date date);

    public String updateUsersPassword(User user, String password);

    String validatePasswordResetToken(ObjectId id, String token);

    User validateUser(ObjectId id);

    User validateUser(String username);

    String validateVerificationToken(String token);

}
