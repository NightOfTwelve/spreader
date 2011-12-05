package com.nali.spreader.factory.exporter;

import java.util.Date;
import java.util.Map;

public interface MultiTaskExporter extends Exporter<MultiTaskMeta> {

	void createTask(Long actionId, Map<String, Object> contents, Long uid, Date expireTime);
}
