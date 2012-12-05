package com.nali.spreader.client.task.exception;

public class CooldownException extends RuntimeException {
	private static final long serialVersionUID = 92371138063383L;
	private long cooldownMillis;

	public CooldownException() {
		super();
	}

	public CooldownException(String message, Throwable cause) {
		super(message, cause);
	}

	public CooldownException(String message) {
		super(message);
	}

	public CooldownException(Throwable cause) {
		super(cause);
	}

	public long getCooldownMillis() {
		return cooldownMillis;
	}

	public void setCooldownMillis(long cooldownMillis) {
		this.cooldownMillis = cooldownMillis;
	}

}
