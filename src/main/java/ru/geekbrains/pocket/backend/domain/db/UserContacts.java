package ru.geekbrains.pocket.backend.domain.db;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@NoArgsConstructor
@Document(collection = "users.contacts")
public class UserContacts {

    @Id
    private ObjectId id;

    @DBRef
    @Indexed
    @Valid
    @Field(value = "user_id")
    private User user;

    @DBRef
    private User contact;

    @Size(min = 1, max = 50)
    @Field(value = "byname")
    private String byName;

    @Field(value = "added_at")
    private Date addedAt;

    public UserContacts(User user, User contact, String byName, Date addedAt) {
        this.user = user;
        this.contact = contact;
        this.byName = byName;
        this.addedAt = addedAt;
    }

    @Override
    public String toString() {
        return "UsersContacts{" +
                "id=" + id +
                ", user=" + user +
                ", contact=" + contact +
                ", byname=" + byName +
                ", added_at=" + addedAt +
                '}';
    }
}
