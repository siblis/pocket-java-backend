package ru.geekbrains.pocket.backend.controller.websocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import ru.geekbrains.pocket.backend.response.GreetingResponse;

@Controller
//"status: sring start|stop
//group:string *
//recipient:string *
//
//*либо в группу, либо в личные сообщения"
public class ChatWebsocketController {
    @MessageMapping("/typing") //Клиент начал либо закончил печатать
    public void clientTyping(@Payload String status,String recipient,String group){}

    public void serverTyping(@Payload String status,String group, String sender){}
}
