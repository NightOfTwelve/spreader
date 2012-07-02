package com.nali.spreader.workshop;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nali.common.util.CollectionUtils;
import com.nali.spreader.constants.Channel;
import com.nali.spreader.constants.Website;
import com.nali.spreader.data.Content;
import com.nali.spreader.data.KeyValue;
import com.nali.spreader.dto.FetchUserWeiboDto;
import com.nali.spreader.factory.SimpleActionConfig;
import com.nali.spreader.factory.base.ContextMeta;
import com.nali.spreader.factory.base.SingleTaskMachineImpl;
import com.nali.spreader.factory.base.SingleTaskMeta;
import com.nali.spreader.factory.exporter.SingleTaskExporter;
import com.nali.spreader.factory.passive.Input;
import com.nali.spreader.factory.passive.SinglePassiveTaskProducer;
import com.nali.spreader.factory.result.ContextedResultProcessor;
import com.nali.spreader.service.IContentKeywordService;
import com.nali.spreader.service.IContentService;
import com.nali.spreader.service.IGlobalUserService;
import com.nali.spreader.service.IKeywordService;
import com.nali.spreader.util.SpecialDateUtil;

@Component
public class FetchWeiboContent extends SingleTaskMachineImpl implements
		SinglePassiveTaskProducer<KeyValue<Long, Long>>,
		ContextedResultProcessor<List<Content>, SingleTaskMeta> {
	private static Logger logger = Logger.getLogger(FetchWeiboContent.class);
	@Autowired
	private IContentService contentService;
	@Autowired
	private WeiboRobotUserHolder robotUserHolder;
	@Autowired
	private IGlobalUserService globalUserService;
	@Autowired
	private IKeywordService keywordService;
	@Autowired
	private IContentKeywordService contentKeywordService;

	public FetchWeiboContent() {
		super(SimpleActionConfig.fetchWeiboContent, Website.weibo, Channel.fetch);
		ContextMeta contextMeta = new ContextMeta("keywords");
		setContextMeta(contextMeta);
	}

	@Override
	public void work(KeyValue<Long, Long> data, SingleTaskExporter exporter) {
		Long uid = data.getKey();
		Long robotId = data.getValue();
		work(uid, robotId, null, exporter);
	}

	@Input
	public void work(SingleTaskExporter exporter, KeyValue<Long, Date> data) {
		Long uid = data.getKey();
		Date lastFetchTime = data.getValue();
		work(uid, null, lastFetchTime, exporter);
	}
	
	@Input
	public void word(FetchUserWeiboDto dto, SingleTaskExporter exporter) {
		Long uid = dto.getUid();
		List<String> keywords = dto.getKeywords();
		List<String> randomKeywords = dto.getRandomkeywords();
		List<String> wrokList = new ArrayList<String>();
		if (!CollectionUtils.isEmpty(keywords)) {
			wrokList.addAll(keywords);
		}
		if (!CollectionUtils.isEmpty(randomKeywords)) {
			wrokList.addAll(randomKeywords);
		}
		Date lastFetchTime = dto.getLastFetchTime();
		exporter.setProperty("keywords", wrokList);
		work(uid, null, lastFetchTime, exporter);
	}

	private void work(Long uid, Long robotId, Date lastFetchTime, SingleTaskExporter exporter) {
		if (robotId == null) {
			robotId = robotUserHolder.getRobotUid();// TODO 不分配具体的人
		}
		if (lastFetchTime == null) {
			lastFetchTime = contentService.getAndTouchLastFetchTime(uid);
		} else {
			contentService.getAndTouchLastFetchTime(uid);
		}
		Long websiteUid = globalUserService.getWebsiteUid(uid);
		if (websiteUid == null) {
			logger.warn("not found user:" + uid);
			return;
		}
		exporter.setProperty("websiteUid", websiteUid);
		exporter.setProperty("lastFetchTime", lastFetchTime);
		exporter.send(robotId, SpecialDateUtil.afterToday(2));
	}

	@Override
	public void handleResult(Date updateTime, List<Content> contents,
			Map<String, Object> contextContents, Long uid) {
		List<String> keywords = (List<String>) contextContents.get("keywords");
		for (Content content : contents) {
			content.setSyncDate(updateTime);
			content.setType(Content.TYPE_WEIBO);
			content.setWebsiteId(Website.weibo.getId());
			// Long uid = userService.assignUser(content.getWebsiteUid());
			// if(uid!=null) {
			// fetchWeiboUserMainPage.send(uid);
			// content.setUid(uid);
			// }
			Long contentId = contentService.assignContentId(content);
			if(!CollectionUtils.isEmpty(keywords)){
				for(String keyword:keywords){
					Long keywordId = this.keywordService.getOrAssignKeywordIdByName(keyword);
					this.contentKeywordService.getOrAssignContentKeywordId(contentId, keywordId);
				}
			}
		}
	}
}
