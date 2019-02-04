package ru.geekbrains.pocket.backend.domain.pub;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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