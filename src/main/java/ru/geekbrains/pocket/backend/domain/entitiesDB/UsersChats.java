package ru.geekbrains.pocket.backend.domain.entitiesDB;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "users.chats")
public class UsersChats {

    @Id
    @Field("_id")
    String id;

    Users user_id;

    Message message;
}
