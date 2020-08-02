package com.visiblethread.demo.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class ApiErrorTest {
	
	private HttpStatus status;
	private String message, error;
	
	@BeforeEach
	public void setup() {
		status = HttpStatus.OK;
		message = "This is a test message";
		error = "Test Error";
	}
	
	@Test
	void testApiErrorConstructor() {
		// given
		
		// when
		ApiError underTest = new ApiError(status, message, error);
		
		// then
		assertEquals(status, underTest.getStatus());
		assertEquals(message, underTest.getMessage());
		
		List<String> actalErrors = underTest.getErrors(); 
		assertTrue(actalErrors.size() == 1);
		assertEquals(error, actalErrors.get(0));
	}

}
