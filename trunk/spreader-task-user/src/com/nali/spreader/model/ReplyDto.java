package com.nali.spreader.model;

import java.io.Serializable;

public class ReplyDto implements Serializable {
	private static final long serialVersionUID = 3239316598865583715L;
	private Long robotId;
	private Long contentId;
	private String text;
	private boolean forward;
	
	public ReplyDto(Long robotId, Long contentId, String text) {
		this(robotId, contentId, text, false);
	}
	
	public ReplyDto(Long robotId, Long contentId, String text, boolean forward) {
		super();
		this.robotId = robotId;
		this.contentId = contentId;
		this.text = text;
		this.forward = forward;
	}
	public Long getRobotId() {
		return robotId;
	}
	public void setRobotId(Long robotId) {
		this.robotId = robotId;
	}
	public Long getContentId() {
		return contentId;
	}
	public void setContentId(Long contentId) {
		this.contentId = contentId;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public boolean isForward() {
		return forward;
	}
	public void setForward(boolean forward) {
		this.forward = forward;
	}

}
