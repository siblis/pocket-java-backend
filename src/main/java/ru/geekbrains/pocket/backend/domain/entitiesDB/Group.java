package ru.geekbrains.pocket.backend.domain.entitiesDB;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "groups")
public class Group {

    @Id
    @Field("_id")
    String id;

    Users creator;

    Project space = null;

    @Indexed
    String name;

    String description;

    @Indexed
    String inviting_code;

    @Field("public")
    boolean isPublic=false;

}
