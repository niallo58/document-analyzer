package com.visiblethread.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.NoResultException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import com.visiblethread.demo.exception.FileStorageException;
import com.visiblethread.demo.model.Document;
import com.visiblethread.demo.model.User;
import com.visiblethread.demo.repository.DocumentRepository;

@ExtendWith(MockitoExtension.class)
class DocumentServiceTest {

	@InjectMocks
	private DocumentService underTest;
	
	@Mock
	private DocumentRepository documentRepository;
	
	@Mock
	private UserService userService;
	
	@Mock
	private UserGroupLinkService userGroupLinkService;
	
	private String userId, emailAddress;
	
	private User expectedUser;
	
	private LocalDateTime createdAfter, createdBefore;
	
	@BeforeEach
	public void setup() {
		userId = "1";
		emailAddress = "test@test.com";
		expectedUser = mock(User.class);
		
		createdAfter = LocalDateTime.now(); 
		createdBefore = createdAfter.plusDays(1);
	}
	
	@Test
	void testGetDocumentsForUsers_returnsDocuments() {
		// given
		List<User> users = Arrays.asList(expectedUser);
		Document document = new Document();
		List<Document> documents = Arrays.asList(document);
		
		when(documentRepository.findByUsers(users)).thenReturn(documents);
		
		// when
		List<Document> actual = underTest.getDocumentsForUsers(users);
		
		// then
		assertEquals(documents, actual);
	}
	
	@Test
	void testGetDocumentsForUsers_returnsNoDocuments() {
		// given
		List<User> users = Arrays.asList(expectedUser);
		List<Document> documents = new ArrayList<>();
		
		when(documentRepository.findByUsers(users)).thenReturn(documents);
		
		// when
		Exception exception = assertThrows(NoResultException.class, () -> {
			underTest.getDocumentsForUsers(users);
	    });
		
		// then
		String expectedErrorMessage = "Cound not find any documents associated with list of users";
		assertEquals(expectedErrorMessage, exception.getMessage());
	}
	
	@Test
	void testUploadDocument_savesDocument() {
		// given
		String expectedFileName = "test.txt";
		byte[] expectedData = "This is the file contents".getBytes();
		MockMultipartFile multipartFile = new MockMultipartFile("testFileName", expectedFileName, "text/plain", expectedData);
		
		mockUserServiceCall();
		
		// when
		underTest.uploadDocument(multipartFile, emailAddress);
		
		// then
		verify(userGroupLinkService).getGroupsForUser(userId);
		
		ArgumentCaptor<Document> documentCaptor = ArgumentCaptor.forClass(Document.class);
		verify(documentRepository).save((Document)documentCaptor.capture());
		
		Document capturedDocument = (Document) documentCaptor.getValue();
		assertEquals(expectedFileName, capturedDocument.getFileName());
		assertEquals(5, capturedDocument.getWordCount());
		assertEquals(expectedUser, capturedDocument.getUser());
		assertNotNull(capturedDocument.getCreatedOn());
	}
	
	@Test
	void testUploadDocument_badFileName() {
		// given
		String expectedFileName = "../test.txt";
		byte[] expectedData = "This is the file contents".getBytes();
		MockMultipartFile multipartFile = new MockMultipartFile("testFileName", expectedFileName, "text/plain", expectedData);
		
		mockUserServiceCall();
		
		// when
		Exception exception = assertThrows(FileStorageException.class, () -> {
			underTest.uploadDocument(multipartFile, emailAddress);
	    });
		
		// then
		String expectedErrorMessage = "Filename contains invalid path sequence : " + expectedFileName;
		assertEquals(expectedErrorMessage, exception.getMessage());
	}
	
	@Test
	void testUploadDocument_nullData() {
		// given
		String expectedFileName = "test.txt";
		byte[] expectedData = null;
		MockMultipartFile multipartFile = new MockMultipartFile("testFileName", expectedFileName, "text/plain", expectedData);
		
		mockUserServiceCall();
		
		// when
		underTest.uploadDocument(multipartFile, emailAddress);
		
		// then
		ArgumentCaptor<Document> documentCaptor = ArgumentCaptor.forClass(Document.class);
		verify(documentRepository).save((Document)documentCaptor.capture());
		
		Document capturedDocument = (Document) documentCaptor.getValue();
		assertEquals(expectedFileName, capturedDocument.getFileName());
		assertEquals(0, capturedDocument.getWordCount());
		assertEquals(expectedUser, capturedDocument.getUser());
		assertNotNull(capturedDocument.getCreatedOn());
	}
	
	@Test
	void testUploadDocument_emptyStringData() {
		// given
		String expectedFileName = "test.txt";
		byte[] expectedData = " ".getBytes();
		MockMultipartFile multipartFile = new MockMultipartFile("testFileName", expectedFileName, "text/plain", expectedData);
		
		mockUserServiceCall();
		
		// when
		underTest.uploadDocument(multipartFile, emailAddress);
		
		// then
		ArgumentCaptor<Document> documentCaptor = ArgumentCaptor.forClass(Document.class);
		verify(documentRepository).save((Document)documentCaptor.capture());
		
		Document capturedDocument = (Document) documentCaptor.getValue();
		assertEquals(expectedFileName, capturedDocument.getFileName());
		assertEquals(0, capturedDocument.getWordCount());
		assertEquals(expectedUser, capturedDocument.getUser());
		assertNotNull(capturedDocument.getCreatedOn());
	}
	
	@Test
	void testUploadDocument_noFileName() {
		// given
		String expectedFileName = "";
		byte[] expectedData = "This is the file contents".getBytes();
		MockMultipartFile multipartFile = new MockMultipartFile("testFileName", expectedFileName, "text/plain", expectedData);
		
		mockUserServiceCall();
		
		// when
		Exception exception = assertThrows(FileStorageException.class, () -> {
			underTest.uploadDocument(multipartFile, emailAddress);
	    });
		
		// then
		String expectedErrorMessage = "Filename cannot be blank";
		assertEquals(expectedErrorMessage, exception.getMessage());
	}
	
	@Test
	void testGetUsersWhoCreatedBetweenGivenDates_returnsUsers() {
		// given
		List<User> users = Arrays.asList(expectedUser);
		Document document = new Document();
		document.setUser(expectedUser);
		List<Document> documents = Arrays.asList(document);
		
		when(documentRepository.findByUsersAndCreatedBetweenGivenDates(users, createdAfter, createdBefore)).thenReturn(documents);
		
		// when
		List<User> actual = underTest.getUsersWhoCreatedBetweenGivenDates(users, createdAfter, createdBefore);
		
		// then
		assertEquals(users, actual);
	}
	
	@Test
	void testGetUsersWhoCreatedBetweenGivenDates_returnsNoUsers() {
		// given
		List<User> users = new ArrayList<>();
		List<Document> documents = new ArrayList<>();
		
		when(documentRepository.findByUsersAndCreatedBetweenGivenDates(users, createdAfter, createdBefore)).thenReturn(documents);
		
		// when
		List<User> actual = underTest.getUsersWhoCreatedBetweenGivenDates(users, createdAfter, createdBefore);
		
		// then
		assertEquals(documents, actual);
	}
	
	private void mockUserServiceCall() {
		when(expectedUser.getId()).thenReturn(userId);
		when(userService.getUser(emailAddress)).thenReturn(expectedUser);
	}
	
}
