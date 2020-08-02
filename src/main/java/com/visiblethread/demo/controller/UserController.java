package com.visiblethread.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.visiblethread.demo.model.User;
import com.visiblethread.demo.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@GetMapping(value = "/get", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
    @ResponseStatus(HttpStatus.OK)
	public User getUser(@RequestParam("emailAddress") String emailAddress) {
		return userService.getUser(emailAddress);
	}
	
	@GetMapping(value = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
    @ResponseStatus(HttpStatus.OK)
	public List<User> getAllUsers() {
		return userService.getAllUsers();
	}
	
}
