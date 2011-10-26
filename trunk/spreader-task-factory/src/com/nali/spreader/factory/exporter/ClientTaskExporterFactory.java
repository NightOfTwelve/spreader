package com.nali.spreader.factory.exporter;

import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;

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
	
	public TaskExporter getExporter(SingleTaskComponent taskProducer) {
		return getExporter(taskProducer.getCode(), taskProducer.getTaskType(), taskProducer.getActionId());
	}

	public TaskExporter getExporter(String taskCode, Integer taskType, Long actionId) {
		return new ClientTaskExporter(taskCode, taskType, actionId);
	}

	class ClientTaskExporter implements TaskExporter {
		private String taskCode;
		private Integer taskType;
		private Long actionId;

		public ClientTaskExporter(String taskCode, Integer taskType, Long actionId) {
			super();
			this.taskCode = taskCode;
			this.taskType = taskType;
			this.actionId = actionId;
		}

		@Override
		public void createTask(Map<String, Object> contents, Long uid, Date expireTime) {
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
