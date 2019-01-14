package ru.geekbrains.pocket.backend.domain.entitiesDB;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Data
@Document(collection = "groups.messages")
public class GroupsMessages {

    @Id
    @Field("_id")
    String id;

    Users sender;

    @Indexed
    Group group;

    String text;

    Date sended_at;
}
