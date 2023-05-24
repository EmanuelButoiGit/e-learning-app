package com.emanuel.apigateway;

import org.apache.http.util.Asserts;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ApiGatewayApplicationTests {

	@Test
	void applicationContextTest() {
		ApiGatewayApplication.main(new String[] {});
		Assertions.assertTrue(true, "Application context loaded successfully.");
	}

}
