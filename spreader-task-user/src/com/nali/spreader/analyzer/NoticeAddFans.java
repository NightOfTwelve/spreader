package com.nali.spreader.analyzer;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.nali.spreader.config.NoticeAddFansConfig;
import com.nali.spreader.data.KeyValue;
import com.nali.spreader.factory.TaskProduceLine;
import com.nali.spreader.factory.config.Configable;
import com.nali.spreader.factory.config.desc.ClassDescription;
import com.nali.spreader.factory.passive.AutowireProductLine;
import com.nali.spreader.factory.regular.RegularAnalyzer;
import com.nali.spreader.keyword.NoticeRelatedObject;

/**
 * 消息自动回粉
 * 
 * @author xiefei
 * 
 */
@Component
@ClassDescription("消息·关注用户")
public class NoticeAddFans implements RegularAnalyzer, NoticeRelatedObject,
		Configable<NoticeAddFansConfig> {
	private NoticeAddFansConfig config;
	@AutowireProductLine
	private TaskProduceLine<KeyValue<Long, Long>> addUserFans;

	@Override
	public String work() {
		Long uid = config.getUid();
		Long robotId = config.getRobotId();
		KeyValue<Long, Long> data = new KeyValue<Long, Long>();
		data.setKey(robotId);
		data.setValue(uid);
		addUserFans.send(data);
		return null;
	}

	@Override
	public void init(NoticeAddFansConfig config) {
		Assert.notNull(config, "NoticeAddFansConfig is null");
		Assert.notNull(config.getRobotId(), "robotId is null");
		Assert.notNull(config.getUid(), "uid is null");
		this.config = config;
	}
}
