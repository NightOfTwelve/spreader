package com.nali.spreader.pool;

public class UidAction {
	private Long uid;
	private Long actionId;
	public UidAction(Long uid, Long actionId) {
		super();
		this.uid = uid;
		this.actionId = actionId;
	}
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public Long getActionId() {
		return actionId;
	}
	public void setActionId(Long actionId) {
		this.actionId = actionId;
	}

}
