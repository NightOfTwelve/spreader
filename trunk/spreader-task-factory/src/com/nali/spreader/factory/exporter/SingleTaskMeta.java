package com.nali.spreader.factory.exporter;

public final class SingleTaskMeta implements TaskMeta {
	private Long actionId;
	private String code;
	private Integer taskType;

	public SingleTaskMeta(Long actionId, String code, Integer taskType) {
		super();
		this.actionId = actionId;
		this.code = code;
		this.taskType = taskType;
	}

	public Long getActionId() {
		return actionId;
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
