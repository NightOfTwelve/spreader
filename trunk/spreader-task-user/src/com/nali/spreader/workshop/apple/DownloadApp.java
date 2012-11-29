package com.nali.spreader.workshop.apple;

import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nali.spreader.constants.Channel;
import com.nali.spreader.constants.Website;
import com.nali.spreader.data.AppInfo;
import com.nali.spreader.data.KeyValue;
import com.nali.spreader.factory.ContextedPassiveWorkshop;
import com.nali.spreader.factory.SimpleActionConfig;
import com.nali.spreader.factory.TaskProduceLine;
import com.nali.spreader.factory.base.SingleTaskMachineImpl;
import com.nali.spreader.factory.exporter.SingleTaskExporter;
import com.nali.spreader.factory.passive.AutowireProductLine;
import com.nali.spreader.model.ClientTask;
import com.nali.spreader.service.IAppDownlodService;
import com.nali.spreader.util.SpecialDateUtil;
import com.nali.spreader.workshop.apple.CommentApple.CommentDto;
import com.nali.spreader.workshop.apple.ReturnVisitApplePassive.ReturnVisitDto;

@Component
public class DownloadApp extends SingleTaskMachineImpl implements ContextedPassiveWorkshop<KeyValue<Long, AppInfo>, Object> {
	private static final int BASE_PRIORITY = ClientTask.BASE_PRIORITY_MAX/10;
	private static Logger logger = Logger.getLogger(DownloadApp.class);
	@Autowired
	private IAppDownlodService appDownlodService;

	@AutowireProductLine
	private TaskProduceLine<KeyValue<Long, CommentDto>> commentApplePassive;
	@AutowireProductLine
	private TaskProduceLine<ReturnVisitDto> returnVisitApplePassive;
	
	public DownloadApp() {
		super(SimpleActionConfig.downloadApp, Website.apple, Channel.intervention);
		setContextMeta(new String[] {"appSource", "appId"}, "url", "millionBite");
	}

	@Override
	public void work(KeyValue<Long, AppInfo> data, SingleTaskExporter exporter) {
		Long uid = data.getKey();
		AppInfo appInfo = data.getValue();
		exporter.setProperty("appSource", appInfo.getAppSource());
		exporter.setProperty("appId", appInfo.getAppId());
		exporter.setProperty("url", appInfo.getUrl());
		exporter.setProperty("millionBite", appInfo.getMillionBite());
		exporter.setProperty("runApp", appInfo.isRunApp());
		exporter.setProperty("waitToEnd", appInfo.isWaitToEnd());
		exporter.setBasePriority(BASE_PRIORITY);
		exporter.send(uid, SpecialDateUtil.afterNow(10));
	}
	
	@Override
	public void handleResult(Date updateTime, Object rlt, Map<String, Object> contextContents, Long uid) {
		Long signTime;
		if (rlt instanceof Long) {
			signTime = (Long) rlt;
		} else {
			logger.error("receive not Long type:" + rlt);
			return;
		}
		String appSource = (String) contextContents.get("appSource");
		String url = (String) contextContents.get("url");
		Double millionBite = (Double) contextContents.get("millionBite");
		ReturnVisitDto returnVisitDto = new ReturnVisitDto(url, millionBite, signTime, uid);
		returnVisitApplePassive.send(returnVisitDto);
		Long appId = (Long) contextContents.get("appId");
		appDownlodService.finishDownload(appSource, appId, uid);
		CommentDto commentDto = new CommentDto();
		commentDto.setAppId(appId);
		commentDto.setAppSource(appSource);
		commentDto.setUrl(url);
		KeyValue<Long, CommentDto> data = new KeyValue<Long, CommentDto>(uid, commentDto);
		commentApplePassive.send(data);
	}

}
