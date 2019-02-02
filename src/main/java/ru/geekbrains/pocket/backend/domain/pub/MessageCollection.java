package ru.geekbrains.pocket.backend.domain.pub;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.geekbrains.pocket.backend.domain.db.UserChat;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageCollection {

    private Integer offset;

    private List<MessagePub> data;

    @Override
    public String toString() {
        return "MessageCollection{" +
                "'offset':'" + offset + "'" +
                ", " + data +
                '}';
    }

}