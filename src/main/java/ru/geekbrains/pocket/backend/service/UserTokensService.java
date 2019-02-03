package ru.geekbrains.pocket.backend.service;

import ru.geekbrains.pocket.backend.domain.db.UserToken;

public interface UserTokensService {

    public String newUserDBToken(UserToken newToken);

    public String deleteUserDBToken(UserToken tokenOnDelete);

}
