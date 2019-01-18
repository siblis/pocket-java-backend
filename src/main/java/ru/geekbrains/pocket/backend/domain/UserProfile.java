package ru.geekbrains.pocket.backend.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

//this class for class User (collection = "users")

@Getter
@Setter
@TypeAlias("profile")
public class UserProfile {
    @Indexed//(unique = true)
            String username;

    @Field("full_name")
    String fullName;

    @Field("last_seen")
    Date lastSeen;

    public UserProfile(String username) {
        this.username = username;
    }

    public UserProfile(String username, String fullName, Date lastSeen) {
        this.username = username;
        this.fullName = fullName;
        this.lastSeen = lastSeen;
    }

    @Override
    public String toString() {
        return "User.profile{" +
                "username=" + username +
                ", fullName=" + fullName +
                ", lastSeen=" + lastSeen +
                '}';
    }
}
