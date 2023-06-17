package com.emanuel.ratingservice;

import com.emanuel.ratingservice.configurations.SwaggerConfiguration;
import io.swagger.v3.oas.models.OpenAPI;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
class SwaggerConfigurationTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private SwaggerConfiguration swaggerConfiguration;

    @Test
    void baseOpenAPIBeanExists() {
        // Test if the baseOpenAPI bean is present in the application context
        OpenAPI openAPI = applicationContext.getBean(OpenAPI.class);
        Assertions.assertNotNull(openAPI);
    }

    @Test
    void baseOpenAPIContainsExpectedInfo() {
        // Test if the baseOpenAPI bean contains the expected information
        OpenAPI openAPI = applicationContext.getBean(OpenAPI.class);

        Assertions.assertEquals("Documentation", openAPI.getInfo().getTitle());
        Assertions.assertEquals("2.3", openAPI.getInfo().getVersion());
        Assertions.assertEquals("Butoi Emanuel-Sebastian", openAPI.getInfo().getContact().getName());
        Assertions.assertEquals("https://www.linkedin.com/in/emanuel-sebastian-butoi-929271213/", openAPI.getInfo().getContact().getUrl());
        Assertions.assertEquals("This service is responsible with all the operations related to ratings", openAPI.getInfo().getDescription());
        Assertions.assertEquals("Apache 2.0", openAPI.getInfo().getLicense().getName());
        Assertions.assertEquals("https://www.apache.org/licenses/LICENSE-2.0.html", openAPI.getInfo().getLicense().getUrl());
        Assertions.assertEquals("https://docs.github.com/en/github/site-policy/github-terms-of-service", openAPI.getInfo().getTermsOfService());
        Assertions.assertEquals("Learn more about my API", openAPI.getExternalDocs().getDescription());
        Assertions.assertEquals("https://github.com/EmanuelButoiGit?tab=repositories", openAPI.getExternalDocs().getUrl());
    }
}