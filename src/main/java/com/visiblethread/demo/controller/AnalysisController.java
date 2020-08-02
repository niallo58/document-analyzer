package com.visiblethread.demo.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.mutable.MutableInt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.visiblethread.demo.model.Document;
import com.visiblethread.demo.model.User;
import com.visiblethread.demo.service.DocumentService;
import com.visiblethread.demo.service.FileProcessorService;
import com.visiblethread.demo.service.UserGroupLinkService;

@RestController
@RequestMapping("/analysis")
public class AnalysisController {
	
	@Autowired
	private UserGroupLinkService userGroupLinkService;
	
	@Autowired
	private DocumentService documentService;
	
	@Autowired
	private FileProcessorService fileProcessorService;
	
	@GetMapping(value = "/documentsUploadedForGroup", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
    @ResponseStatus(HttpStatus.OK)
	public List<String> getDocumentsUploadedForGroup(@RequestParam("groupName") String groupName) {
		List<User> users = userGroupLinkService.getUsersForGroup(groupName);
		List<Document> documents =  documentService.getDocumentsForUsers(users);
		return documents.stream().map(Document::getFileName).collect(Collectors.toList());
	}
	
	@GetMapping(value = "/usersWhoDidNotUploadDocument", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
    @ResponseStatus(HttpStatus.OK)
	public List<User> getUsersWhoDidNotUploadDocument(@RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate) {
		LocalDateTime fromDateTime = LocalDateTime.parse(fromDate, DateTimeFormatter.ISO_ZONED_DATE_TIME);
		LocalDateTime toDateTime = LocalDateTime.parse(toDate, DateTimeFormatter.ISO_ZONED_DATE_TIME);
		
		List<User> users = userGroupLinkService.getAllUsersCreatedBeforeDate(fromDateTime);
		List<User> usersWhoHaveUploadedDocument = documentService.getUsersWhoCreatedBetweenGivenDates(users, fromDateTime, toDateTime);
		
		users.removeAll(usersWhoHaveUploadedDocument);
		return users;
	}
	
	@PostMapping(value = "/termFrequency", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
    @ResponseStatus(HttpStatus.OK)
	public Map<String, MutableInt> getTermFrequency(@RequestParam("file") MultipartFile file, int noOfTermsToReturn) {
		return fileProcessorService.getMostFrequentTermsInFile(file, noOfTermsToReturn);
	}

}
