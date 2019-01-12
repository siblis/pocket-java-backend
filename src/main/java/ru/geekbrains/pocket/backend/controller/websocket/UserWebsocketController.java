package ru.geekbrains.pocket.backend.controller.websocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import ru.geekbrains.pocket.backend.domain.User;
import ru.geekbrains.pocket.backend.response.GreetingResponse;

@Controller
public class UserWebsocketController {
    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public GreetingResponse greeting(User user) {
        return new GreetingResponse("Hello, " + user.getUsername() + "!");
    }
}
