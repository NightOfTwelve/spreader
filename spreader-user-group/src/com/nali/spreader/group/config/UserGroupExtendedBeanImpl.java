package com.nali.spreader.group.config;


public abstract class UserGroupExtendedBeanImpl implements UserGroupExtendedBean {
	private Long fromUserGroup;
	private Long toUserGroup;
	private String strategyDesc;

	public UserGroupExtendedBeanImpl(String strategyDesc) {
		super();
		this.strategyDesc = strategyDesc;
	}

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

	@Override
	public String getExtenderName() {
		return UserGroupExender.NAME;
	}

	@Override
	public String getStrategyDesc() {
		return strategyDesc;
	}
	
}
