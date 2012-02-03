package com.nali.spreader.model;

import java.io.Serializable;
import java.util.Date;

public class TaskError implements Serializable {
	private static final long serialVersionUID = 6335720741993066470L;
	private Long taskId;
	private Long clientId;
	private String taskCode;//remove code?
	private String errorCode;
	private String errorDesc;
	private Object errrorData;
	private Date errorTime;
	public Long getTaskId() {
		return taskId;
	}
	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}
	public Long getClientId() {
		return clientId;
	}
	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public Object getErrrorData() {
		return errrorData;
	}
	public void setErrrorData(Object errrorData) {
		this.errrorData = errrorData;
	}
	public Date getErrorTime() {
		return errorTime;
	}
	public void setErrorTime(Date errorTime) {
		this.errorTime = errorTime;
	}
	public String getTaskCode() {
		return taskCode;
	}
	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}
	public String getErrorDesc() {
		return errorDesc;
	}
	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}
}
