package ru.geekbrains.pocket.backend.domain;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "groups.messages")
public class GroupMessages {

    @Id
    ObjectId id;

    User sender;

    @Indexed
    Group group;

    String text;

    Date sent_at;
}
