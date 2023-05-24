package com.emanuel.discoveryserver;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DiscoveryServerApplicationTests {

	@Test
	void applicationContextTest() {
		DiscoveryServerApplication.main(new String[] {});
		Assertions.assertTrue(true, "Application context loaded successfully.");
	}

}
