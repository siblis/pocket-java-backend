package ru.geekbrains.pocket.backend.controller.rest;

import io.swagger.annotations.*;
import lombok.extern.log4j.Log4j2;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.pocket.backend.domain.db.User;
import ru.geekbrains.pocket.backend.domain.db.UserChat;
import ru.geekbrains.pocket.backend.domain.pub.UserChatCollection;
import ru.geekbrains.pocket.backend.domain.pub.UserProfilePub;
import ru.geekbrains.pocket.backend.service.UserChatService;
import ru.geekbrains.pocket.backend.util.Constant;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Log4j2
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/account")
@Api(tags = "Chats", value = "/account/chats")
public class ChatRestController {

    @Autowired
    private UserChatService userChatService;
    @Autowired
    private HttpRequestComponent httpRequestComponent;

    @ApiOperation(value = "Get chats",
            authorizations =  {@Authorization(value="Bearer Token")},
            notes = "Получить историю чатов пользователя.",
            response = UserChatCollection.class)
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "token",
            required = true, dataType = "string", paramType = "header",
            example = Constant.EXAMPLE_TOKEN)})
    @GetMapping("/chats") //Получить историю чатов
    public ResponseEntity<?> getChats(@RequestParam("offset") Integer offset,
                                      HttpServletRequest request) {
        User user = httpRequestComponent.getUserFromToken(request);
        List<UserChat> userChats;
        if (user != null) {
            userChats = userChatService.getUserChats(user, offset);
            return new ResponseEntity<>(new UserChatCollection(offset, userChats), HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ApiOperation(value = "Delete chat",
            authorizations =  {@Authorization(value="Bearer Token")},
            notes = "Удаление чата пользователя по id чата.")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "token",
            required = true, dataType = "string", paramType = "header",
            example = Constant.EXAMPLE_TOKEN)})
    @DeleteMapping("/chats/{id}") //Удаление чата
    public ResponseEntity<?> deleteChat(@ApiParam(value = "Id chat. String ObjectId.", required = true)
                                        @PathVariable String id,
                                        HttpServletRequest request) {
        ObjectId objectId = new ObjectId(id);
        UserChat userChat = userChatService.getUserChat(objectId);
        if (userChat != null) {
            userChatService.deleteUserChat(objectId);
            return new ResponseEntity<>(HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
