package ru.geekbrains.pocket.backend.domain.db;

import com.mongodb.lang.Nullable;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.Valid;

@Data
@Document(collection = "users.chats")
public class UserChat {

    @Id
    String id;

    UserProfile sender;

    @DBRef
    @Nullable
    Group group = null;

    @DBRef
    @Indexed
    @Valid
    @Field(value = "user_id")
    private User user;

    String preview;

    Integer unread;

}
