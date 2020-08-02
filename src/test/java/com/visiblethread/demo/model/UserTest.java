package com.visiblethread.demo.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserTest {

	public User underTest;
	
	@BeforeEach
	public void setup() {
		underTest = new User();	
	}
	
	@Test
	void testEmailAddress() {
		// given
		String expectedEmailAddress = "test@test.com";
		underTest.setEmailAddress(expectedEmailAddress);
		
		// when
		String actual = underTest.getEmailAddress();
		
		// then
		assertEquals(expectedEmailAddress, actual);
	}
	
	@Test
	void testFirstName() {
		// given
		String expectedFirstName = "Niall";
		underTest.setFirstName(expectedFirstName);
		
		// when
		String actual = underTest.getFirstName();
		
		// then
		assertEquals(expectedFirstName, actual);
	}
	
	@Test
	void testLastName() {
		// given
		String expectedLastName = "O Connor";
		underTest.setLastName(expectedLastName);
		
		// when
		String actual = underTest.getLastName();
		
		// then
		assertEquals(expectedLastName, actual);
	}
	
}
