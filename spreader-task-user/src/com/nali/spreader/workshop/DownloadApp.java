package com.nali.spreader.workshop;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nali.spreader.constants.Channel;
import com.nali.spreader.constants.Website;
import com.nali.spreader.data.AppInfo;
import com.nali.spreader.data.KeyValue;
import com.nali.spreader.factory.ContextedPassiveWorkshop;
import com.nali.spreader.factory.SimpleActionConfig;
import com.nali.spreader.factory.base.SingleTaskMachineImpl;
import com.nali.spreader.factory.exporter.SingleTaskExporter;
import com.nali.spreader.model.ClientTask;
import com.nali.spreader.service.IAppDownlodService;
import com.nali.spreader.util.SpecialDateUtil;

@Component
public class DownloadApp extends SingleTaskMachineImpl implements ContextedPassiveWorkshop<KeyValue<Long, AppInfo>, Boolean> {
	private static final int BASE_PRIORITY = ClientTask.BASE_PRIORITY_MAX/10;
	@Autowired
	private IAppDownlodService appDownlodService;
	
	public DownloadApp() {
		super(SimpleActionConfig.downloadApp, Website.apple, Channel.intervention);
		setContextMeta("appSource", "appId");
	}

	@Override
	public void work(KeyValue<Long, AppInfo> data, SingleTaskExporter exporter) {
		Long uid = data.getKey();
		AppInfo appInfo = data.getValue();
		exporter.setProperty("appSource", appInfo.getAppSource());
		exporter.setProperty("appId", appInfo.getAppId());
		exporter.setProperty("url", appInfo.getUrl());
		exporter.setProperty("secondsWaitBase", appInfo.getSecondsWaitBase());
		exporter.setBasePriority(BASE_PRIORITY);
		exporter.send(uid, SpecialDateUtil.afterNow(10));
	}
	
	@Override
	public void handleResult(Date updateTime, Boolean resultObject, Map<String, Object> contextContents, Long uid) {
		String appSource = (String) contextContents.get("appSource");
		Long appId = (Long) contextContents.get("appId");
		appDownlodService.finishDownload(appSource, appId, uid);
	}

}
