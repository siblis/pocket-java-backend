package ru.geekbrains.pocket.backend.service.DB.interfaces;

import ru.geekbrains.pocket.backend.domain.entitiesDB.UsersTokens;

public interface UserDBTokenService {

    public String newUserDBToken(UsersTokens newToken);

    public String deleteUserDBToken(UsersTokens tokenOnDelete);

}
