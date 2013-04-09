package com.nali.spreader.constants;

import java.util.Map;

import com.nali.spreader.model.RobotUser;
import com.nali.spreader.util.collection.CollectionUtils;

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
		@SuppressWarnings("unchecked")
		public Map<String, Object> getLoginParams(RobotUser robotUser) {
			Map<String, Object> extraInfo = (Map<String, Object>) robotUser.getExtraInfo();
			Map<String, Object> contentObjects = CollectionUtils.newHashMap(
						"name", robotUser.getLoginName(),
						"udid", extraInfo.get("udid"),
						"ipadSerial", extraInfo.get("ipadSerial"),
						"iphoneSerial", extraInfo.get("iphoneSerial"),
						"q1", extraInfo.get("q1"),
						"a1", extraInfo.get("a1"),
						"q2", extraInfo.get("q2"),
						"a2", extraInfo.get("a2"),
						"q3", extraInfo.get("q3"),
						"a3", extraInfo.get("a3"),
						"password", robotUser.getLoginPwd()
					);
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
