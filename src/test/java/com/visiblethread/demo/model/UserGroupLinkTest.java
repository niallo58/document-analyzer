package com.visiblethread.demo.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserGroupLinkTest {

	private UserGroupLink underTest;
	
	private User user;
	
	private Group group;
	
	@BeforeEach
	public void setup() {
		user = new User();
		group = new Group();
		underTest = new UserGroupLink(user, group);	
	}
	
	@Test
	void testUserGroupLink_counstructor() {
		// given
		
		// when
		
		// then
		assertEquals(user, underTest.getUser());
		assertEquals(group, underTest.getGroup());
		assertNotNull(underTest.getJoinOn());
	}
	
	@Test
	void testUser() {
		// given
		User newUser = new User();
		
		// when
		underTest.setUser(newUser);
		
		// then
		assertEquals(newUser, underTest.getUser());
	}
	
	@Test
	void testGroup() {
		// given
		Group newGroup = new Group();
		
		// when
		underTest.setGroup(newGroup);
		
		// then
		assertEquals(newGroup, underTest.getGroup());
	}
	
}
