package ru.geekbrains.pocket.backend.exception;

import org.bson.types.ObjectId;

@SuppressWarnings("serial")
public class GroupNotFoundException extends RuntimeException {
    public GroupNotFoundException(String message) {
        super(message);
    }

    public GroupNotFoundException(ObjectId id) {
        super("could not find group '" + id + "'.");
    }
}
