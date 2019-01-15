package ru.geekbrains.pocket.backend.domain.entitiesDB;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "projects")
@TypeAlias("projects")
public class Project {

    @Id
    @Field("_id")
    String id;


    Users creator;

    String name;

    String description;
}
