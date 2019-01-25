package ru.geekbrains.pocket.backend.service;

import ru.geekbrains.pocket.backend.domain.db.UserTokens;

public interface UserTokensService {

    public String newUserDBToken(UserTokens newToken);

    public String deleteUserDBToken(UserTokens tokenOnDelete);

}
