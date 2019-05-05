package ru.geekbrains.pocket.backend.controller.rest;

import io.swagger.annotations.*;
import lombok.extern.log4j.Log4j2;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.pocket.backend.domain.db.User;
import ru.geekbrains.pocket.backend.domain.pub.UserProfilePub;
import ru.geekbrains.pocket.backend.service.UserService;
import ru.geekbrains.pocket.backend.util.Constant;

import javax.validation.constraints.Size;

@Log4j2
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@Api(tags = "Users", value = "Authentification сontroller")
public class UserRestController {
    @Autowired
    private UserService userService;

    @ApiOperation(value = "Find user by email & get profile",
            authorizations =  {@Authorization(value="Bearer Token")},
            notes = "Поиск пользователя по email и получение его профиля.",
            response = UserProfilePub.class)
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "token",
            required = true, dataType = "string", paramType = "header",
            example = Constant.EXAMPLE_TOKEN)})
    @GetMapping("/users") //Поиск пользователя
    public ResponseEntity<?> findUser(@ApiParam(value = "Email of user that needs to be fetched. Length min = 6, max = 32.",
                                            example = "petr@mail.ru",
                                            required = true)
                                      @RequestParam("email") @Size(min = 6, max = 32) String email) {
        User user = userService.getUserByEmail(email);
        if (user != null)
            return new ResponseEntity<>(new UserProfilePub(user), HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ApiOperation(value = "Find user by id & get profile",
            authorizations =  {@Authorization(value="Bearer Token")},
            notes = "Поиск пользователя по id и получение его профиля.",
            response = UserProfilePub.class)
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "token",
            required = true, dataType = "string", paramType = "header",
            example = Constant.EXAMPLE_TOKEN)})
    @GetMapping("/users/{id}") //Получить информацию о пользователе
    public ResponseEntity<?> getUserProfileById(@ApiParam(value = "Id user. String ObjectId.", required = true)
                                                @PathVariable String id) {
        User user = userService.getUserById(new ObjectId(id));
        if (user != null)
            return new ResponseEntity<>(new UserProfilePub(user), HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
