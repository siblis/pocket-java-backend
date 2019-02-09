package ru.geekbrains.pocket.backend.controller.rest;

import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.pocket.backend.domain.db.User;
import ru.geekbrains.pocket.backend.domain.db.UserChat;
import ru.geekbrains.pocket.backend.domain.db.UserMessage;
import ru.geekbrains.pocket.backend.domain.pub.MessageCollection;
import ru.geekbrains.pocket.backend.domain.pub.MessagePub;
import ru.geekbrains.pocket.backend.domain.pub.UserChatCollection;
import ru.geekbrains.pocket.backend.repository.UserMessageRepository;
import ru.geekbrains.pocket.backend.service.UserChatService;
import ru.geekbrains.pocket.backend.service.UserMessageService;
import ru.geekbrains.pocket.backend.service.UserService;
import ru.geekbrains.pocket.backend.service.UserTokenService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/user")
public class UserMessageRestController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserMessageService userMessageService;

    @GetMapping("/{idUser}/messages") //Получить историю переписки
    public ResponseEntity<?> findUser(@PathVariable String idUser, @RequestParam("offset") Integer offset) {
        User user = userService.getUserById(new ObjectId(idUser));
        List<UserMessage> userMessages;
        if (user != null) {
            userMessages = userMessageService.getAllMessagesUser(user);
            return new ResponseEntity<>(new MessageCollection(offset, userMessages), HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
}

    @GetMapping("/{idUser}/messages/{idMessage}") //Получить конкретное сообщение
    public ResponseEntity<?> findUser(@PathVariable String idUser, @PathVariable String idMessage) {
        UserMessage userMessage = userMessageService.getMessage(new ObjectId(idMessage));
        if (userMessage != null) {
            if (idUser.equals(userMessage.getSender().getId().toString())
                    || idUser.equals(userMessage.getRecipient().getId().toString()))
                return new ResponseEntity<>(new MessagePub(userMessage), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }



}
