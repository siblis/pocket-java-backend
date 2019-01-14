package ru.geekbrains.pocket.backend.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.geekbrains.pocket.backend.domain.Security.ErrorInfo;
import ru.geekbrains.pocket.backend.exception.BadDataException;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class DataControllerAdvice {
    @ResponseBody
    @ExceptionHandler(BadDataException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorInfo handleBadRequest(HttpServletRequest req, Exception ex) {
        return new ErrorInfo(req.getRequestURL().toString(), ex);
    }
}
