package ru.geekbrains.pocket.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.geekbrains.pocket.backend.response.UsersErrorResponse;

@ControllerAdvice
public class UsersRestExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<UsersErrorResponse> handleAllException(Exception exc) {
        UsersErrorResponse usersErrorResponse = new UsersErrorResponse();
        usersErrorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        usersErrorResponse.setMessage(exc.getMessage());
        usersErrorResponse.setTimestamp(System.currentTimeMillis());
        return new ResponseEntity<>(usersErrorResponse, HttpStatus.BAD_REQUEST);
    }
}
