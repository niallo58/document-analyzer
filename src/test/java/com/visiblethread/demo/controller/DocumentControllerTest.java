package com.visiblethread.demo.controller;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import com.visiblethread.demo.service.DocumentService;

@ExtendWith(MockitoExtension.class)
class DocumentControllerTest {

	@InjectMocks
	private DocumentController underTest;
	
	@Mock
	private DocumentService documentService;
	
	@Test
	void testUploadDocument() {
		// given
		MockMultipartFile multipartFile = new MockMultipartFile("testFileName", "test.txt", 
				"text/plain", "This is the file contents".getBytes());
		String emailAddress = "test@test.com";
		
		// when
		underTest.uploadDocument(multipartFile, emailAddress);
		
		// then
		verify(documentService).uploadDocument(multipartFile, emailAddress);
	}
	
}
