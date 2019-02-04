package ru.geekbrains.pocket.backend.domain.pub;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserChatPub {
    private GroupPub group;

    private UserProfilePub sender;

    private String preview;

    private Integer unread;

    @Override
    public String toString() {
        return "UserChatPub{" +
                group +
                ", " + sender +
                "'preview':'" + preview + "'" +
                ", 'unread':'" + unread + "'" +
                '}';
    }

}