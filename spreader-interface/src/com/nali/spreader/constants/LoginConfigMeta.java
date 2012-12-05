package com.nali.spreader.constants;

import java.util.Map;

import com.nali.spreader.model.RobotUser;

public enum LoginConfigMeta {
	weibo(1, 1L) {
		@Override
		public Map<String, Object> getLoginParams(RobotUser robotUser) {
			// TODO Auto-generated method stub
			return null;
		}
	},
	;
	public final Integer websiteId;
	public final Long actionId;
	private LoginConfigMeta(Integer websiteId, Long actionId) {
		this.websiteId = websiteId;
		this.actionId = actionId;
	}
	public abstract Map<String, Object> getLoginParams(RobotUser robotUser);
	
}
