package com.visiblethread.demo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.visiblethread.demo.model.Group;

@Repository
public interface GroupRepository extends CrudRepository<Group, Long> {
	
	List<Group> findByGroupNameIgnoreCase(String groupName);

}
