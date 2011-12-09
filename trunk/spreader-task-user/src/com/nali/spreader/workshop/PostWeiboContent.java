package com.nali.spreader.workshop;

import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.nali.common.util.CollectionUtils;
import com.nali.spreader.constants.Channel;
import com.nali.spreader.constants.Website;
import com.nali.spreader.data.KeyValue;
import com.nali.spreader.factory.PassiveWorkshop;
import com.nali.spreader.factory.SimpleActionConfig;
import com.nali.spreader.factory.base.SingleTaskMachineImpl;
import com.nali.spreader.factory.exporter.SingleTaskExporter;
import com.nali.spreader.util.SpecialDateUtil;

@Component
public class PostWeiboContent extends SingleTaskMachineImpl implements PassiveWorkshop<KeyValue<Long, String>, KeyValue<Long, Long>> {
	private static Logger logger = Logger.getLogger(PostWeiboContent.class);

	public PostWeiboContent() {
		super(SimpleActionConfig.postWeiboContent, Website.weibo, Channel.normal);
	}

	@Override
	public void work(KeyValue<Long, String> data, SingleTaskExporter exporter) {
		Long uid = data.getKey();
		String text = data.getValue();
		Map<String,Object> content = CollectionUtils.newHashMap(2);
		content.put("id", uid);
		content.put("content", text);
		exporter.createTask(content, uid, SpecialDateUtil.afterToday(3));
	}

	@Override
	public void handleResult(Date updateTime, KeyValue<Long, Long> uidToContentIdMap) {
		//目前只是打打酱油
		//TODO result改为 KeyValue<Long, KeyValue<Long, Long>>，记录发过的原帖id
		logger.info("user[" + uidToContentIdMap.getKey() + "] post a content[" + uidToContentIdMap.getValue() + "]");
	}

}
