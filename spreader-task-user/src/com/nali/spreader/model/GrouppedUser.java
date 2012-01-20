package com.nali.spreader.model;

import com.nali.spreader.data.User;

public class GrouppedUser {
	private User user;
	private boolean manual;
	private Long uid;

	public GrouppedUser() {
		
	}
	
	public GrouppedUser(Long uid, boolean manual) {
		this.uid = uid;
		this.manual = manual;
	}
	
	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean isManual() {
		return manual;
	}

	public void setManual(boolean manual) {
		this.manual = manual;
	}
	
	@Override
	public String toString() {
		return new StringBuilder("Uid: ").append(uid).append(", manual: ").append(manual).toString();
	}
}
