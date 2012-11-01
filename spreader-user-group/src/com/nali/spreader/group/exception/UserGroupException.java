package com.nali.spreader.group.exception;

public class UserGroupException extends RuntimeException{
	public UserGroupException(String message) {
		super(message);
	}

	public UserGroupException(String message, Throwable cause) {
		super(message, cause);
	}
}
