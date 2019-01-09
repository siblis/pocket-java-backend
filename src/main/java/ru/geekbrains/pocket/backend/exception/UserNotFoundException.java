package ru.geekbrains.pocket.backend.exception;

@SuppressWarnings("serial")
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(Long id) {
        super("could not find user '" + id + "'.");
    }
}
