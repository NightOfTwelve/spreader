package com.nali.spreader.client.task.exception;

public class FrameworkException extends RuntimeException {
	private static final long serialVersionUID = -9053893941792353603L;

	public FrameworkException() {
		super();
	}

	public FrameworkException(String message, Throwable cause) {
		super(message, cause);
	}

	public FrameworkException(String message) {
		super(message);
	}

	public FrameworkException(Throwable cause) {
		super(cause);
	}

}
