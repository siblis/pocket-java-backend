package ru.geekbrains.pocket.backend.exception;

import org.bson.types.ObjectId;

@SuppressWarnings("serial")
public class UserMessageNotFoundException extends RuntimeException {
    public UserMessageNotFoundException(String message) {
        super(message);
    }

    public UserMessageNotFoundException(ObjectId id) {
        super("could not find user message '" + id + "'.");
    }
}
