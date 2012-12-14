package com.nali.spreader.constants;

import java.util.Map;

import com.nali.common.util.CollectionUtils;
import com.nali.spreader.model.RobotUser;

public enum LoginConfigMeta {
	weibo(1, 3L) {
		@Override
		public Map<String, Object> getLoginParams(RobotUser robotUser) {
			Map<String, Object> contentObjects = CollectionUtils.newHashMap(2);
			contentObjects.put("name", robotUser.getLoginName());
			contentObjects.put("password", robotUser.getLoginPwd());
			return contentObjects;
		}
	},
	apple(2, 2003L) {
		@Override
		public Map<String, Object> getLoginParams(RobotUser robotUser) {
			Map<String, Object> contentObjects = CollectionUtils.newHashMap(2);
			String[] loginNames = robotUser.getLoginName().split("\\#");
			contentObjects.put("name", loginNames[0]);
			contentObjects.put("udid", loginNames[1]);
			contentObjects.put("password", robotUser.getLoginPwd());
			return contentObjects;
		}
	},
	ximalaya(3, 3003L) {
		@Override
		public Map<String, Object> getLoginParams(RobotUser robotUser) {
			Map<String, Object> contentObjects = CollectionUtils.newHashMap(2);
			contentObjects.put("name", robotUser.getLoginName());
			contentObjects.put("password", robotUser.getLoginPwd());
			return contentObjects;
		}
	};
	private static Map<Integer, LoginConfigMeta> loginConfigMap;
	private final Integer websiteId;
	private final Long actionId;

	static {
		LoginConfigMeta[] configs = LoginConfigMeta.values();
		loginConfigMap = CollectionUtils.newHashMap(configs.length);
		for (LoginConfigMeta config : configs) {
			loginConfigMap.put(config.getWebsiteId(), config);
		}
	}

	public static Map<Integer, LoginConfigMeta> getLoginConfigMap() {
		return loginConfigMap;
	}

	public Integer getWebsiteId() {
		return websiteId;
	}

	public Long getActionId() {
		return actionId;
	}

	private LoginConfigMeta(Integer websiteId, Long actionId) {
		this.websiteId = websiteId;
		this.actionId = actionId;
	}

	public abstract Map<String, Object> getLoginParams(RobotUser robotUser);

}
