package com.emanuel.ratingservice.configurations;

import com.emanuel.starterlibrary.configurations.StarterLibraryConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(StarterLibraryConfiguration.class)
public class AppConfiguration {
}
