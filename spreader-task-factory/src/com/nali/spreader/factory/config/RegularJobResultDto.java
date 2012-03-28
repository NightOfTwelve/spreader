package com.nali.spreader.factory.config;

import java.util.Date;

public class RegularJobResultDto {
	private Long id;
	private Long jobId;
	private String jobName;
	private Date startTime;
	private Date endTime;
	private Integer status;
	private String result;

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getJobId() {
		return jobId;
	}

	public void setJobId(Long jobId) {
		this.jobId = jobId;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
}
