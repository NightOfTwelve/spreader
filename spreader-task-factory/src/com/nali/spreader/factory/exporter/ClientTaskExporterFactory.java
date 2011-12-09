package com.nali.spreader.factory.exporter;

import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;

import com.nali.spreader.factory.base.MultiTaskMeta;
import com.nali.spreader.factory.base.SingleTaskMeta;
import com.nali.spreader.factory.base.TaskMeta;
import com.nali.spreader.model.ClientTask;

public class ClientTaskExporterFactory {
	private static Logger logger=Logger.getLogger(ClientTaskExporterFactory.class);
	private static final ContentSerializer DEFAULT_CONTENT_SERIALIZER=new JacksonSerializer();
	private ContentSerializer contentSerializer;
	private TaskSender taskSender;
	
	public ClientTaskExporterFactory(ContentSerializer contentSerializer, TaskSender taskSender) {
		super();
		this.contentSerializer = contentSerializer;
		this.taskSender = taskSender;
	}
	
	public ClientTaskExporterFactory(TaskSender taskSender) {
		this(DEFAULT_CONTENT_SERIALIZER, taskSender);
	}
	
	@SuppressWarnings("unchecked")
	public <TM extends TaskMeta> Exporter<TM> getExporter(TM tm) {
		if (tm instanceof SingleTaskMeta) {
			SingleTaskMeta singleTaskMeta = (SingleTaskMeta) tm;
			return (Exporter<TM>) getSingleTaskExporter(singleTaskMeta.getCode(), singleTaskMeta.getTaskType(), singleTaskMeta.getActionId());
		} else if (tm instanceof MultiTaskMeta) {
			return (Exporter<TM>) getMultiTaskExporter(tm.getCode(), tm.getTaskType());
		} else {
			throw new IllegalArgumentException("unsupported TaskProducer type:"+tm);
		}
	}

	private MultiTaskExporter getMultiTaskExporter(String code, Integer taskType) {
		return new MultiTaskExporterImpl(code, taskType);
	}

	private SingleTaskExporter getSingleTaskExporter(String taskCode, Integer taskType, Long actionId) {
		return new SingleTaskExporterImpl(taskCode, taskType, actionId);
	}

	class MultiTaskExporterImpl extends ExporterImpl<MultiTaskMeta> implements MultiTaskExporter {
		public MultiTaskExporterImpl(String taskCode, Integer taskType) {
			super(taskCode, taskType);
		}
		public void createTask(Long actionId, Map<String, Object> contents, Long uid, Date expireTime) {
			super.createTask(actionId, contents, uid, expireTime);
		}
	}

	class SingleTaskExporterImpl extends ExporterImpl<SingleTaskMeta> implements SingleTaskExporter {
		private final Long actionId;

		public SingleTaskExporterImpl(String taskCode, Integer taskType, Long actionId) {
			super(taskCode, taskType);
			this.actionId = actionId;
		}

		@Override
		public void createTask(Map<String, Object> contents, Long uid, Date expireTime) {
			createTask(actionId, contents, uid, expireTime);
		}
		
	}

	class ExporterImpl<TM extends TaskMeta> implements Exporter<TM> {
		private String taskCode;
		private Integer taskType;

		public ExporterImpl(String taskCode, Integer taskType) {
			super();
			this.taskCode = taskCode;
			this.taskType = taskType;
		}

		void createTask(Long actionId, Map<String, Object> contents, Long uid, Date expireTime) {
			try {
				String contentString = contentSerializer.serialize(contents);
				ClientTask task = new ClientTask();
				task.setActionId(actionId);
				task.setTaskType(taskType);
				task.setContents(contentString);
				task.setExpireTime(expireTime);
				task.setTaskCode(taskCode);
				task.setUid(uid);
				taskSender.send(task);
			} catch (Exception e) {
				logger.error(e, e);
			}
		}

	}
}
