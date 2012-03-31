package com.nali.spreader.factory.config;

import java.io.Serializable;

/**
 * 任务执行情况的DTO
 * 
 * @author xiefei
 * 
 */
public class TaskResultInfoQueryDto implements Serializable {
	private static final long serialVersionUID = 6212285204127937425L;

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
