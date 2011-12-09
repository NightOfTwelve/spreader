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
import com.nali.spreader.service.IContentService;
import com.nali.spreader.util.SpecialDateUtil;

@Component
public class ForwardWeiboContent extends SingleTaskMachineImpl implements PassiveWorkshop<KeyValue<Long, Long>, KeyValue<Long, KeyValue<Long, Long>>> {
	@Autowired
	private IContentService contentService;

	public ForwardWeiboContent() {
		super(SimpleActionConfig.forwardWeiboContent, Website.weibo, Channel.normal);
	}

	@Override
	public void work(KeyValue<Long, Long> data, SingleTaskExporter exporter) {
		work(data.getKey(), data.getValue(), exporter);
	}

	private void work(Long robotUid, Long contentId, SingleTaskExporter exporter) {
		Content content = contentService.getContentById(contentId);
		Map<String, Object> contents = CollectionUtils.newHashMap(5);
		contents.put("id", robotUid);
		contents.put("contentId", contentId);
		contents.put("websiteContentId", content.getWebsiteContentId());
		contents.put("websiteUid", content.getWebsiteUid());
		contents.put("entry", content.getEntry());
		exporter.createTask(contents, robotUid, SpecialDateUtil.afterToday(3));
		contentService.addPostContentId(robotUid, contentId);
	}

	@Override
	public void handleResult(Date updateTime, KeyValue<Long, KeyValue<Long, Long>> resultObject) {
		//do nothing??
	}

}
