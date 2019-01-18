package ru.geekbrains.pocket.backend.domain.entitiesDB;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@NoArgsConstructor
@Document(collection = "users.messages")
public class UsersMessages {

    @Id
    String _id;

    Users sender;

    Users recepient;

    String text;

    boolean read;

    Date send_at;

    public UsersMessages(Users sender, Users recepient, String text) {
        this.sender = sender;
        this.recepient = recepient;
        this.text = text;
    }
}
