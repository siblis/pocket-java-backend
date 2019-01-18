package ru.geekbrains.pocket.backend.domain;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import ru.geekbrains.pocket.backend.enumeration.GroupRole;

@Data
@Document(collection = "projects.members")
@TypeAlias("projects.members")
public class ProjectMembers {

    @Id
    ObjectId id;

    Project project;

    User member;

    GroupRole role = GroupRole.speacker;
}
