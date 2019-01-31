package ru.geekbrains.pocket.backend.controller.websocket;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import ru.geekbrains.pocket.backend.response.GreetingResponse;
import org.springframework.web.socket.AbstractWebSocketMessage;
import  org.springframework.web.socket.PingMessage;
import java.sql.Timestamp;

@Controller
public class UtilityWebsocketController {
    public void serverPing(Timestamp timestamp){};
    public void clientPong(Timestamp timestamp){};
}
