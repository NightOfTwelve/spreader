package com.nali.spreader.task;

public enum TaskStatus {
	unassigned(0), assigning(1), assigned(2), failed(3), success(4);

	private int statusVal;

	private TaskStatus(int statusVal) {
		this.statusVal = statusVal;
	}

	public int getStatusVal() {
		return statusVal;
	}
}
