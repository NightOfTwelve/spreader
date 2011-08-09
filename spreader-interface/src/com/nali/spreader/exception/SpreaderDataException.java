package com.nali.spreader.exception;

public class SpreaderDataException extends RuntimeException {
	private static final long serialVersionUID = -1087646309776229890L;

	public SpreaderDataException() {
		super();
	}

	public SpreaderDataException(String message, Throwable cause) {
		super(message, cause);
	}

	public SpreaderDataException(String message) {
		super(message);
	}

	public SpreaderDataException(Throwable cause) {
		super(cause);
	}

}
