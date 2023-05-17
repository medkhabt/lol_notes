package com.medkha.lol_notes;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LolNotesApplicationTests {

	@Disabled("disabled for CI, as this test checks the database connection, which can't be done currently in the ci without docker")
	@Test
	void contextLoads() {
	}

}
