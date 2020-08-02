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

import com.visiblethread.demo.model.Group;
import com.visiblethread.demo.service.GroupService;

@RestController
@RequestMapping("/group")
public class GroupController {

	@Autowired
	private GroupService groupService;
	
	@GetMapping(value = "/get", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
    @ResponseStatus(HttpStatus.OK)
	public List<Group> getGroup(@RequestParam("groupName") String groupName) {
		return groupService.getGroup(groupName);
	}
	
	@GetMapping(value = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
    @ResponseStatus(HttpStatus.OK)
	public List<Group> getAllGroups() {
		return groupService.getAllGroups();
	}
	
}
