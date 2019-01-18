package ru.geekbrains.pocket.backend.domain;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "users.contacts")
public class UserContacts {

    @Id
    ObjectId id;

    @Indexed
    User user_id;

    User contact;

    String byname;

    Date added_at;

}
