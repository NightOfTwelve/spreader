package com.nali.spreader.model;

import java.io.Serializable;
import java.util.Date;

public class ActiveTaskDto implements Serializable {
	private static final long serialVersionUID = 8320397728030082007L;
	private Date now;
	private String priorityExpression;
	public Date getNow() {
		return now;
	}
	public void setNow(Date now) {
		this.now = now;
	}
	public String getPriorityExpression() {
		return priorityExpression;
	}
	public void setPriorityExpression(String priorityExpression) {
		this.priorityExpression = priorityExpression;
	}
}
