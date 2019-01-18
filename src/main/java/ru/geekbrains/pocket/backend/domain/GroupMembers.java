package ru.geekbrains.pocket.backend.domain;


import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "groups.members")
public class GroupMembers {

    @Id
    ObjectId id;

    Group group;

    User member;

    boolean administrator = false;
}
