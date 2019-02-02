package ru.geekbrains.pocket.backend.controller.advice;

import org.springframework.hateoas.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.geekbrains.pocket.backend.exception.RoleNotFoundException;

@ControllerAdvice
public class WebRoleExceptionHandler {
    @ExceptionHandler(RoleNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    VndErrors roleNotFoundExceptionHandler(RoleNotFoundException ex) {
        return new VndErrors("error", ex.getMessage());
    }
}
