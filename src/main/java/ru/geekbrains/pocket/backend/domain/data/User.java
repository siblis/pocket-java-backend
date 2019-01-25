package ru.geekbrains.pocket.backend.domain.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.geekbrains.pocket.backend.domain.db.UserProfile;
import ru.geekbrains.pocket.backend.util.validation.ValidEmail;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String id;

    @NotEmpty
    @ValidEmail
    private String email;

    private UserProfile profile;

    public User(String email, UserProfile userProfile) {
        this.email = email;
        this.profile = userProfile;
    }

    @Override
    public String toString() {
        return "User{" +
                "'id':" + id +
                ", 'email':" + email +
                ", " + profile +
                '}';
    }

}