package com.visiblethread.demo.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DocumentTest {
	
	private Document underTest;
	
	private User user;
	
	private String fileName;
	
	private int wordCount;
	
	private byte[] data;
	
	@BeforeEach
	public void setup() {
		user = new User();
		fileName = "test.txt";
		wordCount = 100;
		data = "This is test data".getBytes();
		underTest = new Document(fileName, wordCount, data, user);
	}
	
	@Test
	void testDocument_constructor() {
		// given
		
		// when
		
		// then
		assertNotNull(underTest.getCreatedOn());
		assertEquals(user, underTest.getUser());
		assertEquals(fileName, underTest.getFileName());
		assertEquals(wordCount, underTest.getWordCount());
		assertEquals(data, underTest.getData());
	}
	
	@Test
	void testDocument_user() {
		// given
		User newUser = new User();
		
		// when
		underTest.setUser(newUser);
		
		// then
		assertEquals(newUser, underTest.getUser());
	}
	
	@Test
	void testDocument_fileName() {
		// given
		String newFileName = "new.txt";
		
		// when
		underTest.setFileName(newFileName);
		
		// then
		assertEquals(newFileName, underTest.getFileName());
	}
	
	@Test
	void testDocument_wordCount() {
		// given
		int newWordCount = 200;
		
		// when
		underTest.setWordCount(newWordCount);
		
		// then
		assertEquals(newWordCount, underTest.getWordCount());
	}
	
	@Test
	void testDocument_data() {
		// given
		byte[] newData = "New data".getBytes();
		
		// when
		underTest.setData(newData);
		
		// then
		assertEquals(newData, underTest.getData());
	}
	
}
