package com.nali.spreader.workshop;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
		exporter.setProperty("id", robotUid);
		exporter.setProperty("contentId", contentId);
		exporter.setProperty("websiteContentId", content.getWebsiteContentId());
		exporter.setProperty("websiteUid", content.getWebsiteUid());
		exporter.setProperty("entry", content.getEntry());
		exporter.send(robotUid, SpecialDateUtil.afterToday(3));
		contentService.addPostContentId(robotUid, contentId);
	}

	@Override
	public void handleResult(Date updateTime, KeyValue<Long, KeyValue<Long, Long>> resultObject) {
		//do nothing??
	}

}
