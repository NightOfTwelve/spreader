package com.nali.spreader.factory;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public enum MultiActionConfig {
	registerRobotUserEmail(1000L, 1020L),
	;
	private Set<Long> actionIds;
	private String taskCode;
	private MultiActionConfig(Long startActionId, Long endActionId) {
		this(getIdRange(startActionId, endActionId));
	}
	private static Long[] getIdRange(Long startActionId, Long endActionId) {
		Long[] ids = new Long[(int) (endActionId-startActionId)];
		for (int i = 0; i < endActionId-startActionId; i++) {
			ids[i] = startActionId + i;
		}
		return ids;
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
		return actionIds.contains(actionId);
	}
}
