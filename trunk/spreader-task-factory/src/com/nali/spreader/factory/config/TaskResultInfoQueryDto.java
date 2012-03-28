package com.nali.spreader.factory.config;

/**
 * 任务执行情况的DTO
 * 
 * @author xiefei
 * 
 */
public class TaskResultInfoQueryDto {

	private String status;

	private Integer count;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
}
