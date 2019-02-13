package ru.geekbrains.pocket.backend.controller.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.geekbrains.pocket.backend.domain.db.User;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class SocketRestController {

    private SimpUserRegistry userRegistry;

    @Autowired
    private HttpRequestComponent httpRequestComponent;

    @Autowired
    public SocketRestController(SimpUserRegistry userRegistry) {
        this.userRegistry = userRegistry;
    }

    @GetMapping("/socket") //Создать WS соеденение
    //В отличие от других запросов, токен передается не в заголовке а в get-параметре
    public ResponseEntity<?> openSocket(@RequestParam("token") String token) {
        User user = httpRequestComponent.getUserFromToken(token);
        List<String> result = new ArrayList<>();
        if (user != null){
            //создаём WS соединение
            for (SimpUser simpUser : this.userRegistry.getUsers()) {
                result.add(simpUser.toString());
            }

            return new ResponseEntity<>(result, HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

//https://www.baeldung.com/spring-security-websockets
    //var endpoint = '/ws/?access_token=' + auth.access_token;
    //var socket = new SockJS(endpoint);
    //var stompClient = Stomp.over(socket);
}
