package ru.geekbrains.pocket.backend.exception;

@SuppressWarnings("serial")
public class BadDataException extends RuntimeException {
    public BadDataException(String message) {
        super(message);
    }
}


