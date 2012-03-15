package com.nali.spreader.config;

import com.nali.spreader.factory.config.desc.PropertyDescription;

@Deprecated
public class NamedGroupUserAddFansDto extends GroupUserAddFansDto {
	private static final long serialVersionUID = -8715099331157074220L;
	@PropertyDescription("分组名称")
	private String groupName;
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
}
