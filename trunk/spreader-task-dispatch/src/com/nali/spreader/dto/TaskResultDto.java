package com.nali.spreader.dto;

import java.io.Serializable;
import java.util.Date;

public class TaskResultDto implements Serializable {

	private static final long serialVersionUID = 2886463493261476493L;

	private Long uid;

	private Long clientId;

	private Long clientTaskId;

	private String taskCode;

	private Date executedTime;

	private String executeStatus;

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	public Long getClientTaskId() {
		return clientTaskId;
	}

	public void setClientTaskId(Long clientTaskId) {
		this.clientTaskId = clientTaskId;
	}

	public String getTaskCode() {
		return taskCode;
	}

	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}

	public Date getExecutedTime() {
		return executedTime;
	}

	public void setExecutedTime(Date executedTime) {
		this.executedTime = executedTime;
	}

	public String getExecuteStatus() {
		return executeStatus;
	}

	public void setExecuteStatus(String executeStatus) {
		this.executeStatus = executeStatus;
	}
}
