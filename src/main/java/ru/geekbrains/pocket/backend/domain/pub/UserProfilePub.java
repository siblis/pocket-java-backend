package ru.geekbrains.pocket.backend.domain.pub;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.geekbrains.pocket.backend.domain.db.User;

import javax.validation.constraints.NotNull;

//this class for class User (collection = "users")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserProfilePub {
    private String id;

    private String username;

    private String full_name;

    public UserProfilePub(@NotNull User user) {
        this.id = user.getId().toString();
        this.username = user.getProfile().getUsername();
        this.full_name = user.getProfile().getFullName();
    }

    @Override
    public String toString() {
        return "profile{" +
                "'id':" + id +
                "'username':" + username +
                ", 'full_name':" + full_name +
                '}';
    }
}
