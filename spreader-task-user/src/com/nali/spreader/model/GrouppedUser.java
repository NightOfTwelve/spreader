package com.nali.spreader.model;

import java.util.ArrayList;
import java.util.List;

public class GrouppedUser {
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

	public boolean isManual() {
		return manual;
	}

	public void setManual(boolean manual) {
		this.manual = manual;
	}
	
	
	
	public static List<Long> getUids(List<GrouppedUser> grouppedUsers) {
		List<Long> uids = new ArrayList<Long>(grouppedUsers.size());
		for(GrouppedUser grouppedUser : grouppedUsers) {
			uids.add(grouppedUser.getUid());
		}
		return uids;
	}
	
	@Override
	public String toString() {
		return new StringBuilder("Uid: ").append(uid).append(", manual: ").append(manual).toString();
	}
}
