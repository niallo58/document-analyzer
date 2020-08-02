package com.visiblethread.demo.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "USER_GROUP_LINK")
public class UserGroupLink implements Serializable {

	private static final long serialVersionUID = -1635347594594962333L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private String id;
	

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY, targetEntity=User.class)
    @JoinColumn(name="USER_ID", referencedColumnName = "ID")
    private User user;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY, targetEntity=Group.class)
    @JoinColumn(name="GROUP_ID", referencedColumnName = "ID")
    private Group group;
	
	@Column(name= "JOIN_ON")
    private LocalDateTime joinOn;
	
	public UserGroupLink() {}
	
	public UserGroupLink(User user, Group group) {
        this.user = user;
        this.group = group;
        this.setJoinOn(LocalDateTime.now());
    }
	
	public String getId() {
		return id;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public Group getGroup() {
		return group;
	}
	
	public void setGroup(Group group) {
		this.group = group;
	}
	
	public LocalDateTime getJoinOn() {
		return joinOn;
	}
	
	public void setJoinOn(LocalDateTime joinOn) {
		this.joinOn = joinOn;
	}
	
	
}
