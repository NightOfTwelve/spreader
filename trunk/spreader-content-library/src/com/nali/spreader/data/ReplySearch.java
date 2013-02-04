package com.nali.spreader.data;

import java.io.Serializable;

public class ReplySearch implements Serializable {

	private static final long serialVersionUID = 3599252024441746992L;

	private Float score;

	private String reply;

	public Float getScore() {
		return score;
	}

	public void setScore(Float score) {
		this.score = score;
	}

	public String getReply() {
		return reply;
	}

	public void setReply(String reply) {
		this.reply = reply;
	}
}
