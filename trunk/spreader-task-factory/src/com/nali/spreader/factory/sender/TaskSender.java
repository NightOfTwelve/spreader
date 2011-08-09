package com.nali.spreader.factory.sender;

import java.util.Date;
import java.util.Map;

public interface TaskSender {

	/**
	 * 原则上该方法不会抛出异常
	 */
	void send(Long actionId, Map<String, Object> contents, Long uid, Date expireTime);
}
