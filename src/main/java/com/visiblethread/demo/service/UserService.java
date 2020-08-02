package com.visiblethread.demo.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.visiblethread.demo.model.User;
import com.visiblethread.demo.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;

	public User getUser(String emailAddress) {
		Optional<User> user = userRepository.findByEmailAddressIgnoreCase(emailAddress);
		if(!user.isPresent()) {
			throw new NoResultException("Cound not find any user with the given email address : " + emailAddress);
		} else {
			return user.get();
		}
	}
	
	public List<User> getAllUsers() {
		List<User> results = (List<User>) userRepository.findAll();
		if(results.isEmpty()) {
			throw new NoResultException("No users found");
		} else {
			return results;
		}
	}
	
}
