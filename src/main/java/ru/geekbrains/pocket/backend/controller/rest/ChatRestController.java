package ru.geekbrains.pocket.backend.controller.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.pocket.backend.domain.db.User;
import ru.geekbrains.pocket.backend.domain.db.UserChat;
import ru.geekbrains.pocket.backend.domain.pub.UserChatCollection;
import ru.geekbrains.pocket.backend.domain.pub.UserPub;
import ru.geekbrains.pocket.backend.repository.UserChatRepository;
import ru.geekbrains.pocket.backend.service.UserChatService;
import ru.geekbrains.pocket.backend.service.UserTokenService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/account")
public class ChatRestController {
    private static final String TOKEN_PREFIX = "Bearer ";
    private static final String HEADER_STRING = "Authorization";

    @Autowired
    private UserTokenService userTokenService;
    @Autowired
    private UserChatService userChatService;

    @GetMapping("/chats") //Получить историю чатов
    public ResponseEntity<?> findUser(@RequestParam("offset") Integer offset,
                                      HttpServletRequest request) {
        String header = request.getHeader(HEADER_STRING);
        String authToken = null;
        if (header != null && header.startsWith(TOKEN_PREFIX)) {
            authToken = header.replace(TOKEN_PREFIX, "");
        }
        User user = userTokenService.getUserByToken(authToken);
        List<UserChat> userChats;
        if (user != null) {
            userChats = userChatService.getUserChats(user);
            return new ResponseEntity<>(new UserChatCollection(offset, userChats), HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
