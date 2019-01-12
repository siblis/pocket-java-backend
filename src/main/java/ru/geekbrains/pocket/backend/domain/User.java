package ru.geekbrains.pocket.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import ru.geekbrains.pocket.backend.validation.ValidEmail;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "users")
@CompoundIndexes({
        @CompoundIndex(name = "name_email_idx", def = "{'username': 1, 'email': -1}")
})
public class User {
    @Id
    @Field(value = "id")
    private Long id;

    @Indexed(unique = true)
    @Field(value = "username")
    private String username;

    @JsonIgnore
    @Field(value = "password")
    private String password;

    @Size(min = 1, max = 50)
    //@Pattern(regexp = "[^0-9]*")
    @Field(value = "lastname")
    private String lastname;

    @Size(min = 1, max = 50)
    //@Pattern(regexp = "[^0-9]*")
    @Field(value = "firstname")
    private String firstname;

    @NotEmpty
    @ValidEmail
    @Indexed
    @Field(value = "email")
    private String email;

    @DBRef
    @Field(value = "roles")
    private Collection<Role> roles;

    public User(String username, String password, @NotEmpty String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public User(String username, String password, String lastname, String firstname, String email) {
        this.username = username;
        this.password = password;
        this.lastname = lastname;
        this.firstname = firstname;
        this.email = email;
    }

    public User(String username, String password, String lastname, String firstname, String email, Collection<Role> roles) {
        this.username = username;
        this.password = password;
        this.lastname = lastname;
        this.firstname = firstname;
        this.email = email;
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username=" + username +
                ", lastname=" + lastname +
                ", firstname=" + firstname +
                ", email=" + email +
                '}';
    }
}
