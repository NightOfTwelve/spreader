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

	public String toString() {
		String stat = getStatusName(status);
		StringBuilder sb = new StringBuilder(stat);
		sb.append(":");
		sb.append(cnt);
		sb.append("个");
		return sb.toString();
	}

	private String getStatusName(Integer status) {
		if (status == null) {
			return "未执行";
		}
		if (status.equals(Task.STATUS_SUCCESS)) {
			return "成功";
		}
		if (status.equals(Task.STATUS_FAILED)) {
			return "失败";
		}
		return "未知错误";
	}
}
