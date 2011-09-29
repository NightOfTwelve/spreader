package com.nali.spreader.factory.exporter;

import com.nali.spreader.constants.Channel;
import com.nali.spreader.constants.Website;
import com.nali.spreader.factory.SimpleActionConfig;

public abstract class SingleTaskProducerImpl implements SingleTaskProducer {
	private SimpleActionConfig actionConfig;
	private Integer taskType;
	protected final Integer websiteId;
	
	public SingleTaskProducerImpl(SimpleActionConfig actionConfig, Website website, Channel channel) {
		this.actionConfig = actionConfig;
		this.websiteId = website.getId();
		this.taskType = websiteId * 100 + channel.getId();
	}

	@Override
	public Long getActionId() {
		return actionConfig.getActionId();
	}

	@Override
	public String getCode() {
		return actionConfig.getTaskCode();
	}

	@Override
	public Integer getTaskType() {
		return taskType;
	}

}