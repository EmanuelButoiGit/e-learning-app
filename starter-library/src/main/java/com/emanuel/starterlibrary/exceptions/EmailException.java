package com.emanuel.starterlibrary.exceptions;

public class EmailException extends Exception {
    public EmailException(Exception e, String subject) {
        super("Cannot send mail with the following subject: \"" + subject + "\". " + e);
    }
}
