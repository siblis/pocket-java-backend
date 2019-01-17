package ru.geekbrains.pocket.backend.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.Size;


    @Getter
    @Setter
    @NoArgsConstructor
    @Document(collection = "users.contacts")
    public class UsersContacts {
        @Id
        @Field(value = "id")
        private ObjectId id;

        @Getter
        @Setter
        @Indexed(unique = true)
        @Field(value = "<users:_id>")
        private String user_id;

        @Field(value = "<users:_id>")
        private String contact;

        @Getter
        @Setter
        @Size(min = 1, max = 50)
        //@Pattern(regexp = "[^0-9]*")
        @Field(value = "byname")
        private String byname;

        @Getter
        @Setter
        @Field(value = "added_at")
        private String added_at;

        public UsersContacts(ObjectId id,String user_id,String contact,String byname,String added_at) {
            this.id = id;
            this.user_id = user_id;
            this.contact = contact;
            this.byname = byname;
            this.added_at = added_at;
        }
        @Override
        public String toString() {
            return "UsersContacts{" +
                    "id=" + id +
                    ", user_id=" + user_id +
                    ", contact=" + contact +
                    ", byname=" + byname +
                    ", added_at=" + added_at +
                    '}';
        }
    }

