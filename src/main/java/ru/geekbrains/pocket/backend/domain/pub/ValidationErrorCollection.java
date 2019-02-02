package ru.geekbrains.pocket.backend.domain.pub;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ValidationErrorCollection {

    private String message;

    private List<ValidationError> data;

    @Override
    public String toString() {
        return "ValidationErrorCollection{" +
                "'message':'" + message + "'" +
                "'data':'" + data + "'" +
                '}';
    }

}