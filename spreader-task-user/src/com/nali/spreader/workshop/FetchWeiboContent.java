package com.nali.spreader.workshop;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
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
import com.nali.spreader.factory.passive.Input;
import com.nali.spreader.service.IContentService;
import com.nali.spreader.service.IGlobalUserService;
import com.nali.spreader.util.SpecialDateUtil;

@Component
public class FetchWeiboContent extends SingleTaskMachineImpl implements
		PassiveWorkshop<KeyValue<Long, Long>, List<Content>> {
	private static Logger logger = Logger.getLogger(FetchWeiboContent.class);
	@Autowired
	private IContentService contentService;
	@Autowired
	private WeiboRobotUserHolder robotUserHolder;
	@Autowired
	private IGlobalUserService globalUserService;

	public FetchWeiboContent() {
		super(SimpleActionConfig.fetchWeiboContent, Website.weibo, Channel.fetch);
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
		if(websiteUid==null) {
			logger.warn("not found user:" + uid);
			return;
		}
		exporter.setProperty("websiteUid", websiteUid);
		exporter.setProperty("lastFetchTime", lastFetchTime);
		exporter.send(robotId, SpecialDateUtil.afterToday(2));
	}

	@Override
	public void handleResult(Date updateTime, List<Content> contents) {
		for (Content content : contents) {
			content.setSyncDate(updateTime);
			content.setType(Content.TYPE_WEIBO);
			content.setWebsiteId(Website.weibo.getId());
			// Long uid = userService.assignUser(content.getWebsiteUid());
			// if(uid!=null) {
			// fetchWeiboUserMainPage.send(uid);
			// content.setUid(uid);
			// }
			contentService.saveContent(content);
		}
	}

}
