package ru.geekbrains.pocket.backend.domain;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "projects")
@TypeAlias("projects")
public class Project {

    @Id
    ObjectId id;

    User creator;

    String name;

    String description;
}
