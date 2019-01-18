package ru.geekbrains.pocket.backend.domain;

import com.mongodb.lang.Nullable;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "users.chats")
public class UserChat {

    @Id
    String id;

    UserProfile sender;

    @Nullable
    Group group = null;
    @Indexed
    @Field(value = "user_id")
    private User userId;

    String preview;

    Integer unread;

}
