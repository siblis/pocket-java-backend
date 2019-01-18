package ru.geekbrains.pocket.backend.domain.entitiesDB;

import com.mongodb.lang.Nullable;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Field;


@Data
@RequiredArgsConstructor()
public class Message {

    @Id
    @Field("_id")
    String id;

    @Nullable
    Group group = null;


    Users sender;

    String preview;

    Integer unread;
}
