package ru.geekbrains.pocket.backend.domain.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;
import java.util.Date;

//this class for class User (collection = "users")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserProfile {
    private String id;

    private String name;

    private String username;

    private String full_name;

    public UserProfile(String username) {
        this.username = username;
    }

    public UserProfile(String id, String username) {
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
