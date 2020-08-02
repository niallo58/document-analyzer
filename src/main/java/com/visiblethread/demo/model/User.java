package com.visiblethread.demo.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "USER_INFO")
public class User implements Serializable {
	
	private static final long serialVersionUID = 1892339097919421370L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private String id;
	
	@Column(name = "created_on")
	private LocalDateTime createdOn;
	
	@Size(min = 1, max = 30, message = "First name must be between 1 and 30 characters")
    @Column(name = "first_name")
	private String firstName;
	
	@Size(min = 1, max = 30, message = "Last name must be between 1 and 30 characters")
    @Column(name = "last_name")
	private String lastName;
	
    @NotNull
    @Email(message = "Email should be valid")
    @Column(name = "email_address")
	private String emailAddress;

    public User() {
		this.createdOn = LocalDateTime.now();
	}
    
    public String getId() {
    	return id;
    }
    
    public LocalDateTime getCreatedOn() {
    	return createdOn;
    }
    
    public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

}
