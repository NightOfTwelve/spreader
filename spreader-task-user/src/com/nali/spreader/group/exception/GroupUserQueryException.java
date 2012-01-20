package com.nali.spreader.group.exception;

public class GroupUserQueryException extends RuntimeException{
	public GroupUserQueryException(String message) {
		super(message);
	}

	public GroupUserQueryException(String message, Throwable cause) {
		super(message, cause);
	}
}
