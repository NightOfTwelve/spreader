package com.nali.spreader.factory.exporter;

import com.nali.spreader.constants.Channel;
import com.nali.spreader.constants.Website;
import com.nali.spreader.factory.SimpleActionConfig;

public class SingleTaskComponentImpl implements TaskMachine<SingleTaskMeta> {
	private SingleTaskMeta taskMeta;
	protected final Integer websiteId;
	
	public SingleTaskComponentImpl(SimpleActionConfig actionConfig, Website website, Channel channel) {
		this.websiteId = website.getId();
		Integer taskType = website.getId() * 100 + channel.getId();
		taskMeta = new SingleTaskMeta(actionConfig.getActionId(), actionConfig.getTaskCode(), taskType);
	}

	@Override
	public SingleTaskMeta getTaskMeta() {
		return taskMeta;
	}

}