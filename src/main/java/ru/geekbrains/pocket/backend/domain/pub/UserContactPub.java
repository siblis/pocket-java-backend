package ru.geekbrains.pocket.backend.domain.pub;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserContactPub {

    private UserProfilePub contact;
    private String byname;
    private Timestamp added_at;

    @Override
    public String toString() {
        return "UserContactPub{" +
                "'contact':'" + contact + "'" +
                ", 'byname':'" + byname + "'" +
                ", 'added_at':'" + added_at + "'" +
                '}';
    }

}