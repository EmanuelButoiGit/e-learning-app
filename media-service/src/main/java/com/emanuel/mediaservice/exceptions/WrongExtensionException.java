package com.emanuel.mediaservice.exceptions;

public class WrongExtensionException extends Exception {
    private static final String UNSUPPORTED_EXTENSION = "The application does not support .%s extension";
    private static final String WRONG_MIME = "Error for file with name %s Mime type %s doesn't match with extension %s";
    public WrongExtensionException(String extension){
        super(String.format(UNSUPPORTED_EXTENSION, extension));
    }
    public WrongExtensionException(String fileName, String mime, String extension) {
        super(String.format(WRONG_MIME, fileName, mime, extension));
    }
}
