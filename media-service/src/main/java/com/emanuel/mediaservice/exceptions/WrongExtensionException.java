package com.emanuel.mediaservice.exceptions;

public class WrongExtensionException extends Exception {
    private static final String MESSAGE = "Error for file with name %s Mime type %s doesn't match with extension %s";
    public WrongExtensionException(String message){
        super(message);
    }
    public WrongExtensionException(String fileName, String mime, String extension) {
        super(String.format(MESSAGE, fileName, mime, extension));
    }
}
