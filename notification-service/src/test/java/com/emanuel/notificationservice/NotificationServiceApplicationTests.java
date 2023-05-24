package com.emanuel.notificationservice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class NotificationServiceApplicationTests {

	@Test
	void contextLoads() {
		NotificationServiceApplication.main(new String[]{});
		Assertions.assertTrue(true, "Application context loaded successfully.");
	}

}
