package ru.geekbrains.pocket.backend.domain.pub;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//this class for class User (collection = "users")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserProfilePub {
    private String id;

    private String name;

    private String username;

    private String full_name;

    public UserProfilePub(String username) {
        this.username = username;
    }

    public UserProfilePub(String id, String username) {
        this.id = id;
        this.username = username;
    }

    @Override
    public String toString() {
        return "profile{" +
                "'id':" + id +
                "'name':" + name +
                "'username':" + username +
                ", 'full_name':" + full_name +
                '}';
    }
}
