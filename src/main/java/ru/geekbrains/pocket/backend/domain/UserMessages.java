package ru.geekbrains.pocket.backend.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@NoArgsConstructor
@Document(collection = "users.messages")
public class UserMessages {

    @Id
    ObjectId _id;

    User sender;

    User recepient;

    String text;

    boolean read;

    Date send_at;

    public UserMessages(User sender, User recepient, String text) {
        this.sender = sender;
        this.recepient = recepient;
        this.text = text;
    }
}
