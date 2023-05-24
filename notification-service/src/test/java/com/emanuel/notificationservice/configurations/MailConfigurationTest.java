package com.emanuel.notificationservice.configurations;

import org.junit.jupiter.api.Test;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class MailConfigurationTest {

    @Test
    void testJavaMailSender() {
        MailConfiguration configuration = new MailConfiguration();
        ReflectionTestUtils.setField(configuration, "host", "smtp.gmail.com");
        ReflectionTestUtils.setField(configuration, "port", 587);
        ReflectionTestUtils.setField(configuration, "username", "test@gmail.com");
        ReflectionTestUtils.setField(configuration, "password", "password");
        ReflectionTestUtils.setField(configuration, "protocol", "smtp");

        JavaMailSender mailSender = configuration.javaMailSender();

        assertNotNull(mailSender, "JavaMailSender bean should not be null");
        assertEquals("smtp.gmail.com", ReflectionTestUtils.getField(mailSender, "host"));
        assertEquals(587, ReflectionTestUtils.getField(mailSender, "port"));
        assertEquals("test@gmail.com", ReflectionTestUtils.getField(mailSender, "username"));
        assertEquals("password", ReflectionTestUtils.getField(mailSender, "password"));
        assertEquals("smtp", ReflectionTestUtils.getField(mailSender, "protocol"));
    }
}
