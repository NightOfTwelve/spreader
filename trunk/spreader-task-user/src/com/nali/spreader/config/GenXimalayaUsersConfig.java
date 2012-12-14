package com.nali.spreader.config;

import java.io.Serializable;
import java.util.List;

import com.nali.spreader.factory.config.desc.PropertyDescription;

public class GenXimalayaUsersConfig implements Serializable {
	private static final long serialVersionUID = -6947610377312769609L;
	@PropertyDescription("需要排除的用户分组")
	private List<String> excludeUserGroup;
	@PropertyDescription("需要生成多少个帐号")
	private Integer genCount;

	public Integer getGenCount() {
		return genCount;
	}

	public void setGenCount(Integer genCount) {
		this.genCount = genCount;
	}

	public List<String> getExcludeUserGroup() {
		return excludeUserGroup;
	}

	public void setExcludeUserGroup(List<String> excludeUserGroup) {
		this.excludeUserGroup = excludeUserGroup;
	}
}
