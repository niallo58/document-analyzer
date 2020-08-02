package com.visiblethread.demo.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.visiblethread.demo.model.Group;
import com.visiblethread.demo.model.User;
import com.visiblethread.demo.model.UserGroupLink;
import com.visiblethread.demo.repository.UserGroupLinkRepository;

@Service
public class UserGroupLinkService {

	@Autowired
	private UserGroupLinkRepository userGroupLinkRepository;

	public List<Group> getGroupsForUser(String userId) {
		List<UserGroupLink> results = userGroupLinkRepository.findByUserId(userId);
		if(results.isEmpty()) {
			throw new NoResultException("User for the given ID is not associated with an groups : " + userId);
		}
		
		return results.stream().map(UserGroupLink::getGroup).collect(Collectors.toList());
	}
	
	public List<User> getAllUsersCreatedBeforeDate(LocalDateTime beforeDate) {
		List<UserGroupLink> results = userGroupLinkRepository.findByUserCreatedOnBefore(beforeDate);
		if(results.isEmpty()) {
			throw new NoResultException("No users have been created before the given date");
		}
		
		return results.stream().map(UserGroupLink::getUser).collect(Collectors.toList());
	}

	public List<User> getUsersForGroup(String groupName) {
		List<UserGroupLink> results = userGroupLinkRepository.findByGroupName(groupName);
		if(results.isEmpty()) {
			throw new NoResultException("No users are associated with the given group name : " + groupName);
		}
		
		return results.stream().map(UserGroupLink::getUser).collect(Collectors.toList());
	}
	
}
