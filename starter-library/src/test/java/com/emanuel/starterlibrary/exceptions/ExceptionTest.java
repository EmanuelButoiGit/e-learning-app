package com.emanuel.starterlibrary.exceptions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ExceptionTest {
    @Test
    void databaseExceptionWithMessage() {
        String message = "Test database exception message";
        DataBaseException exception = new DataBaseException(message);
        Assertions.assertEquals(message, exception.getMessage());
    }

    @Test
    void databaseExceptionWithNullMessage() {
        DataBaseException exception = new DataBaseException(null);
        Assertions.assertNull(exception.getMessage());
    }

    @Test
    void deserializationExceptionWithMessage() {
        String message = "Test deserialization exception exception message";
        DeserializationException exception = new DeserializationException(message);
        Assertions.assertEquals(message, exception.getMessage());
    }

    @Test
    void deserializationExceptionWithNullMessage() {
        DeserializationException exception = new DeserializationException(null);
        Assertions.assertNull(exception.getMessage());
    }

    @Test
    void documentExceptionWithMessage() {
        String message = "Test document exception exception message";
        DocumentException exception = new DocumentException(message);
        Assertions.assertEquals(message, exception.getMessage());
    }

    @Test
    void documentExceptionWithNullMessage() {
        DocumentException exception = new DocumentException(null);
        Assertions.assertNull(exception.getMessage());
    }

    @Test
    void entityNotFoundExceptionWithMessage() {
        String message = "Test document exception exception message";
        EntityNotFoundException exception = new EntityNotFoundException(message);
        Assertions.assertEquals(message, exception.getMessage());
        String parametrizedMessage = "%s not found with id %s ";
        EntityNotFoundException parametrizedException = new EntityNotFoundException(parametrizedMessage, null, 1L);
        Assertions.assertEquals(String.format(parametrizedMessage, null, 1L), parametrizedException.getMessage());
    }

    @Test
    void entityNotFoundExceptionWithLong() {
        Long id = 0L;
        String expectedMessage = "Entity not found with id: ";
        EntityNotFoundException exception = new EntityNotFoundException(id);
        Assertions.assertEquals(expectedMessage + id, exception.getMessage());
    }

    @Test
    void infectedFileExceptionWithMessage() {
        String message = "Test infected file exception message";
        InfectedFileException exception = new InfectedFileException(message);
        Assertions.assertEquals(message, exception.getMessage());
    }

    @Test
    void infectedFileExceptionWithNullMessage() {
        InfectedFileException exception = new InfectedFileException(null);
        Assertions.assertNull(exception.getMessage());
    }

    @Test
    void mp3ExceptionWithMessage() {
        String message = "Test mp3 exception message";
        Mp3Exception exception = new Mp3Exception(message);
        Assertions.assertEquals(message, exception.getMessage());
    }

    @Test
    void mp3ExceptionWithNullMessage() {
        Mp3Exception exception = new Mp3Exception(null);
        Assertions.assertNull(exception.getMessage());
    }

    @Test
    void wrongExtensionExceptionWithMessage() {
        String unsupportedMessage = "The application does not support .%s extension for this operation";
        String unsupportedExtension = ".exe";
        WrongExtensionException unsupportedException = new WrongExtensionException(unsupportedExtension);
        Assertions.assertEquals(String.format(unsupportedMessage, unsupportedExtension), unsupportedException.getMessage());

        String wrongMime = "video/avi";
        String fileName = "wrong234";
        String wrongExtension = ".mp3";
        String wrongMessage = "Mime type %s doesn't match with extension %s for the file with the following name: %s.";
        WrongExtensionException wrongException = new WrongExtensionException(fileName, wrongMime, wrongExtension);
        Assertions.assertEquals(String.format(wrongMessage, fileName, wrongMime, wrongExtension), wrongException.getMessage());
    }

    @Test
    void sanitizationExceptionWithMessage() {
        String message = "Test sanitization exception message";
        SanitizationException exception = new SanitizationException(message);
        Assertions.assertEquals(message, exception.getMessage());
    }

    @Test
    void sanitizationExceptionWithNullMessage() {
        SanitizationException exception = new SanitizationException(null);
        Assertions.assertNull(exception.getMessage());
    }

    @Test
    void emailExceptionWithMessage() {
        String subject = "Test email subject";
        Exception e = new Exception("Test exception");
        EmailException exception = new EmailException(e, subject);
        String expectedMessage = "Cannot send mail with the following subject: \"" + subject + "\". " + e;
        Assertions.assertEquals(expectedMessage, exception.getMessage());
    }
}
