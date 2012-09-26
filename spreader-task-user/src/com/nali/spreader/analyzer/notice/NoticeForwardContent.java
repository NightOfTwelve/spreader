package com.nali.spreader.analyzer.notice;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.nali.spreader.config.NoticeForwardContentConfig;
import com.nali.spreader.data.KeyValue;
import com.nali.spreader.factory.TaskProduceLine;
import com.nali.spreader.factory.config.Configable;
import com.nali.spreader.factory.config.desc.ClassDescription;
import com.nali.spreader.factory.passive.AutowireProductLine;
import com.nali.spreader.factory.regular.RegularAnalyzer;
import com.nali.spreader.keyword.NoticeRelatedObject;

@Component
@ClassDescription("消息·转发微博")
public class NoticeForwardContent implements RegularAnalyzer, NoticeRelatedObject,
		Configable<NoticeForwardContentConfig> {
	private NoticeForwardContentConfig config;
	@AutowireProductLine
	private TaskProduceLine<KeyValue<Long, Long>> forwardWeiboContent;

	@Override
	public String work() {
		Long toUid = config.getToUid();
		Assert.notNull(toUid, "toUid is null");
		Long contentId = config.getContentId();
		Assert.notNull(contentId, "contentId is null");
		KeyValue<Long, Long> kv = new KeyValue<Long, Long>();
		kv.setKey(toUid);
		kv.setValue(contentId);
		forwardWeiboContent.send(kv);
		return null;
	}

	@Override
	public void init(NoticeForwardContentConfig config) {
		this.config = config;
	}
}
