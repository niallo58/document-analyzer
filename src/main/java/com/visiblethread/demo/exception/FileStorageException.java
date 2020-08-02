package com.visiblethread.demo.exception;

public class FileStorageException extends RuntimeException {

	private static final long serialVersionUID = -5021790310094487598L;

	public FileStorageException(String errorMessage) {
        super(errorMessage);
    }
	
}
