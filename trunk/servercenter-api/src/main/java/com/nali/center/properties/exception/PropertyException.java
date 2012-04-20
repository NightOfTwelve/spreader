package com.nali.center.properties.exception;

public class PropertyException extends RuntimeException {
	public PropertyException(String message, Exception cause) {
		super(message, cause);
	}

	public PropertyException(String message) {
		super(message);
	}
	
	public PropertyException() {
		super();
	}
}
