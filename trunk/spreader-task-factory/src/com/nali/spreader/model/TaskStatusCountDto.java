package com.nali.spreader.model;

import java.io.Serializable;

public class TaskStatusCountDto implements Serializable {

	private static final long serialVersionUID = -753097330101146597L;

	private Integer status;
	private Integer cnt;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getCnt() {
		return cnt;
	}

	public void setCnt(Integer cnt) {
		this.cnt = cnt;
	}
}
