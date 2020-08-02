package com.visiblethread.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.apache.commons.lang3.mutable.MutableInt;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

@ExtendWith(MockitoExtension.class)
public class FileProcessorServiceTest {

	@InjectMocks
	private FileProcessorService underTest;
	
	@Test
	void testGetMostFrequentTermsInFile_returnsResults() {
		// given
		int noOfTermsToReturn = 2;
		MockMultipartFile multipartFile = new MockMultipartFile("testFileName", "test.txt", 
			"text/plain", "This this this Contains contains is the file contents".getBytes());
		
		// when
		Map<String, MutableInt> actual = underTest.getMostFrequentTermsInFile(multipartFile, noOfTermsToReturn);
		
		// then
		assertTrue(actual.size() == 2);
		assertEquals(new MutableInt(3), actual.get("this"));
		assertEquals(new MutableInt(2), actual.get("contains"));
	}
	
	@Test
	void testGetMostFrequentTermsInFile_fileContainsAllStopWords() {
		// given
		int noOfTermsToReturn = 2;
		MockMultipartFile multipartFile = new MockMultipartFile("testFileName", "test.txt", 
			"text/plain", "The Me You I Of And A We".getBytes());
		
		// when
		Map<String, MutableInt> actual = underTest.getMostFrequentTermsInFile(multipartFile, noOfTermsToReturn);
		
		// then
		assertTrue(actual.size() == 0);
	}
	
}
