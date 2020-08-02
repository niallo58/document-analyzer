package com.visiblethread.demo.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GroupTest {

	public Group underTest;
	
	@BeforeEach
	public void setup() {
		underTest = new Group();	
	}
	
	@Test
	void testEmailAddress() {
		// given
		String expectedGroupName = "Admin";
		underTest.setGroupName(expectedGroupName);
		
		// when
		String actual = underTest.getGroupName();
		
		// then
		assertEquals(expectedGroupName, actual);
	}
	
}
