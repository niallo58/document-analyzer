package com.visiblethread.demo.service;

import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.visiblethread.demo.model.Group;
import com.visiblethread.demo.repository.GroupRepository;

@Service
public class GroupService {

	@Autowired
	private GroupRepository groupRepository;

	public List<Group> getGroup(String groupName) {
		List<Group> results = groupRepository.findByGroupNameIgnoreCase(groupName);
		if(results.isEmpty()) {
			throw new NoResultException("Cound not find any groups with the given name : " + groupName);
		} else {
			return results;
		}
	}
	
	public List<Group> getAllGroups() {
		List<Group> results = (List<Group>) groupRepository.findAll();
		if(results.isEmpty()) {
			throw new NoResultException("No groups found");
		} else {
			return results;
		}
	}
	
}
