package com.emanuel.starterlibrary.exceptions;

public class EntityNotFoundException extends Exception {
    public EntityNotFoundException(String message) {
        super(message);
    }
    public EntityNotFoundException(Long id) {
        super("Entity not found with id: " + id);
    }
    public EntityNotFoundException(String message, Object className, Long id) {
        super(String.format(message, className, id));
    }
}
