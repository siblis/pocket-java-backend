package ru.geekbrains.pocket.backend.domain;

import lombok.*;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.messaging.handler.annotation.Payload;

//this class for class UserMessage & GroupMessage

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@TypeAlias("attachment")
public class Attachment {
    private String type;
    private Payload payload;
}
