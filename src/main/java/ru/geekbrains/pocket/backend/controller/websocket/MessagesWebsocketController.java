package ru.geekbrains.pocket.backend.controller.websocket;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;
import ru.geekbrains.pocket.backend.domain.Message;

//https://o7planning.org/ru/10719/create-a-simple-chat-application-with-spring-boot-and-websocket
//https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html#websocket-server

@Controller
public class MessagesWebsocketController {

    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public MessagesWebsocketController(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @MessageMapping("/test") //Отправить новое сообщение
    @SendTo("/topic/test") //Событие "Новое сообщение"
    public Message sendTest(@Payload Message message) {
        return message;
    }

    @MessageMapping("/send") //Отправить новое сообщение
    @SendToUser("/topic/new") //Событие "Новое сообщение"
    public String clientMessageSend(@Payload ClientMessageSend message) {
        if (message.getText().equals("") || message.getGroup().equals("")) {
            //TODO проверка на ошибки
            return "Error";
        } else {
            String messageId = "";
            //TODO отправить сообщение получателю
            //send message Websocket
            this.simpMessagingTemplate.convertAndSend("/queue/new", message.getText());
            return messageId;
        }
    }

    @MessageMapping("/read") //Прочитал сообщение
    @SendToUser("/queue/read") //Cобытие "Пользователь прочитал сообщение"
    public ServerMessageRead clientMessageRead(@Payload ClientMessageRead message, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", message.getSender());
        //TODO найти сообщение в бд и получить получателя
        String recipient = "";
        System.out.println("test readMessage: " + message.getSender());
        System.out.println("test readMessage: " + HtmlUtils.htmlEscape(message.getSender()));
        return new ServerMessageRead(message.getIdMessage(), recipient);
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    private static class ClientMessageSend {
        private String text;
        private String group;
        private String recipicent;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    private static class ClientMessageRead {
        private String idMessage;
        private String sender;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    private static class ServerMessageRead {
        private String idMessage;
        private String recipient;
    }
}
