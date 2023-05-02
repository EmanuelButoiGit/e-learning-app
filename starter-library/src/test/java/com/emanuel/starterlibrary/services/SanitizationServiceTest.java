package com.emanuel.starterlibrary.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class SanitizationServiceTest {
    private SanitizationService sanitizationService;

    @BeforeEach
    void setUp() {
        sanitizationService = new SanitizationService();
    }

    @ParameterizedTest
    @MethodSource("provideSanitizeStringTestData")
    void testSanitizeString(String inputString, String expectedOutput) {
        String sanitizedString = sanitizationService.sanitizeString(inputString);
        assertEquals(expectedOutput, sanitizedString);
    }

    private static Stream<Arguments> provideSanitizeStringTestData() {
        return Stream.of(
                Arguments.of("<h1>Hello, World!</h1>", "Hello, World!"),
                Arguments.of("<script>alert('Hello, World!');</script>", ""),
                Arguments.of("<h1><span>Hello, World!</span></h1>", "Hello, World!"),
                Arguments.of("<h1 class=\"title\">Hello, World!</h1>", "Hello, World!"),
                Arguments.of("<!-- This is a comment --><h1>Hello, World!</h1>", "Hello, World!"),
                Arguments.of("<h1>Héllo, Wörld!</h1>", "Héllo, Wörld!")
        );
    }
}
