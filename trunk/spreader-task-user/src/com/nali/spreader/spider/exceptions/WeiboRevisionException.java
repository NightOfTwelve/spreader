package com.nali.spreader.spider.exceptions;

public class WeiboRevisionException extends RuntimeException {

	private static final long serialVersionUID = -2028097205368119553L;

	public WeiboRevisionException(String message) {
		super(message);
	}

	public WeiboRevisionException(String message, Throwable t) {
		super(message, t);
	}
}
