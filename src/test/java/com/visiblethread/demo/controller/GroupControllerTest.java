package com.visiblethread.demo.controller;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.visiblethread.demo.service.GroupService;

@ExtendWith(MockitoExtension.class)
class GroupControllerTest {

	@InjectMocks
	private GroupController underTest;
	
	@Mock
	private GroupService groupService;
	
	@Test
	void testGetGroup() {
		// given
		String groupName = "Admin";
		
		// when
		underTest.getGroup(groupName);
		
		// then
		verify(groupService).getGroup(groupName);
	}
	
	@Test
	void testGetAllGroups() {
		// given
		
		// when
		underTest.getAllGroups();
		
		// then
		verify(groupService).getAllGroups();
	}
	
}
