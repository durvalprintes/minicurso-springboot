package com.ufopa.spring;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("default")
class ApplicationDefaultProfileTest {

	@Test
	void contextLoads() {
		assertTrue(true);
	}

}
