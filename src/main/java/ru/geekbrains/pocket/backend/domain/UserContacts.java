package ru.geekbrains.pocket.backend.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.Size;
import java.util.Date;

@Data
@NoArgsConstructor
@Document(collection = "users.contacts")
public class UserContacts {

    @Id
    private ObjectId id;

    @Indexed
    @Field(value = "user_id")
    private User userId;

    private User contact;

    @Size(min = 1, max = 50)
    @Field(value = "byname")
    private String byName;

    @Field(value = "added_at")
    private Date addedAt;

    public UserContacts(User userId, User contact, String byName, Date addedAt) {
        this.userId = userId;
        this.contact = contact;
        this.byName = byName;
        this.addedAt = addedAt;
    }

    @Override
    public String toString() {
        return "UsersContacts{" +
                "id=" + id +
                ", userId=" + userId +
                ", contact=" + contact +
                ", byname=" + byName +
                ", added_at=" + addedAt +
                '}';
    }
}
