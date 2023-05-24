package com.emanuel.mediaservice.configurations;

import io.swagger.v3.oas.models.OpenAPI;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class SwaggerConfigurationTest {

    @Test
    void testBaseOpenAPI() {
        SwaggerConfiguration configuration = new SwaggerConfiguration();
        OpenAPI openAPI = configuration.baseOpenAPI();

        assertNotNull(openAPI, "OpenAPI bean should not be null");
        assertEquals("Documentation", openAPI.getInfo().getTitle());
        assertEquals("3.4", openAPI.getInfo().getVersion());
        assertEquals("Butoi Emanuel-Sebastian", openAPI.getInfo().getContact().getName());
        assertEquals("https://www.linkedin.com/in/emanuel-sebastian-butoi-929271213/", openAPI.getInfo().getContact().getUrl());
        assertEquals("This service is responsible with all the operations related to media", openAPI.getInfo().getDescription());
        assertEquals("Apache 2.0", openAPI.getInfo().getLicense().getName());
        assertEquals("https://www.apache.org/licenses/LICENSE-2.0.html", openAPI.getInfo().getLicense().getUrl());
        assertEquals("https://docs.github.com/en/github/site-policy/github-terms-of-service", openAPI.getInfo().getTermsOfService());
        assertEquals("Learn more about my API", openAPI.getExternalDocs().getDescription());
        assertEquals("https://github.com/EmanuelButoiGit?tab=repositories", openAPI.getExternalDocs().getUrl());
    }
}
