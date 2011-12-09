package com.nali.spreader.factory.base;

import com.nali.spreader.constants.Channel;
import com.nali.spreader.constants.Website;
import com.nali.spreader.factory.MultiActionConfig;

public class MultiTaskMachineImpl implements TaskMachine<MultiTaskMeta> {
	private MultiTaskMeta taskMeta;
	protected final Integer websiteId;
	
	public MultiTaskMachineImpl(MultiActionConfig actionConfig, Website website, Channel channel) {
		this.websiteId = website.getId();
		Integer taskType = website.getId() * 100 + channel.getId();
		taskMeta = new MultiTaskMeta(actionConfig.getTaskCode(), taskType);
	}

	@Override
	public MultiTaskMeta getTaskMeta() {
		return taskMeta;
	}

}