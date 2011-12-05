package com.nali.spreader.factory.exporter;

public final class MultiTaskMeta implements TaskMeta {
	private String code;
	private Integer taskType;

	public MultiTaskMeta(String code, Integer taskType) {
		super();
		this.code = code;
		this.taskType = taskType;
	}

	@Override
	public String getCode() {
		return code;
	}

	@Override
	public Integer getTaskType() {
		return taskType;
	}
}