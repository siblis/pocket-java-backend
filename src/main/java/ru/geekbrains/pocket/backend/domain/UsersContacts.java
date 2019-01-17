package ru.geekbrains.pocket.backend.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.util.Date;

import javax.validation.constraints.Size;


    @Getter
    @Setter
    @NoArgsConstructor
    @Document(collection = "users.contacts")
    public class UsersContacts {
        @Id
        @Field(value = "_id")
        private ObjectId id;

        @Getter
        @Setter
        @Indexed(unique = true)
        @Field(value = "<users:_id>")
        private String userId;

        @Field(value = "<users:_id>")
        private String contact;

        @Getter
        @Setter
        @Size(min = 1, max = 50)
        //@Pattern(regexp = "[^0-9]*")
        @Field(value = "byName")
        private String byName;

        @Getter
        @Setter
        @Field(value = "added_at")
        private Date addedAt;

        public UsersContacts(ObjectId id, String userId, String contact, String byName, Date addedAt) {
            this.id = id;
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
                    ", byName=" + byName +
                    ", added_at=" + addedAt +
                    '}';
        }
    }

