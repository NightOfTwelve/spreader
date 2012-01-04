package com.nali.spreader.model;

import java.io.Serializable;
import java.util.Map;

public class TaskContext implements Serializable {
	private static final long serialVersionUID = 8333561796096909292L;
	private Long uid;
	private Map<String, Object> contents;
	public TaskContext(Long uid, Map<String, Object> contents) {
		super();
		this.uid = uid;
		this.contents = contents;
	}
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public Map<String, Object> getContents() {
		return contents;
	}
	public void setContents(Map<String, Object> contents) {
		this.contents = contents;
	}
}
