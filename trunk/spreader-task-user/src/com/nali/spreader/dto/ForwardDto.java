package com.nali.spreader.dto;

import java.util.Date;

import com.nali.spreader.data.Content;

public class ForwardDto {
	private Long contentId;
	private Content content;
	private Long robotUid;
	private Date startTime;
	public ForwardDto() {
		super();
	}
	public ForwardDto(Content content, Long robotUid, Date startTime) {
		super();
		this.content = content;
		this.robotUid = robotUid;
		this.startTime = startTime;
	}
	public ForwardDto(Long contentId, Long robotUid, Date startTime) {
		super();
		this.contentId = contentId;
		this.robotUid = robotUid;
		this.startTime = startTime;
	}
	public Long getContentId() {
		return contentId;
	}
	public void setContentId(Long contentId) {
		this.contentId = contentId;
	}
	public Content getContent() {
		return content;
	}
	public void setContent(Content content) {
		this.content = content;
	}
	public Long getRobotUid() {
		return robotUid;
	}
	public void setRobotUid(Long robotUid) {
		this.robotUid = robotUid;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

}
