package com.nali.spreader.analyzer.notice;

import org.springframework.stereotype.Component;

import com.nali.spreader.config.NoticeReplayWeiboConfig;
import com.nali.spreader.factory.TaskProduceLine;
import com.nali.spreader.factory.config.Configable;
import com.nali.spreader.factory.config.desc.ClassDescription;
import com.nali.spreader.factory.passive.AutowireProductLine;
import com.nali.spreader.factory.regular.RegularAnalyzer;
import com.nali.spreader.keyword.NoticeRelatedObject;
import com.nali.spreader.model.ReplyDto;

@Component
@ClassDescription("消息·回复微博")
public class NoticeReplayWeibo implements RegularAnalyzer, NoticeRelatedObject,
		Configable<NoticeReplayWeiboConfig> {
	private NoticeReplayWeiboConfig config;
	@AutowireProductLine
	private TaskProduceLine<ReplyDto> replyWeibo;

	@Override
	public String work() {
		ReplyDto replyDto = new ReplyDto(config.getToUid(), config.getReplayContentId(), config.getContent(), config.getNeedForward());
		replyWeibo.send(replyDto);
		return null;
	}

	@Override
	public void init(NoticeReplayWeiboConfig config) {
		if(config.getNeedForward()==null) {
			config.setNeedForward(false);
		}
		this.config = config;
	}
}
