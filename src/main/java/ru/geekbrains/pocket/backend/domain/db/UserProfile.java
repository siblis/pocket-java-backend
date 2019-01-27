package ru.geekbrains.pocket.backend.domain.db;

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
@TypeAlias("profile")
public class UserProfile {
    @NotNull
    @Indexed(unique = true)
    private String username;

    @Field("full_name")
    private String fullName;

    @Field("last_seen")
    private Date lastSeen;

    public UserProfile(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "profile{" +
                "'username':" + username +
                ", 'fullName':" + fullName +
                ", 'lastSeen':" + lastSeen +
                '}';
    }
}
