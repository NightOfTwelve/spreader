package com.nali.spreader.client.task.exception;

public class RetryException extends CooldownException {
	private static final long serialVersionUID = 3241648063383L;
	private int weight;

	public RetryException(int weight, long cooldownMillis, String message, Throwable cause) {
		super(message, cause);
		this.weight = weight;
		setCooldownMillis(cooldownMillis);
	}

	public RetryException(int weight, long cooldownMillis, String message) {
		super(message);
		this.weight = weight;
		setCooldownMillis(cooldownMillis);
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

}
