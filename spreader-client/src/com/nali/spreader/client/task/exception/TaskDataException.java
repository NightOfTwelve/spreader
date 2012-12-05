package com.nali.spreader.client.task.exception;

public class TaskDataException extends RuntimeException {
	private static final long serialVersionUID = -90538172792353603L;

	public TaskDataException() {
		super();
	}

	public TaskDataException(String message, Throwable cause) {
		super(message, cause);
	}

	public TaskDataException(String message) {
		super(message);
	}

	public TaskDataException(Throwable cause) {
		super(cause);
	}

}
