package com.nali.spreader.dto;

import java.io.Serializable;
import java.util.Date;

import com.nali.common.model.Limit;

/**
 * 查询任务执行明细的参数DTO
 * 
 * @author xiefei
 * 
 */
public class ClientTaskStatDtlQueryParamDto implements Serializable {

	private static final long serialVersionUID = -1962976230309285945L;
	private Long taskId;
	private String taskCode;
	private Long clientId;
	private Integer status;
	private Date startTime;
	private Date endTime;
	private Long uid;
	private String errorCode;
	private Integer websiteId;
	private Integer start;
	private Integer limit;
	private Limit lit;

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public Limit getLit() {
		return lit;
	}

	public void setLit(Limit lit) {
		this.lit = lit;
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
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

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
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
}
