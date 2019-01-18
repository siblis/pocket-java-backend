package ru.geekbrains.pocket.backend.domain;

import com.mongodb.lang.Nullable;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "users.chats")
public class UserChats {

    @Id
    String id;

    @Indexed
    User user_id;

    @Nullable
    Group group = null;

    User sender;

    String preview;

    Integer unread;

}
