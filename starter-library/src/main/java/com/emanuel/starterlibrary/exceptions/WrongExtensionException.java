package com.emanuel.starterlibrary.exceptions;

public class WrongExtensionException extends Exception {
    private static final String UNSUPPORTED_EXTENSION = "The application does not support .%s extension for this operation";
    private static final String WRONG_MIME = "Mime type %s doesn't match with extension %s for the file with the following name: %s.";
    public WrongExtensionException(String extension){
        super(String.format(UNSUPPORTED_EXTENSION, extension));
    }
    public WrongExtensionException(String fileName, String mime, String extension) {
        super(String.format(WRONG_MIME, fileName, mime, extension));
    }
}
