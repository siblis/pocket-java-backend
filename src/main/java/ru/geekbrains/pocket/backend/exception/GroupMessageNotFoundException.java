package ru.geekbrains.pocket.backend.exception;

import org.bson.types.ObjectId;

@SuppressWarnings("serial")
public class GroupMessageNotFoundException extends RuntimeException {
    public GroupMessageNotFoundException(String message) {
        super(message);
    }

    public GroupMessageNotFoundException(ObjectId id) {
        super("could not find group message '" + id + "'.");
    }
}
