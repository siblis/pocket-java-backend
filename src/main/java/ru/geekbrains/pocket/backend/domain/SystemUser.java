package ru.geekbrains.pocket.backend.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.geekbrains.pocket.backend.util.validation.FieldMatch;
import ru.geekbrains.pocket.backend.util.validation.ValidEmail;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

//this class for Spring Security

@Getter
@Setter
@NoArgsConstructor
@FieldMatch(first = "password", second = "matchingPassword", message = "The password fields must match")
public class SystemUser {
    @NotNull(message = "is required")
    @Size(min = 6, message = "{Size.userDto.firstName}")
    //@Pattern(regexp = "^[a-zA-Z0-9]{4}", message = "only 4 letters/digits")
//    @Pattern(regexp = "^\\S*(?=\\S{6,})(?=\\S*[a-z])(?=\\S*[A-Z])(?=\\S*[\\d])\\S*$",
    private String username;

    @NotNull(message = "is required")
    @Size(min = 3, message = "is required")
    //@ValidPassword
    private String password;

    @NotNull(message = "is required")
    @Size(min = 3, message = "is required")
    private String matchingPassword;

    @NotNull(message = "is required")
    @Size(min = 3, message = "is required")
    private String lastname;

    @NotNull(message = "is required")
    @Size(min = 3, message = "is required")
    private String firstname;

    @ValidEmail
    @NotNull(message = "is required")
    @Size(min = 6, message = "{Size.userDto.email}")
    private String email;

    private boolean isUsing2FA;

    private Integer role;

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("UserDto [username=").append(username).append(", password=")
                .append(password).append(", matchingPassword=").append(matchingPassword)
                .append(", email=").append(email).append(", isUsing2FA=")
                .append(isUsing2FA).append(", role=").append(role).append("]");
        return builder.toString();
    }
}
