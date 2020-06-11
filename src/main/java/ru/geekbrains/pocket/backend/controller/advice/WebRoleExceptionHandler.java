package ru.geekbrains.pocket.backend.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;
import ru.geekbrains.pocket.backend.exception.RoleNotFoundException;

@ControllerAdvice
public class WebRoleExceptionHandler {

    @ExceptionHandler(RoleNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ResponseStatusException roleNotFoundExceptionHandler(RoleNotFoundException ex) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
    }
}
