package com.nali.spreader.group.config;


public class UserGroupSupportedImpl implements UserGroupSupported {
	private Long fromUserGroup;
	private Long toUserGroup;

	@Override
	public void setFromUserGroup(Long fromUserGroup) {
		this.fromUserGroup = fromUserGroup;
	}

	@Override
	public void setToUserGroup(Long toUserGroup) {
		this.toUserGroup = toUserGroup;
	}

	public Long getFromUserGroup() {
		return fromUserGroup;
	}

	public Long getToUserGroup() {
		return toUserGroup;
	}
	
}
