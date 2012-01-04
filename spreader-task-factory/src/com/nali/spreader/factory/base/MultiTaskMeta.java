package com.nali.spreader.factory.base;

public final class MultiTaskMeta implements TaskMeta {
	private String code;
	private Integer taskType;
	private ContextMeta contextMeta;

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

	@Override
	public ContextMeta getContextMeta() {
		return contextMeta;
	}

	public void setContextMeta(ContextMeta contextMeta) {
		this.contextMeta = contextMeta;
	}
}