package com.visiblethread.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.persistence.NoResultException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.visiblethread.demo.model.User;
import com.visiblethread.demo.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
	
	@InjectMocks
	private UserService underTest;
	
	@Mock
	private UserRepository userRepository;
	
	private User user;
	
	@BeforeEach
	public void setup() {
		user = new User();
	}
	
	@Test
	void testGetUser_returnsResults() {
		// given
		Optional<User> expectedUser = Optional.of(user);
		String emailAddress = "example@test.com";

		when(userRepository.findByEmailAddressIgnoreCase(emailAddress)).thenReturn(expectedUser);
		
		// when
		User actual = underTest.getUser(emailAddress);
		
		// then
		assertEquals(user, actual);
	}
	
	@Test
	void testGetUser_returnsNoResults() {
		// given
		Optional<User> expectedUser = Optional.empty();
		String emailAddress = "example@test.com";

		when(userRepository.findByEmailAddressIgnoreCase(emailAddress)).thenReturn(expectedUser);
		
		// when
		Exception exception = assertThrows(NoResultException.class, () -> {
			underTest.getUser(emailAddress);
	    });
		
		// then
		String expectedErrorMessage = "Cound not find any user with the given email address : " + emailAddress;
		assertEquals(expectedErrorMessage, exception.getMessage());
	}
	
	@Test
	void testGetAllUsers_returnsResults() {
		// given
		List<User> expectedUsers = Arrays.asList(user);
		
		when(userRepository.findAll()).thenReturn(expectedUsers);
		
		// when
		List<User> actual = underTest.getAllUsers();
		
		// then
		assertEquals(expectedUsers, actual);
	}
	
	@Test
	void testGetAllUsers_returnsNoResults() {
		// given
		List<User> expectedUsers = new ArrayList<>();

		when(userRepository.findAll()).thenReturn(expectedUsers);
		
		// when
		Exception exception = assertThrows(NoResultException.class, () -> {
			underTest.getAllUsers();
	    });
		
		// then
		String expectedErrorMessage = "No users found";
		assertEquals(expectedErrorMessage, exception.getMessage());
	}
	
}
