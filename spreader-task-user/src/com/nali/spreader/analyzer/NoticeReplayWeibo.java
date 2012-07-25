package com.nali.spreader.analyzer;

import org.springframework.stereotype.Component;

import com.nali.spreader.config.NoticeReplayWeiboConfig;
import com.nali.spreader.factory.TaskProduceLine;
import com.nali.spreader.factory.config.Configable;
import com.nali.spreader.factory.config.desc.ClassDescription;
import com.nali.spreader.factory.passive.AutowireProductLine;
import com.nali.spreader.factory.regular.RegularAnalyzer;
import com.nali.spreader.keyword.NoticeRelatedObject;

@Component
@ClassDescription("消息·回复微博")
public class NoticeReplayWeibo implements RegularAnalyzer, NoticeRelatedObject,
		Configable<NoticeReplayWeiboConfig> {
	private NoticeReplayWeiboConfig config;
	@AutowireProductLine
	private TaskProduceLine<NoticeReplayWeiboConfig> replyWeibo;

	@Override
	public String work() {
		Boolean needForward = config.getNeedForward();
		config.setNeedForward(Boolean.TRUE.equals(needForward));
		replyWeibo.send(config);
		return null;
	}

	@Override
	public void init(NoticeReplayWeiboConfig config) {
		this.config = config;
	}
}
