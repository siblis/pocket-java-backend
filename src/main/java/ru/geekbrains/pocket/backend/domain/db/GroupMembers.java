package ru.geekbrains.pocket.backend.domain.db;


import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "groups.members")
public class GroupMembers {

    @Id
    ObjectId id;

    @DBRef
    Group group;

    @DBRef
    User member;

    boolean administrator = false;
}
