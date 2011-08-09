package com.nali.spreader.factory;

public enum SimpleWorkshopConfig {
	fetchWeiboStarUser(1L),
	fetchWeiboUserMainPage(2L),
	;
	private Long actionId;
	private String taskCode;
	private SimpleWorkshopConfig(String taskCode, Long actionId) {
		this.actionId = actionId;
		setTaskCode(taskCode);//TODO 对code加密
	}
	private SimpleWorkshopConfig(Long actionId) {
		this.actionId = actionId;
		setTaskCode(name());
	}
	public Long getActionId() {
		return actionId;
	}
	public void setActionId(Long actionId) {
		this.actionId = actionId;
	}
	public String getTaskCode() {
		return taskCode;
	}
	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}
}
