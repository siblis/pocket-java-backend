package ru.geekbrains.pocket.backend.domain.entitiesDB;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Data
@Document(collection = "users")
@TypeAlias("users")
public class Users {

    @Id
    @Field("_id")
    private String id;
    private String email;
    private String password;
    private Date created_at;

    @Field(value = "profile")
    private Profile profile;

    public Users(){}

    public Profile getProfile() {
        return profile;
    }

    public Users(String email, String password, Profile profile) {
        this.email = email;
        this.password = password;
        this.created_at = new Date();
        this.profile = profile;
    }
}
