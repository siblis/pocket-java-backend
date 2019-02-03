package ru.geekbrains.pocket.backend.domain.pub;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserBlacklist {

    private UserProfilePub user;

    @Override
    public String toString() {
        return "UserBlacklist{" +
                "" + user +
                '}';
    }

}