package com.visiblethread.demo.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.mutable.MutableInt;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import com.visiblethread.demo.model.Document;
import com.visiblethread.demo.model.User;
import com.visiblethread.demo.service.DocumentService;
import com.visiblethread.demo.service.FileProcessorService;
import com.visiblethread.demo.service.UserGroupLinkService;

@ExtendWith(MockitoExtension.class)
class AnalysisControllerTest {

	@InjectMocks
	private AnalysisController underTest;
	
	@Mock
	private UserGroupLinkService userGroupLinkService;
	
	@Mock
	private DocumentService documentService;
	
	@Mock
	private FileProcessorService fileProcessorService;
	
	@Test
	void testGetDocumentsUploadedForGroup() {
		// given
		User user = new User();
		List<User> users = Arrays.asList(user);
		String fileName = "testFile.txt";
		Document document = new Document(fileName , 100, "test data".getBytes(), user);
		
		String groupName = "Admin";
		List<Document> documents = Arrays.asList(document);
		
		when(userGroupLinkService.getUsersForGroup(groupName)).thenReturn(users);
		when(documentService.getDocumentsForUsers(users)).thenReturn(documents);
		
		// when
		List<String> actual = underTest.getDocumentsUploadedForGroup(groupName);
		
		// then
		assertEquals(Arrays.asList(fileName), actual);
	}
	
	@Test
	void testGetUsersWhoDidNotUploadDocument() {
		// given
		ZonedDateTime fromDate = ZonedDateTime.now();
		ZonedDateTime toDate = fromDate.plusDays(1);
		
		User userWithUpload = new User();
		User userWithoutUpload = new User();
		List<User> users = new ArrayList<>(Arrays.asList(userWithUpload, userWithoutUpload));
		List<User> usersWithoutUploading = new ArrayList<>(Arrays.asList(userWithUpload));
		
		
		when(userGroupLinkService.getAllUsersCreatedBeforeDate(fromDate.toLocalDateTime())).thenReturn(users);
		when(documentService.getUsersWhoCreatedBetweenGivenDates(
				users, fromDate.toLocalDateTime(), toDate.toLocalDateTime())).thenReturn(usersWithoutUploading);
		
		// when
		List<User> actual = underTest.getUsersWhoDidNotUploadDocument(
				fromDate.format(DateTimeFormatter.ISO_ZONED_DATE_TIME), toDate.format(DateTimeFormatter.ISO_ZONED_DATE_TIME));
		
		// then
		assertTrue(actual.size() == 1);
		assertEquals(userWithoutUpload, actual.get(0));
	}
	
	@Test
	void testGetUsersWhoDidNotUploadDocument_returnsMapOfMostFrequentWords() {
		// given
		int noOfTermsToReturn = 5;
		MockMultipartFile multipartFile = new MockMultipartFile("testFileName", "test.txt", 
				"text/plain", "This is the file contents".getBytes());
		Map<String, MutableInt> expectedMap = new LinkedHashMap<>();

		when(fileProcessorService.getMostFrequentTermsInFile(multipartFile, noOfTermsToReturn)).thenReturn(expectedMap);
		
		// when
		Map<String, MutableInt> actual = underTest.getTermFrequency(multipartFile, noOfTermsToReturn);

		// then
		assertEquals(expectedMap, actual);
	}
	
}
