package ru.geekbrains.pocket.backend.domain.pub;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.geekbrains.pocket.backend.util.validation.ValidEmail;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserPub {
    private String id;

    @NotEmpty
    @ValidEmail
    private String email;

    private UserProfilePub profile;

    public UserPub(String email, UserProfilePub userProfile) {
        this.email = email;
        this.profile = userProfile;
    }

    @Override
    public String toString() {
        return "User{" +
                "'id':'" + id + "'" +
                ", 'email':'" + email + "'" +
                ", " + profile +
                '}';
    }

}