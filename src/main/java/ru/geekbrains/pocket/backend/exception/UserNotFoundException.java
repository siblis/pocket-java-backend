package ru.geekbrains.pocket.backend.exception;

import org.bson.types.ObjectId;

@SuppressWarnings("serial")
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(ObjectId id) {
        super("could not find user '" + id + "'.");
    }
}
