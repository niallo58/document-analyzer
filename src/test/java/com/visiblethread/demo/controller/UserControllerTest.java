package com.visiblethread.demo.controller;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.visiblethread.demo.service.UserService;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {
	
	@InjectMocks
	private UserController underTest;
	
	@Mock
	private UserService userService;
	
	@Test
	void testGetUser() {
		// given
		String emailAddress = "example@test.com";
		
		// when
		underTest.getUser(emailAddress);
		
		// then
		verify(userService).getUser(emailAddress);
	}
	
	@Test
	void testGetAllUsers() {
		// given
		
		// when
		underTest.getAllUsers();
		
		// then
		verify(userService).getAllUsers();
	}

}
