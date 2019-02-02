package ru.geekbrains.pocket.backend.domain.db;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import java.util.Date;

@Data
@Document(collection = "users.tokens")
public class UserTokens {

    @Id
    ObjectId id;

    @DBRef
    @Valid
    User user;

    @Indexed
    String unique_token;

    String user_ip;

    String agent;

    Date date;


}
