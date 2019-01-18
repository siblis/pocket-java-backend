package ru.geekbrains.pocket.backend.domain.entitiesDB;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Data
@Document(collection = "users.tokens")
public class UsersTokens {

    @Id
    @Field("_id")
    String id;

    Users user;

    @Indexed
    String unique_token;

    String user_ip;

    String agent;

    Date date;


}
