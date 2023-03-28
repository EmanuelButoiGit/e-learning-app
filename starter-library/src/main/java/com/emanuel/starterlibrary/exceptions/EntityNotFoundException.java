package com.emanuel.starterlibrary.exceptions;

public class EntityNotFoundException extends Exception {
    public EntityNotFoundException(String message) {
        super(message);
    }
    public EntityNotFoundException(String message, Object className, Long id) {
        super(String.format(message, className, id));
    }
}
