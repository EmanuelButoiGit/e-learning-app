package com.emanuel.recommendationservice;

import com.emanuel.recommendationservice.configurations.SwaggerConfiguration;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class SwaggerConfigurationTest {

    @Test
    void testBaseOpenAPI() {
        // Arrange
        SwaggerConfiguration swaggerConfiguration = new SwaggerConfiguration();

        // Act
        OpenAPI openAPI = swaggerConfiguration.baseOpenAPI();

        // Assert
        assertNotNull(openAPI);

        Info info = openAPI.getInfo();
        assertNotNull(info);
        assertEquals("Documentation", info.getTitle());
        assertEquals("2.8", info.getVersion());

        Contact contact = info.getContact();
        assertNotNull(contact);
        assertEquals("Butoi Emanuel-Sebastian", contact.getName());
        assertEquals("https://www.linkedin.com/in/emanuel-sebastian-butoi-929271213/", contact.getUrl());

        assertEquals("This service is responsible with the recommendation workflow", info.getDescription());

        License license = info.getLicense();
        assertNotNull(license);
        assertEquals("Apache 2.0", license.getName());
        assertEquals("https://www.apache.org/licenses/LICENSE-2.0.html", license.getUrl());

        assertEquals("https://docs.github.com/en/github/site-policy/github-terms-of-service", info.getTermsOfService());

        ExternalDocumentation externalDocs = openAPI.getExternalDocs();
        assertNotNull(externalDocs);
        assertEquals("Learn more about my API", externalDocs.getDescription());
        assertEquals("https://github.com/EmanuelButoiGit?tab=repositories", externalDocs.getUrl());
    }
}
