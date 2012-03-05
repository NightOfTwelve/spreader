package com.nali.spreader.model;

import java.io.Serializable;

public class UserTaskCount implements Serializable {
	private static final long serialVersionUID = 7678282826980569311L;
	private Long uid;
	private Long actionId;
	private Integer count;
	private Integer taskType;
	
	public static class QueryDto {
		private Integer taskType;
		private Long lowestPriority;
		public Integer getTaskType() {
			return taskType;
		}
		public void setTaskType(Integer taskType) {
			this.taskType = taskType;
		}
		public Long getLowestPriority() {
			return lowestPriority;
		}
		public void setLowestPriority(Long lowestPriority) {
			this.lowestPriority = lowestPriority;
		}
	}
	
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
	public Integer getTaskType() {
		return taskType;
	}
	public void setTaskType(Integer taskType) {
		this.taskType = taskType;
	}
}
