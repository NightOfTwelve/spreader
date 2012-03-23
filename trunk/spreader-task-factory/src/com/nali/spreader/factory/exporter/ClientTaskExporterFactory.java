package com.nali.spreader.factory.exporter;

import java.util.Map;

import com.nali.spreader.factory.base.MultiTaskMeta;
import com.nali.spreader.factory.base.SingleTaskMeta;
import com.nali.spreader.factory.base.TaskMeta;
import com.nali.spreader.service.ITaskService;

public class ClientTaskExporterFactory {
	private TaskSender taskSender;
	private ITaskService taskService;
	private IResultInfo resultInfo;
	
	public ClientTaskExporterFactory(ITaskService taskService, TaskSender taskSender, IResultInfo resultInfo) {
		this.taskService = taskService;
		this.taskSender = taskSender;
		this.resultInfo = resultInfo;
	}
	
	public <TM extends TaskMeta, E extends Exporter<TM>> ExporterProvider<TM, E> getExporterProvider(TM tm, Class<E> exporterClass) {
		ExporterProvider<TM, E> rlt;
		if (tm instanceof SingleTaskMeta) {
			rlt = new ExporterProvider<TM, E>(exporterClass, SingleTaskExporterImpl.class, tm, taskSender,
					taskService, resultInfo, tm.getContextMeta()==null? null: tm.getContextMeta().getSystemPropertyMap());
		} else if (tm instanceof MultiTaskMeta) {
			rlt = new ExporterProvider<TM, E>(exporterClass, MultiTaskExporterImpl.class, tm, taskSender,
					taskService, resultInfo, tm.getContextMeta()==null? null: tm.getContextMeta().getSystemPropertyMap());
		} else {
			throw new IllegalArgumentException("unsupported TaskProducer type:"+tm);
		}
		return rlt;
	}

	static class MultiTaskExporterImpl extends BaseDBStoreExporterImpl<MultiTaskMeta> implements MultiTaskExporter {
		private Long actionId;
		public MultiTaskExporterImpl(MultiTaskMeta taskMeta, TaskSender taskSender, ITaskService taskService, IResultInfo resultInfo, Map<String, Boolean> systemPropertyMap) {
			super(taskMeta, taskSender, taskService, resultInfo, systemPropertyMap);
		}
		@Override
		public void setActionId(Long actionId) {
			this.actionId = actionId;
		}
		@Override
		protected Long getActionId() {
			return actionId;
		}
		@Override
		protected void resetOther() {
			actionId = null;
		}
	}

	static class SingleTaskExporterImpl extends BaseDBStoreExporterImpl<SingleTaskMeta> implements SingleTaskExporter {
		public SingleTaskExporterImpl(SingleTaskMeta taskMeta, TaskSender taskSender, ITaskService taskService, IResultInfo resultInfo, Map<String, Boolean> systemPropertyMap) {
			super(taskMeta, taskSender, taskService, resultInfo, systemPropertyMap);
		}
		@Override
		protected Long getActionId() {
			return getTaskMeta().getActionId();
		}
	}

}
