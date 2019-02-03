package ru.geekbrains.pocket.backend.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.geekbrains.pocket.backend.util.validation.FieldMatch;
import ru.geekbrains.pocket.backend.util.validation.PasswordMatches;
import ru.geekbrains.pocket.backend.util.validation.ValidEmail;
import ru.geekbrains.pocket.backend.util.validation.ValidPassword;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

//this class for Spring Security

@Getter
@Setter
@NoArgsConstructor
//@FieldMatch(first = "password", second = "matchingPassword", message = "The password fields must match")
@PasswordMatches
public class SystemUser {
    @NotNull
    @Size(min = 3, message = "{Size.userDto.firstName}")
    //@Pattern(regexp = "^[a-zA-Z0-9]{4}", message = "only 4 letters/digits")
//    @Pattern(regexp = "^\\S*(?=\\S{6,})(?=\\S*[a-z])(?=\\S*[A-Z])(?=\\S*[\\d])\\S*$",
    private String firstname;

    @NotNull
    @Size(min = 1, message = "{Size.userDto.lastName}")
    private String lastname;

    @ValidPassword
    private String password;

    @NotNull
    @Size(min = 3)
    private String matchingPassword;

    @ValidEmail
    @NotNull
    @Size(min = 6, message = "{Size.userDto.email}")
    private String email;

    private boolean isUsing2FA;

    private Integer role;

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("UserDto [username=").append(firstname)
                .append(", lastName=").append(lastname)
                .append(", password=").append(password)
                .append(", matchingPassword=").append(matchingPassword)
                .append(", email=").append(email)
                .append(", isUsing2FA=").append(isUsing2FA)
                .append(", role=").append(role).append("]");
        return builder.toString();
    }
}
