package ru.geekbrains.pocket.backend.domain.db;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import ru.geekbrains.pocket.backend.enumeration.GroupRole;

@Data
@NoArgsConstructor
@Document(collection = "projects.members")
@TypeAlias("projects.members")
public class ProjectMembers {

    @Id
    ObjectId id;

    @DBRef
    Project project;

    @DBRef
    User member;

    GroupRole role = GroupRole.speacker;
}
