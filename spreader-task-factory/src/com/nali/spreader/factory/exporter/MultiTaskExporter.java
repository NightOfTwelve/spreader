package com.nali.spreader.factory.exporter;

import com.nali.spreader.factory.base.MultiTaskMeta;

public interface MultiTaskExporter extends Exporter<MultiTaskMeta> {

	void setActionId(Long actionId);
}
