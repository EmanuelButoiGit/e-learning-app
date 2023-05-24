package com.emanuel.mediaservice.configurations;

import com.emanuel.starterlibrary.configurations.StarterLibraryConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class AppConfigurationTest {
    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void shouldLoadStarterLibraryConfiguration() {
        StarterLibraryConfiguration starterLibraryConfiguration = applicationContext.getBean(StarterLibraryConfiguration.class);
        assertNotNull(starterLibraryConfiguration);
    }
}
