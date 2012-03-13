package com.nali.center.properties.exception;

public class ValueResolveException extends Exception {
	/**
	 * Create an AccessException with a specific message and cause.
	 * @param message the message
	 * @param cause the cause
	 */
	public ValueResolveException(String message, Exception cause) {
		super(message, cause);
	}

	/**
	 * Create an AccessException with a specific message.
	 * @param message the message
	 */
	public ValueResolveException(String message) {
		super(message);
	}
}
