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
public class UserContactCollection {
    private String user;
    private Integer offset;
    private List<UserContactPub> data;

    @Override
    public String toString() {
        return "UserContactCollection{" +
                "'user':'" + user + "'" +
                ", 'offset':'" + offset + "'" +
                ", " + data +
                '}';
    }

}