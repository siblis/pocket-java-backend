package ru.geekbrains.pocket.backend.domain.pub;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.geekbrains.pocket.backend.domain.db.User;
import ru.geekbrains.pocket.backend.util.validation.ValidEmail;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserBlacklistCollection {
    private String user;
    private Integer offset;
    private List<UserBlacklist> data;

    @Override
    public String toString() {
        return "UserBlacklistCollection{" +
                "'user':'" + user + "'" +
                ", 'offset':'" + offset + "'" +
                ", " + data +
                '}';
    }

}