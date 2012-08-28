package com.nali.spreader.workshop;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.nali.spreader.constants.Channel;
import com.nali.spreader.constants.Website;
import com.nali.spreader.data.KeyValue;
import com.nali.spreader.factory.ContextedPassiveWorkshop;
import com.nali.spreader.factory.SimpleActionConfig;
import com.nali.spreader.factory.base.SingleTaskMachineImpl;
import com.nali.spreader.factory.exporter.SingleTaskExporter;
import com.nali.spreader.model.ClientTask;
import com.nali.spreader.util.SpecialDateUtil;
import com.nali.spreader.workshop.CommentApple.CommentDto;

@Component
public class CommentApple extends SingleTaskMachineImpl implements ContextedPassiveWorkshop<KeyValue<Long, CommentDto>, Boolean> {
	private static final int BASE_PRIORITY = ClientTask.BASE_PRIORITY_MAX/10;
	
	public CommentApple() {
		super(SimpleActionConfig.commentApple, Website.apple, Channel.intervention);
		setContextMeta("appSource", "appId");
	}

	@Override
	public void work(KeyValue<Long, CommentDto> data, SingleTaskExporter exporter) {
		Long uid = data.getKey();
		CommentDto commentDto = data.getValue();
		exporter.setProperty("appSource", commentDto.getAppSource());
		exporter.setProperty("appId", commentDto.getAppId());
		exporter.setProperty("url", commentDto.getUrl());
		exporter.setProperty("title", commentDto.getTitle());
		exporter.setProperty("content", commentDto.getContent());
		exporter.setProperty("stars", commentDto.getStars());
		exporter.setBasePriority(BASE_PRIORITY);
		exporter.send(uid, SpecialDateUtil.afterNow(10));//TODO add start time
	}
	
	@Override
	public void handleResult(Date updateTime, Boolean resultObject, Map<String, Object> contextContents, Long uid) {
//		String appSource = (String) contextContents.get("appSource");
//		Long appId = (Long) contextContents.get("appId");
		//TODO do something
	}

	public static class CommentDto implements Serializable {
		private static final long serialVersionUID = -6628117760404432870L;
		private Long appId;
		private String appSource;
		private String url;
		private String title;
		private String content;
		private Integer stars;
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
		public Long getAppId() {
			return appId;
		}
		public void setAppId(Long appId) {
			this.appId = appId;
		}
		public String getAppSource() {
			return appSource;
		}
		public void setAppSource(String appSource) {
			this.appSource = appSource;
		}
		public Integer getStars() {
			return stars;
		}
		public void setStars(Integer stars) {
			this.stars = stars;
		}
	}
}
