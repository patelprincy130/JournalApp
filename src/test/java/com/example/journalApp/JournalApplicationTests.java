package com.example.journalApp;

import com.example.journalApp.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
class JournalApplicationTests {

	@Test
	void contextLoads() {
	}
    @Test
    void testGetId() {
        UserEntity user = new UserEntity();
        assertNull(user.getId());
    }


}
