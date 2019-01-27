package ru.geekbrains.pocket.backend.domain.db;

import com.mongodb.lang.Nullable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "groups.messages")
public class GroupMessage {

    @Id
    private ObjectId id;

    @NotNull
    private User sender;

    @Indexed
    @NotNull
    private Group group;

    @NotEmpty
    private String text;

    private Object in_thread;

    private ObjectId belongs_to;

    @Nullable
    private Attachment attachment;

    private Date sent_at;

    public GroupMessage(User sender, Group group, String text) {
        this.sender = sender;
        this.group = group;
        this.text = text;
    }

    @Override
    public String toString() {
        return "GroupMessage{" +
                "id=" + id +
                ", sender=" + sender.getUsername() +
                ", group=" + group.getName() +
                ", text=" + text +
                ", sent_at=" + sent_at +
                '}';
    }

}
