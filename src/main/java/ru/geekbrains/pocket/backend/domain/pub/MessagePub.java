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
public class MessagePub {

    private String id;

    private String type; //direct|group

    private String sender; //<users:_id>

    private String recipient; //<users:_id>

    private String group; //<group:_id>

    private String text;

    private boolean isRead;

    private Timestamp sent_at;

    @Override
    public String toString() {
        return "MessagePub{" +
                "'id':'" + id + "'" +
                ", 'type':'" + type + "'" +
                ", 'sender':'" + sender + "'" +
                ", 'recipient':'" + recipient + "'" +
                ", 'group +':'" + group + "'" +
                ", 'text':'" + text + "'" +
                ", 'isRead':'" + isRead + "'" +
                ", 'sent_at':'" + sent_at + "'" +
                '}';
    }
}