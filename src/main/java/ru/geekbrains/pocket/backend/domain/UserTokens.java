package ru.geekbrains.pocket.backend.domain;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "users.tokens")
public class UserTokens {

    @Id
    ObjectId id;

    User user_id;

    @Indexed
    String unique_token;

    String user_ip;

    String agent;

    Date date;


}
