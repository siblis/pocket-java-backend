package ru.geekbrains.pocket.backend.domain.pub;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.geekbrains.pocket.backend.domain.db.GroupMessage;
import ru.geekbrains.pocket.backend.domain.db.UserChat;
import ru.geekbrains.pocket.backend.domain.db.UserMessage;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class MessageCollection {

    private Integer offset;

    private List<MessagePub> data;

    public <T extends UserMessage, GroupMessage> MessageCollection(Integer offset, List<T> messages){
        this.offset = offset;
        this.data = messages.stream().map(message -> new MessagePub(message)).collect(Collectors.toList());
    }

//    public MessageCollection(Integer offset, List<? extends GroupMessage> groupMessages){
//        this.offset = offset;
//        this.data = groupMessages.stream().map(MessagePub::new).collect(Collectors.toList());
//    }

    @Override
    public String toString() {
        return "MessageCollection{" +
                "'offset':'" + offset + "'" +
                ", " + data +
                '}';
    }

}