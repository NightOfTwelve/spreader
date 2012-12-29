package com.nali.spreader.client.ximalaya.exception;

public class AuthenticationException extends Throwable {

	private static final long serialVersionUID = 1L;

	public AuthenticationException() {
	}

	public AuthenticationException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public AuthenticationException(String msg) {
		super(msg);
	}
}
