package com.visiblethread.demo.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "DOCUMENT_INFO")
public class Document {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private String id;
	
	@Column(name = "created_on")
	private LocalDateTime createdOn;

	@NotNull
	@Column(name = "file_name")
    private String fileName;
    
	@NotNull
    @Column(name = "word_count")
    private int wordCount;

    @Lob
    private byte[] data;
    
    @NotNull
	@ManyToOne(fetch = FetchType.LAZY, targetEntity=User.class)
    @JoinColumn(name="USER_ID", referencedColumnName = "ID")
    private User user;
    
    public Document() {}
    
    public Document(String fileName, int wordCount, byte[] data, User user) {
    	this.fileName = fileName;
    	this.wordCount = wordCount;
    	this.data = data;
    	this.user = user;
    	this.createdOn = LocalDateTime.now();
    }

    public String getId() {
    	return id;
    }

    public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public int getWordCount() {
		return wordCount;
	}

	public void setWordCount(int wordCount) {
		this.wordCount = wordCount;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

}
