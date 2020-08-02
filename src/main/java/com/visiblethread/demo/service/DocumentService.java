package com.visiblethread.demo.service;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.visiblethread.demo.exception.FileStorageException;
import com.visiblethread.demo.model.Document;
import com.visiblethread.demo.model.User;
import com.visiblethread.demo.repository.DocumentRepository;

@Service
public class DocumentService {
	
	@Autowired
	private DocumentRepository documentRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserGroupLinkService userGroupLinkService;
	
	public List<Document> getDocumentsForUsers(List<User> users){
		List<Document> results = documentRepository.findByUsers(users);
		if(results.isEmpty()) {
			throw new NoResultException("Cound not find any documents associated with list of users");
		} else {
			return results;
		}
	}
	
	public List<User> getUsersWhoCreatedBetweenGivenDates(List<User> users, 
			LocalDateTime createdAfter, LocalDateTime createdBefore){
		List<Document> results = documentRepository.findByUsersAndCreatedBetweenGivenDates(users, createdAfter, createdBefore);
		return results.stream().map(Document::getUser).collect(Collectors.toList());
	}

	public void uploadDocument(MultipartFile file, String emailAddress) {
		User user = validateUser(emailAddress);
		String fileName = getFileName(file);
		byte[] data = getFileData(file);
		int wordCount = getWordCount(file);
		
		Document document = new Document(fileName, wordCount, data, user);
		documentRepository.save(document);
	}

	private User validateUser(String emailAddress) {
		User user = userService.getUser(emailAddress);
		userGroupLinkService.getGroupsForUser(user.getId());
		return user;
	}
	
	private int getWordCount(MultipartFile file) {
		// processing file line by line as to not keep large files in memory
		int totalWords = 0;
		try (InputStream inputStream = file.getInputStream(); 
				Scanner sc = new Scanner(inputStream, "UTF-8")) {
		    while (sc.hasNextLine()) {
		        String line = sc.nextLine();
		        totalWords = totalWords + countWords(line);
		    }
		    if (sc.ioException() != null) {
		    	throw new FileStorageException("Error processing data from file");
		    }
		} catch (IOException e) {
			throw new FileStorageException("Error processing data from file");
		}
		return totalWords;
	}
	
	private int countWords(String sentence) {
		if(sentence == null || sentence.isEmpty()) {
			return 0;
		}
		StringTokenizer tokens = new StringTokenizer(sentence);
		return tokens.countTokens();
	}

	private String getFileName(MultipartFile file) {
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		if(fileName == null || fileName.isEmpty()) {
			throw new FileStorageException("Filename cannot be blank");
		} else if(fileName.contains("..")) {
            throw new FileStorageException("Filename contains invalid path sequence : " + fileName);
        }
		return fileName;
	}
	
	private byte[] getFileData(MultipartFile file) {
		try {
			return file.getBytes();
		} catch (IOException e) {
			throw new FileStorageException("Error retrieving data from file");
		}
	}
}
