package ru.geekbrains.pocket.backend.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.messaging.handler.annotation.Payload;

//this class for class Message.attachment

@Getter
@Setter
@NoArgsConstructor
@ToString
@TypeAlias("attachment")
public class Attachment {
    private String type;
    private Payload payload;
}
