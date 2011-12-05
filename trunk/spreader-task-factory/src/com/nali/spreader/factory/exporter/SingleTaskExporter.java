package com.nali.spreader.factory.exporter;

import java.util.Date;
import java.util.Map;

public interface SingleTaskExporter extends Exporter<SingleTaskMeta> {

	/**
	 * 原则上该方法不会抛出异常
	 */
	void createTask(Map<String, Object> contents, Long uid, Date expireTime);
}
