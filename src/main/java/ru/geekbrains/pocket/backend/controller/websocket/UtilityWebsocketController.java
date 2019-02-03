package ru.geekbrains.pocket.backend.controller.websocket;

import org.springframework.stereotype.Controller;

import java.sql.Timestamp;

@Controller
public class UtilityWebsocketController {
    public void serverPing(Timestamp timestamp){};
    public void clientPong(Timestamp timestamp){};
}
