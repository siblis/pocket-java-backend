package ru.geekbrains.pocket.backend.domain.entitiesDB;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Data
@Document(collection = "users.contacts")
public class UsersContacts {

    @Id
    @Field("_id")
    String id;

    @Indexed
    Users user_id;


    Users contact;

    String byName;

    Date addedAt;

}
