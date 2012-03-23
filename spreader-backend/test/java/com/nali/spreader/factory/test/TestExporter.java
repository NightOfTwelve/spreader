package com.nali.spreader.factory.test;

import java.io.IOException;

import com.nali.spreader.factory.base.SingleTaskMeta;
import com.nali.spreader.factory.exporter.BaseDBStoreExporterImpl;
import com.nali.spreader.factory.exporter.IResultInfo;
import com.nali.spreader.factory.exporter.SingleTaskExporter;
import com.nali.spreader.model.ClientTask;
import com.nali.spreader.service.ITaskService;

public class TestExporter extends BaseDBStoreExporterImpl<SingleTaskMeta> implements SingleTaskExporter {
	private Long taskId;

	public TestExporter(SingleTaskMeta taskMeta, ITaskService taskService, IResultInfo resultInfo) {
		super(taskMeta, null, taskService, resultInfo, taskMeta.getContextMeta().getSystemPropertyMap());
	}

	@Override
	protected void send(ClientTask clientTask) throws IOException {
		taskId = clientTask.getId();
		System.out.println("send:" + clientTask.getContents());
	}

	@Override
	protected Long getActionId() {
		return 0L;
	}

	public Long getTaskId() {
		return taskId;
	}
	
}
