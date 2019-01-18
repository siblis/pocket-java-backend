package ru.geekbrains.pocket.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mongodb.lang.Nullable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import ru.geekbrains.pocket.backend.util.validation.ValidEmail;

import javax.validation.constraints.NotEmpty;
import java.util.Collection;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "users")
@TypeAlias("users")
public class User {
    @Id
    private ObjectId id;

    @Nullable
    @NotEmpty
    @ValidEmail
    @Indexed(unique = true)
    private String email;

    @NotEmpty
    @JsonIgnore
    private String password;

    private Date created_at;

    @DBRef
    @Field(value = "profile")
    private UserProfile profile;// = new UserProfile();

    @Nullable
    @Indexed(unique = true)
    private String username;

    @DBRef
    @Nullable
    @Field(value = "roles")
    private Collection<Role> roles;

    public User(String email, String password, UserProfile userProfile) {
        this.email = email;
        this.username = userProfile.getUsername();
        this.password = password;
        this.created_at = new Date();
        this.profile = userProfile;
    }

    public User(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.created_at = new Date();
        //this.profile = new UserProfile(username);
    }

    public User(String email, String username, String password, Collection<Role> roles) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.created_at = new Date();
        //this.profile = new UserProfile(username);
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email=" + email +
                ", username=" + username +
                '}';
    }
}
