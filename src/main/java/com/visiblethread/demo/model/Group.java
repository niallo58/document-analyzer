package com.visiblethread.demo.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "GROUP_INFO")
public class Group implements Serializable {

	private static final long serialVersionUID = -3953772059408919628L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private String id;
	
	@Column(name = "created_on")
	private LocalDateTime createdOn;
	
	@Size(min = 1, max = 50, message = "Group name must be between 1 and 30 characters")
	@Column(name = "group_name")
	private String groupName;
	
	public Group() {
		this.createdOn = LocalDateTime.now();
	}
	
	public String getId() {
		return id;
	}
	
	public LocalDateTime getCreatedOn() {
		return createdOn;
	}
	
	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

}
