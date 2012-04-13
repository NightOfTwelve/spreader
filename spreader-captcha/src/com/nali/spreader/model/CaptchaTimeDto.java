package com.nali.spreader.model;

import java.util.Date;

public class CaptchaTimeDto {
	private Date begin;
	private Date end;
	private Long clientId;
	public Date getBegin() {
		return begin;
	}
	public void setBegin(Date begin) {
		this.begin = begin;
	}
	public Date getEnd() {
		return end;
	}
	public void setEnd(Date end) {
		this.end = end;
	}
	public Long getClientId() {
		return clientId;
	}
	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}
}
