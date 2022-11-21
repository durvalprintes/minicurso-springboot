package com.ufopa.spring;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("dev")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class ApplicationDevProfileTest {

	@Test
	void contextLoads() {
		assertTrue(true);
	}

}
