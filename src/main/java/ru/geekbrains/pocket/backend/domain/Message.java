package ru.geekbrains.pocket.backend.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@NoArgsConstructor
@Document(collection = "users.messages")
public class Message {

    @Id
    ObjectId _id;

    @Indexed
    User sender; //отправитель

    @Indexed
    User recepient; //получатель

    Group group;

    String text;

    Attachment attachment = null;

    boolean read;

    Date send_at;

    public Message(User sender, User recepient, String text) {
        this.sender = sender;
        this.recepient = recepient;
        this.text = text;
    }
}
