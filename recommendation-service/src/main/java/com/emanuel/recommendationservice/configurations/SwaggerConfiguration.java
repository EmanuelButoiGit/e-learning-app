package com.emanuel.recommendationservice.configurations;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition
@Configuration
public class SwaggerConfiguration {
    @Bean
    public OpenAPI baseOpenAPI(){
        return new OpenAPI().info(
                new Info()
                        .title("Documentation")
                        .version("1.8")
                        .contact(new Contact()
                                .name("Butoi Emanuel-Sebastian")
                                .url("https://www.linkedin.com/in/emanuel-sebastian-butoi-929271213/"))
                        .description("This service is responsible with the recommendation workflow")
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html"))
                        .termsOfService("https://docs.github.com/en/github/site-policy/github-terms-of-service"))
                .externalDocs(new ExternalDocumentation()
                        .description("Learn more about my API")
                        .url("https://github.com/EmanuelButoiGit?tab=repositories")
        );
    }
}
