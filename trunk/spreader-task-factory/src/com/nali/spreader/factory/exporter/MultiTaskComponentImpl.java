package com.nali.spreader.factory.exporter;

import com.nali.spreader.constants.Channel;
import com.nali.spreader.constants.Website;
import com.nali.spreader.factory.MultiActionConfig;

public class MultiTaskComponentImpl implements TaskMachine<MultiTaskMeta> {
	private MultiTaskMeta taskMeta;
	protected final Integer websiteId;
	
	public MultiTaskComponentImpl(MultiActionConfig actionConfig, Website website, Channel channel) {
		this.websiteId = website.getId();
		Integer taskType = website.getId() * 100 + channel.getId();
		taskMeta = new MultiTaskMeta(actionConfig.getTaskCode(), taskType);
	}

	@Override
	public MultiTaskMeta getTaskMeta() {
		return taskMeta;
	}

}