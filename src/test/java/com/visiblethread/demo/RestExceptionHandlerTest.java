package com.visiblethread.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.persistence.NoResultException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import com.visiblethread.demo.exception.FileStorageException;
import com.visiblethread.demo.handler.RestExceptionHandler;
import com.visiblethread.demo.model.ApiError;

class RestExceptionHandlerTest {

	private RestExceptionHandler underTest;
	
	private String message;
	
	@BeforeEach
	public void setup() {
		underTest = new RestExceptionHandler();
		
		message = "Test is a test messsage";
	}
	
	@Test
	void testHandleMaxUploadSizeExceededException() {
		// given	
		long fileSize = 2448583;
		String expectedMessage = "Maximum upload size of " + fileSize + " bytes exceeded";
		MaxUploadSizeExceededException ex = new MaxUploadSizeExceededException(fileSize);
		
		// when
		ResponseEntity<ApiError> actual = underTest.handleMaxUploadSizeExceededException(ex);
		
		// then
		assertEquals(HttpStatus.PAYLOAD_TOO_LARGE, actual.getStatusCode());
		
		ApiError actualBody = actual.getBody();
		assertEquals(HttpStatus.PAYLOAD_TOO_LARGE, actualBody.getStatus());
		assertEquals(expectedMessage, actualBody.getMessage());
		assertTrue(actualBody.getErrors().size() == 1);
		assertEquals("The file you are trying to upload is too large", actualBody.getErrors().get(0));
	}
	
	@Test
	void testHandleNoResultsFound() {
		// given
		NoResultException ex = new NoResultException(message);
		
		// when
		ResponseEntity<ApiError> actual = underTest.handleNoResultsFound(ex);
		
		// then
		assertEquals(HttpStatus.OK, actual.getStatusCode());
		
		ApiError actualBody = actual.getBody();
		assertEquals(HttpStatus.OK, actualBody.getStatus());
		assertEquals(message, actualBody.getMessage());
		assertTrue(actualBody.getErrors().size() == 1);
		assertEquals("Query ran successful, not data found", actualBody.getErrors().get(0));
	}
	
	@Test
	void testHandleFileStorageException() {
		// given		
		FileStorageException ex = new FileStorageException(message);
		
		// when
		ResponseEntity<ApiError> actual = underTest.handleFileStorageException(ex);
		
		// then
		assertEquals(HttpStatus.BAD_REQUEST, actual.getStatusCode());
		
		ApiError actualBody = actual.getBody();
		assertEquals(HttpStatus.BAD_REQUEST, actualBody.getStatus());
		assertEquals(message, actualBody.getMessage());
		assertTrue(actualBody.getErrors().size() == 1);
		assertEquals("Error when trying to upload document", actualBody.getErrors().get(0));
	}
	
	@Test
	void testHandleAll() {
		// given		
		Exception ex = new Exception(message);
		
		// when
		ResponseEntity<ApiError> actual = underTest.handleAll(ex);
		
		// then
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actual.getStatusCode());
		
		ApiError actualBody = actual.getBody();
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actualBody.getStatus());
		assertEquals(message, actualBody.getMessage());
		assertTrue(actualBody.getErrors().size() == 1);
		assertEquals("Unexpected error occurred", actualBody.getErrors().get(0));
	}
	
}
