package com.nali.spreader.factory;

import java.util.Map;

public class TaskContent {
	private Map<String, Object> contents;
	private Long uid;
	public TaskContent() {
		super();
	}
	public TaskContent(Map<String, Object> contents, Long uid) {
		super();
		this.contents = contents;
		this.uid = uid;
	}
	public Map<String, Object> getContents() {
		return contents;
	}
	public void setContents(Map<String, Object> contents) {
		this.contents = contents;
	}
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
}
