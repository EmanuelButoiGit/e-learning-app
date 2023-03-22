package com.emanuel.ratingservice.configurations;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition
@Configuration
public class SwaggerDocConfig {
    @Bean
    public OpenAPI baseOpenAPI(){
        return new OpenAPI().info(
                new Info()
                        .title("Documentation")
                        .version("1.2")
                        .description("This service is responsible with all the operations related to ratings")
        );
    }
}
