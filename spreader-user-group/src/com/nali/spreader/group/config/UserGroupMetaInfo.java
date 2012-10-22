package com.nali.spreader.group.config;

public class UserGroupMetaInfo {
	public static final String FROM_GROUP="${fromGroup}";
	public static final String TO_GROUP="${toGroup}";
	private String strategyDesc;

	public UserGroupMetaInfo(String strategyDesc) {
		super();
		this.strategyDesc = strategyDesc;
	}

	public String getStrategyDesc() {
		return strategyDesc;
	}

	public void setStrategyDesc(String strategyDesc) {
		this.strategyDesc = strategyDesc;
	}
	
	public boolean getHasFromGroup() {
		return strategyDesc.indexOf(FROM_GROUP)!=-1;
	}
	
	public boolean getHasToGroup() {
		return strategyDesc.indexOf(TO_GROUP)!=-1;
	}
}
