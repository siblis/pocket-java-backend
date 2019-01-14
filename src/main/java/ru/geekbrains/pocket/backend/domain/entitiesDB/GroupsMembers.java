package ru.geekbrains.pocket.backend.domain.entitiesDB;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
@Document(collection = "groups.members")
public class GroupsMembers {

    @Id
    @Field("_id")
    String id;

    Group group;

    List<Users> member;
}
