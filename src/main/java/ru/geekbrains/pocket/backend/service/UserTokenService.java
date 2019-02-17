package ru.geekbrains.pocket.backend.service;

import org.bson.types.ObjectId;
import ru.geekbrains.pocket.backend.domain.db.PasswordResetToken;
import ru.geekbrains.pocket.backend.domain.db.User;
import ru.geekbrains.pocket.backend.domain.db.UserToken;
import ru.geekbrains.pocket.backend.enumeration.TokenStatus;
import ru.geekbrains.pocket.backend.exception.TokenNotFoundException;

public interface UserTokenService {

    PasswordResetToken createPasswordResetTokenForUser(User user, String token);

    UserToken createOrUpdateTokenForUser(User user);

    User getUserByToken(String token);

    UserToken getNewToken(User user);

    UserToken getUserToken(User user);

    UserToken getUserToken(String token);

    UserToken getValidToken(User user);

    UserToken updateUserToken(UserToken userToken);

    UserToken updateUserToken(String token) throws TokenNotFoundException;

    void deleteUserToken(UserToken tokenOnDelete);

    void deleteAllUserToken();

    String validatePasswordResetToken(ObjectId id, String token);

    TokenStatus getTokenStatus(String token);

}
