package com.nali.spreader.factory.sender;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import com.nali.spreader.constants.ClientType;
import com.nali.spreader.model.ClientTask;

public abstract class JacksonSerializeTaskSender implements TaskSender {
	private static Logger logger=Logger.getLogger(JacksonSerializeTaskSender.class);
	private static ObjectMapper objectMapper = new ObjectMapper();
	private Integer clientType=ClientType.dotNet.getId();
	private String taskCode;

	public JacksonSerializeTaskSender(Integer clientType, String taskCode) {
		super();
		this.clientType = clientType;
		this.taskCode = taskCode;
	}

	public JacksonSerializeTaskSender(String taskCode) {
		super();
		this.taskCode = taskCode;
	}

	@Override
	public void send(Long actionId, Map<String, Object> contents, Long uid, Date expireTime) {
		try {
			String contentString = objectMapper.writeValueAsString(contents);
			ClientTask task = new ClientTask();
			task.setActionId(actionId);
			task.setClientType(clientType);
			task.setContents(contentString);
			task.setExpireTime(expireTime);
			task.setTaskCode(taskCode);
			task.setUid(uid);
			send(task);
		} catch (Exception e) {
			logger.error(e, e);
		}
	}

	protected abstract void send(ClientTask task) throws IOException;
}
