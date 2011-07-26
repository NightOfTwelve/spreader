package com.nali.spreader.model;

import java.io.Serializable;

public class UserTaskCount implements Serializable {
	private static final long serialVersionUID = 7678282826980569311L;
	private Long uid;
	private Long actionId;
	private Integer count;
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public Long getActionId() {
		return actionId;
	}
	public void setActionId(Long actionId) {
		this.actionId = actionId;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
}
