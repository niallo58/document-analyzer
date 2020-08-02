package com.visiblethread.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.NoResultException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.visiblethread.demo.model.Group;
import com.visiblethread.demo.model.User;
import com.visiblethread.demo.model.UserGroupLink;
import com.visiblethread.demo.repository.UserGroupLinkRepository;

@ExtendWith(MockitoExtension.class)
class UserGroupLinkServiceTest {

	@InjectMocks
	private UserGroupLinkService underTest;
	
	@Mock
	private UserGroupLinkRepository userGroupLinkRepository;
	
	private String userId, groupName;
	
	private UserGroupLink userGroupLink;
	
	private Group group;
	
	private User user;
	
	private LocalDateTime beforeDate;
	
	@BeforeEach
	public void setup() {
		userId = "1";
		groupName = "Admin";
		beforeDate = LocalDateTime.now();
		
		user = new User();
		group = new Group();
		userGroupLink = new UserGroupLink(user, group);
	}
	
	@Test
	void testGetGroupsForUser_returnsResults() {
		// given
		List<UserGroupLink> expectedUserGroupLinks = Arrays.asList(userGroupLink);

		when(userGroupLinkRepository.findByUserId(userId)).thenReturn(expectedUserGroupLinks);
		
		// when
		List<Group> actual = underTest.getGroupsForUser(userId);
		
		// then
		assertEquals(Arrays.asList(group), actual);
	}
	
	@Test
	void testGetGroupsForUser_returnsNoResults() {
		// given
		List<UserGroupLink> expectedUserGroupLinks = new ArrayList<>();

		when(userGroupLinkRepository.findByUserId(userId)).thenReturn(expectedUserGroupLinks);
		
		// when
		Exception exception = assertThrows(NoResultException.class, () -> {
			underTest.getGroupsForUser(userId);
	    });
		
		// then
		String expectedErrorMessage = "User for the given ID is not associated with an groups : " + userId;
		assertEquals(expectedErrorMessage, exception.getMessage());
	}
	
	@Test
	void testGetUsersInGroup_returnsResults() {
		// given
		List<UserGroupLink> expectedUserGroupLinks = Arrays.asList(userGroupLink);

		when(userGroupLinkRepository.findByGroupName(groupName)).thenReturn(expectedUserGroupLinks);
		
		// when
		List<User> actual = underTest.getUsersForGroup(groupName);
		
		// then
		assertEquals(Arrays.asList(user), actual);
	}
	
	@Test
	void testGetUsersInGroup_returnsNoResults() {
		// given
		List<UserGroupLink> expectedUserGroupLinks = new ArrayList<>();

		when(userGroupLinkRepository.findByGroupName(groupName)).thenReturn(expectedUserGroupLinks);
		
		// when
		Exception exception = assertThrows(NoResultException.class, () -> {
			underTest.getUsersForGroup(groupName);
	    });
		
		// then
		String expectedErrorMessage = "No users are associated with the given group name : " + groupName;
		assertEquals(expectedErrorMessage, exception.getMessage());
	}
	
	@Test
	void testGetAllUsersCreatedBeforeDate_returnsResults() {
		// given
		List<UserGroupLink> expectedUserGroupLinks = Arrays.asList(userGroupLink);

		when(userGroupLinkRepository.findByUserCreatedOnBefore(beforeDate)).thenReturn(expectedUserGroupLinks);
		
		// when
		List<User> actual = underTest.getAllUsersCreatedBeforeDate(beforeDate);
		
		// then
		assertEquals(Arrays.asList(user), actual);
	}
	
	@Test
	void testGetAllUsersCreatedBeforeDate_returnsNoResults() {
		// given
		List<UserGroupLink> expectedUserGroupLinks = new ArrayList<>();

		when(userGroupLinkRepository.findByUserCreatedOnBefore(beforeDate)).thenReturn(expectedUserGroupLinks);
		
		// when
		Exception exception = assertThrows(NoResultException.class, () -> {
			underTest.getAllUsersCreatedBeforeDate(beforeDate);
	    });
		
		// then
		String expectedErrorMessage = "No users have been created before the given date";
		assertEquals(expectedErrorMessage, exception.getMessage());
	}
}
