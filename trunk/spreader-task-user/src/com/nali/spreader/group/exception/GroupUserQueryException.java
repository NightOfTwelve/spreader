package com.nali.spreader.group.exception;

public class GroupUserQueryException extends RuntimeException{
	private static final long serialVersionUID = 6033721395017739225L;

	public GroupUserQueryException(String message) {
		super(message);
	}

	public GroupUserQueryException(String message, Throwable cause) {
		super(message, cause);
	}
}
