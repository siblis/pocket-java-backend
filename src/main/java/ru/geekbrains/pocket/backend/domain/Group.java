package ru.geekbrains.pocket.backend.domain;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "groups")
public class Group {

    @Id
    ObjectId id;

    User creator;

    Project space = null;

    @Indexed
    String name;

    String description;

    @Indexed
    String invitation_code;

    @Field("public")
    boolean isPublic=false;

}
