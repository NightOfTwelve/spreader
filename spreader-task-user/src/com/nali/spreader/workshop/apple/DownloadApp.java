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
import com.nali.spreader.factory.passive.Input;
import com.nali.spreader.model.ClientTask;
import com.nali.spreader.service.IAppDownlodService;
import com.nali.spreader.util.SpecialDateUtil;
import com.nali.spreader.workshop.apple.CommentApple.CommentDto;
import com.nali.spreader.workshop.apple.ReturnVisitApplePassive.ReturnVisitDto;

@Component
public class DownloadApp extends SingleTaskMachineImpl implements
		ContextedPassiveWorkshop<KeyValue<Long, AppInfo>, Object> {
	private static final int BASE_PRIORITY = ClientTask.BASE_PRIORITY_MAX / 10;
	private static Logger logger = Logger.getLogger(DownloadApp.class);
	@Autowired
	private IAppDownlodService appDownlodService;

	@AutowireProductLine
	private TaskProduceLine<KeyValue<Long, CommentDto>> commentApple;
	@AutowireProductLine
	private TaskProduceLine<ReturnVisitDto> returnVisitApplePassive;

	public DownloadApp() {
		super(SimpleActionConfig.downloadApp, Website.apple,
				Channel.intervention);
		setContextMeta(new String[] { "appSource", "appId" }, "url",
				"millionBite", "stars", "title", "comment");
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

	@Input
	public void work(AppCommentDto dto, SingleTaskExporter exporter) {
		Long uid = dto.getUid();
		String appSource = dto.getAppSource();
		Long appId = dto.getAppId();
		String url = dto.getUrl();
		Double millionBite = dto.getMillionBite();
		String comment = dto.getComment();
		Integer stars = dto.getStars();
		String title = dto.getTitle();
		exporter.setProperty("appSource", appSource);
		exporter.setProperty("appId", appId);
		exporter.setProperty("url", url);
		exporter.setProperty("millionBite", millionBite);
		exporter.setProperty("comment", comment);
		exporter.setProperty("stars", stars);
		exporter.setProperty("title", title);
		exporter.setBasePriority(BASE_PRIORITY);
		exporter.send(uid, SpecialDateUtil.afterNow(10));
	}

	public static class AppCommentDto {
		private Long uid;
		private String appSource;
		private Long appId;
		private String url;
		private Double millionBite;
		private String comment;
		private Integer stars;
		private String title;

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public Integer getStars() {
			return stars;
		}

		public void setStars(Integer stars) {
			this.stars = stars;
		}

		public Long getUid() {
			return uid;
		}

		public void setUid(Long uid) {
			this.uid = uid;
		}

		public String getAppSource() {
			return appSource;
		}

		public void setAppSource(String appSource) {
			this.appSource = appSource;
		}

		public Long getAppId() {
			return appId;
		}

		public void setAppId(Long appId) {
			this.appId = appId;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public Double getMillionBite() {
			return millionBite;
		}

		public void setMillionBite(Double millionBite) {
			this.millionBite = millionBite;
		}

		public String getComment() {
			return comment;
		}

		public void setComment(String comment) {
			this.comment = comment;
		}
	}

	@Override
	public void handleResult(Date updateTime, Object rlt,
			Map<String, Object> contextContents, Long uid) {
		String appSource = (String) contextContents.get("appSource");
		String url = (String) contextContents.get("url");
		Double millionBite = (Double) contextContents.get("millionBite");
		String comment = (String) contextContents.get("comment");
		String title = (String) contextContents.get("title");
		Integer stars = (Integer) contextContents.get("stars");
		Long signTime;
		if (rlt != null) {
			if (rlt instanceof Long) {
				signTime = (Long) rlt;
				ReturnVisitDto returnVisitDto = new ReturnVisitDto(url,
						millionBite, signTime, uid);
				returnVisitApplePassive.send(returnVisitDto);
			} else {
				logger.error("receive not Long type:" + rlt);
				return;
			}
		}
		if (stars != null) {
			Long appId = (Long) contextContents.get("appId");
			appDownlodService.finishDownload(appSource, appId, uid);
			CommentDto commentDto = new CommentDto();
			commentDto.setAppId(appId);
			commentDto.setAppSource(appSource);
			commentDto.setUrl(url);
			commentDto.setStars(stars);
			commentDto.setTitle(title);
			commentDto.setContent(comment);
			KeyValue<Long, CommentDto> data = new KeyValue<Long, CommentDto>(
					uid, commentDto);
			commentApple.send(data);
		}
	}
}
