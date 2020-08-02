package com.visiblethread.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

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
import com.visiblethread.demo.repository.GroupRepository;

@ExtendWith(MockitoExtension.class)
class GroupServiceTest {

	@InjectMocks
	private GroupService underTest;
	
	@Mock
	private GroupRepository groupRepository;
	
	private Group group;
	
	private String groupName;
	
	@BeforeEach
	public void setup() {
		group = new Group();
		groupName = "Admin";
	}
	
	@Test
	void testGetUser_returnsResults() {
		// given
		List<Group> expectedGroups = Arrays.asList(group);

		when(groupRepository.findByGroupNameIgnoreCase(groupName)).thenReturn(expectedGroups);
		
		// when
		List<Group> actual = underTest.getGroup(groupName);
		
		// then
		assertEquals(expectedGroups, actual);
	}
	
	@Test
	void testGetUser_returnsNoResults() {
		// given
		List<Group> expectedGroups = new ArrayList<>();

		when(groupRepository.findByGroupNameIgnoreCase(groupName)).thenReturn(expectedGroups);
		
		// when
		Exception exception = assertThrows(NoResultException.class, () -> {
			underTest.getGroup(groupName);
	    });
		
		// then
		String expectedErrorMessage = "Cound not find any groups with the given name : " + groupName;
		assertEquals(expectedErrorMessage, exception.getMessage());
	}
	
	@Test
	void testGetAllUsers_returnsResults() {
		// given
		List<Group> expectedGroups = Arrays.asList(group);
		
		when(groupRepository.findAll()).thenReturn(expectedGroups);
		
		// when
		List<Group> actual = underTest.getAllGroups();
		
		// then
		assertEquals(expectedGroups, actual);
	}
	
	@Test
	void testGetAllUsers_returnsNoResults() {
		// given
		List<Group> expectedGroups = new ArrayList<>();

		when(groupRepository.findAll()).thenReturn(expectedGroups);
		
		// when
		Exception exception = assertThrows(NoResultException.class, () -> {
			underTest.getAllGroups();
	    });
		
		// then
		String expectedErrorMessage = "No groups found";
		assertEquals(expectedErrorMessage, exception.getMessage());
	}
	
}
