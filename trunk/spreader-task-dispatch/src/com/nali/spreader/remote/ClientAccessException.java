package com.nali.spreader.remote;

public class ClientAccessException extends RuntimeException {
	private static final long serialVersionUID = -907998328744347247L;

	public ClientAccessException() {
		super();
	}

	public ClientAccessException(String message, Throwable cause) {
		super(message, cause);
	}

	public ClientAccessException(String message) {
		super(message);
	}

	public ClientAccessException(Throwable cause) {
		super(cause);
	}

}
