package com.nali.spreader.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 封装执行任务明细结果DTO
 * 
 * @author xiefei
 * 
 */
public class ClientTaskaStatDetailDto implements Serializable {

	private static final long serialVersionUID = -4147946161140388400L;
	private Long taskId;
	private String contents;
	private String taskCode;
	private Long clientId;
	private Integer status;
	private Date executedTime;
	private Long uid;
	private String errorCode;
	private Integer websiteId;
	private String websiteErrorDesc;

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getTaskCode() {
		return taskCode;
	}

	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}

	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getExecutedTime() {
		return executedTime;
	}

	public void setExecutedTime(Date executedTime) {
		this.executedTime = executedTime;
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public Integer getWebsiteId() {
		return websiteId;
	}

	public void setWebsiteId(Integer websiteId) {
		this.websiteId = websiteId;
	}

	public String getWebsiteErrorDesc() {
		return websiteErrorDesc;
	}

	public void setWebsiteErrorDesc(String websiteErrorDesc) {
		this.websiteErrorDesc = websiteErrorDesc;
	}
}
