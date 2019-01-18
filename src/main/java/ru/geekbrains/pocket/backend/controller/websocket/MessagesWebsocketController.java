package ru.geekbrains.pocket.backend.controller.websocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import ru.geekbrains.pocket.backend.domain.Message;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//https://o7planning.org/ru/10719/create-a-simple-chat-application-with-spring-boot-and-websocket
//WS schema - https://docs.google.com/spreadsheets/d/1kOqK6-GVI9PSdgWRTDZDHhOXiUNP1XL9DkKsUM-lPbc/edit#gid=0

@Controller
public class MessagesWebsocketController {

    @MessageMapping("/send") //Отправить новое сообщение
    @SendTo("/topic/new") //Событие "Новое сообщение"
    public Message sendMessage(@Payload Message message) {
        return message;
    }

    @MessageMapping("/read") //Прочитал сообщение
    @SendTo("/topic/read") //Cобытие "Пользовтель прочитал сообщение"
    public List<String> readMessage(@Payload String idMessage, String sender, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", sender);
        //TODO найти сообщение в бд и получить получателя
        String recipient = "";
        List<String> response = new ArrayList<String>(Arrays.asList(idMessage, recipient));
        return response;

    }

}
