package ru.geekbrains.pocket.backend.exception;

public class RoleNotFoundException extends RuntimeException {
    public RoleNotFoundException(String message) {
        super(message);
    }

    public RoleNotFoundException(Long id) {
        super("could not find role '" + id + "'.");
    }
}
