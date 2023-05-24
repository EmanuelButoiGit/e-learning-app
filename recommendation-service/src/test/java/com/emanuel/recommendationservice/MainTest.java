package com.emanuel.recommendationservice;

import com.emanuel.starterlibrary.StarterLibraryApplication;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MainTest {
    @Test
    void testContextLoads() {
        RecommendationServiceApplication.main(new String[]{});
        Assertions.assertTrue(true, "Application context loaded successfully.");
    }
}
