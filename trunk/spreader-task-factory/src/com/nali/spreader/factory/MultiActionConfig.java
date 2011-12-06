package com.nali.spreader.factory;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public enum MultiActionConfig {
	registerRobotUserEmail(1000L, 1020L),
	;
	private Set<Long> actionIds;
	private String taskCode;
	private Long startActionId;
	private Long endActionId;
	private MultiActionConfig(Long startActionId, Long endActionId) {
		this.startActionId = startActionId;
		this.endActionId = endActionId;
		setTaskCode(name());
	}
	private MultiActionConfig(Long[] actionIds) {
		this.actionIds = new HashSet<Long>(Arrays.asList(actionIds));
		setTaskCode(name());
	}
	public String getTaskCode() {
		return taskCode;
	}
	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}
	public boolean check(Long actionId) {
		if(actionIds!=null) {
			return actionIds.contains(actionId);
		} else {
			return actionId>=startActionId && actionId<endActionId;
		}
	}
}
