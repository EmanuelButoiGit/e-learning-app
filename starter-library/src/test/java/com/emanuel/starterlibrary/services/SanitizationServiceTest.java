package com.emanuel.starterlibrary.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
 class SanitizationServiceTest {
    private SanitizationService sanitizationService;

    @BeforeEach
    void setUp() {
        sanitizationService = new SanitizationService();
    }

    @Test
    void testSanitizeStringWithHtmlTags() {
        String inputString = "<h1>Hello, World!</h1>";
        String expectedOutput = "Hello, World!";
        String sanitizedString = sanitizationService.sanitizeString(inputString);
        Assertions.assertEquals(expectedOutput, sanitizedString);
    }

    @Test
    void testSanitizeStringWithScriptTags() {
        String inputString = "<script>alert('Hello, World!');</script>";
        String expectedOutput = "";
        String sanitizedString = sanitizationService.sanitizeString(inputString);
        Assertions.assertEquals(expectedOutput, sanitizedString);
    }
}
