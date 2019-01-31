package ru.geekbrains.pocket.backend.controller.advice;

import org.springframework.hateoas.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.geekbrains.pocket.backend.exception.UserNotFoundException;

import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//Улучшенная обработка ошибок с использованием VndErrors
//http://spring-projects.ru/guides/tutorials-bookmarks/
@ControllerAdvice
public class RequestMappingRestControllerAdvice {
    @ExceptionHandler(value = {ConstraintViolationException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map> handleValidationFailure(ConstraintViolationException ex) {
        final Map<String, Object> response = new HashMap<>();
        response.put("message", "Your request contains errors");
        response.put("errors", ex.getConstraintViolations()
                .stream()
                .map(it -> new HashMap<String, String>() {{
                    put(it.getPropertyPath().toString(), it.getMessage());
                }})
                .collect(Collectors.toList())
        );

        return ResponseEntity.badRequest().body(response);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return ex.getBindingResult()
                .getAllErrors().stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.toList());
    }

//    @ExceptionHandler
//    @ResponseBody
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public List handle(MethodArgumentNotValidException exception) {
//        //do your stuff here
//        return exception.getBindingResult().getFieldErrors()
//                .stream()
//                .map(FieldError::getDefaultMessage)
//                .collect(Collectors.toList());
//    }

}
