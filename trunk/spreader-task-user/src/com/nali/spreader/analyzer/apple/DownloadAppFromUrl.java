package com.nali.spreader.analyzer.apple;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nali.spreader.config.UrlDownloadCount;
import com.nali.spreader.constants.Website;
import com.nali.spreader.data.AppInfo;
import com.nali.spreader.data.KeyValue;
import com.nali.spreader.factory.TaskProduceLine;
import com.nali.spreader.factory.config.Configable;
import com.nali.spreader.factory.config.desc.ClassDescription;
import com.nali.spreader.factory.passive.AutowireProductLine;
import com.nali.spreader.factory.regular.RegularAnalyzer;
import com.nali.spreader.service.IAppDownlodService;

@Component
@ClassDescription("下载apple应用")
public class DownloadAppFromUrl implements RegularAnalyzer,Configable<UrlDownloadCount> {
	@Autowired
	private IAppDownlodService appDownlodService;
	@AutowireProductLine
	private TaskProduceLine<KeyValue<Long, AppInfo>> downloadApp;
	private AppInfo appInfo;
	private int count;

	@Override
	public String work() {
		List<Long> assignUids = appDownlodService.assignUids(Website.apple.getId(), appInfo.getAppSource(), appInfo.getAppId(), count);
		for (Long uid : assignUids) {
			downloadApp.send(new KeyValue<Long, AppInfo>(uid, appInfo));
		}
		return "预计下载：" + count + ", 实际下载：" + assignUids.size();
	}

	@Override
	public void init(UrlDownloadCount dto) {
		if(dto.getUrl()==null || dto.getCount()==null) {
			throw new IllegalArgumentException("url and count must not be empty");
		}
		if(dto.getMillionBite()==null) {
			throw new IllegalArgumentException("millionBite must not be empty");
		}
		appInfo = appDownlodService.parseUrl(dto.getUrl());
		appInfo.setMillionBite(dto.getMillionBite());
		appInfo.setRunApp(dto.isRunApp());
		appInfo.setWaitToEnd(dto.isWaitToEnd());
		count = dto.getCount();
	}

}
