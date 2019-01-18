package ru.geekbrains.pocket.backend.domain.entitiesDB;

import lombok.Data;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "projects.members")
@TypeAlias("projects.members")
public class ProjectMembers {

    String id;

    Project project;

    Users member;

    GroupRole role = GroupRole.speacker;
}
