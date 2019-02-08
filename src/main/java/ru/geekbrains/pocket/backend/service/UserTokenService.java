package ru.geekbrains.pocket.backend.service;

import org.bson.types.ObjectId;
import ru.geekbrains.pocket.backend.domain.db.PasswordResetToken;
import ru.geekbrains.pocket.backend.domain.db.User;
import ru.geekbrains.pocket.backend.domain.db.UserToken;

public interface UserTokenService {

    PasswordResetToken createPasswordResetTokenForUser(User user, String token);

    UserToken createVerificationTokenForUser(User user);

    User getUserByToken(String token);

    UserToken getVerificationToken(User user);

    UserToken getVerificationToken(String token);

    UserToken generateNewVerificationToken(String token);

    UserToken newUserToken(UserToken newToken);

    void deleteUserToken(UserToken tokenOnDelete);

    void deleteAllUserToken();

    String validatePasswordResetToken(ObjectId id, String token);

    String validateVerificationToken(String token);

}
