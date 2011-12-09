package com.nali.spreader.workshop;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nali.common.util.CollectionUtils;
import com.nali.spreader.constants.Channel;
import com.nali.spreader.constants.Website;
import com.nali.spreader.data.Content;
import com.nali.spreader.data.KeyValue;
import com.nali.spreader.factory.PassiveWorkshop;
import com.nali.spreader.factory.SimpleActionConfig;
import com.nali.spreader.factory.base.SingleTaskMachineImpl;
import com.nali.spreader.factory.exporter.SingleTaskExporter;
import com.nali.spreader.factory.passive.Input;
import com.nali.spreader.service.IContentService;
import com.nali.spreader.util.SpecialDateUtil;

@Component
public class ReplyWeibo extends SingleTaskMachineImpl implements PassiveWorkshop<KeyValue<Long, KeyValue<Long, String>>, KeyValue<Long, KeyValue<Long,Boolean>>> {
	@Autowired
	private IContentService contentService;
	@Autowired
	private WeiboRobotUserHolder weiboRobotUserHolder;

	public ReplyWeibo() {
		super(SimpleActionConfig.replyWeibo, Website.weibo, Channel.normal);
	}

	@Input
	public void work(SingleTaskExporter exporter, KeyValue<Long, String> data) {
		Long contentId = data.getKey();
		String text = data.getValue();
		Long robotUid = weiboRobotUserHolder.getRobotUid();
		work(robotUid, contentId, text, exporter);
	}

	@Override
	public void work(KeyValue<Long, KeyValue<Long, String>> data, SingleTaskExporter exporter) {
		Long robotUid = data.getKey();
		KeyValue<Long, String> contentReply = data.getValue();
		Long contentId = contentReply.getKey();
		String text = contentReply.getValue();
		work(robotUid, contentId, text, exporter);
	}
	
	private void work(Long robotUid, Long contentId, String text, SingleTaskExporter exporter) {
		Content content = contentService.getContentById(contentId);

		Map<String, Object> contents = CollectionUtils.newHashMap(6);
		contents.put("id", robotUid);
		contents.put("contentId", contentId);
		contents.put("websiteContentId", content.getWebsiteContentId());
		contents.put("websiteUid", content.getWebsiteUid());
		contents.put("entry", content.getEntry());
		contents.put("text", text);
		exporter.createTask(contents, robotUid, SpecialDateUtil.afterToday(3));
	}

	@Override
	public void handleResult(Date updateTime, KeyValue<Long, KeyValue<Long,Boolean>> data) {
	}
	
}
