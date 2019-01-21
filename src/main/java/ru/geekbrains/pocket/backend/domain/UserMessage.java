package ru.geekbrains.pocket.backend.domain;

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
@Document(collection = "users.messages")
public class UserMessage {

    @Id
    private ObjectId id;

    @Indexed
    @NotNull
    private User sender; //отправитель

    @Indexed
    @NotNull
    private User recepient; //получатель

    @NotEmpty
    private String text;

    @Nullable
    private Attachment attachment = null;

    private boolean read;

    private Date sent_at;

    public UserMessage(User sender, User recepient, String text) {
        this.sender = sender;
        this.recepient = recepient;
        this.text = text;
    }

    @Override
    public String toString() {
        return "UserMessage{" +
                "id=" + id +
                ", sender=" + sender.getUsername() +
                ", recepient=" + recepient.getUsername() +
                ", text=" + text +
                ", sent_at=" + sent_at +
                '}';
    }
}
