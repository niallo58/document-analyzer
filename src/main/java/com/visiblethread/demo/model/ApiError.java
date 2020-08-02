package com.visiblethread.demo.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;

public class ApiError {

	private HttpStatus status;
	private String message;
	private List<String> errors;

	public ApiError(final HttpStatus status, final String message, final String error) {
		this.status = status;
		this.message = message;
		this.errors = new ArrayList<>();
		if (error != null && !error.isEmpty()) {
			this.errors.add(error);
		}
	}

	public List<String> getErrors() {
		return new ArrayList<>(errors);
	}

	public HttpStatus getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}
	
}
