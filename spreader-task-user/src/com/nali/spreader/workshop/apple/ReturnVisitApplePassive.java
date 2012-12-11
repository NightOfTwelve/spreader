package com.nali.spreader.workshop.apple;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import com.nali.spreader.constants.Channel;
import com.nali.spreader.constants.Website;
import com.nali.spreader.factory.PassiveWorkshop;
import com.nali.spreader.factory.SimpleActionConfig;
import com.nali.spreader.factory.base.SingleTaskMachineImpl;
import com.nali.spreader.factory.config.Configable;
import com.nali.spreader.factory.config.desc.ClassDescription;
import com.nali.spreader.factory.exporter.SingleTaskExporter;
import com.nali.spreader.util.random.LongRandomer;
import com.nali.spreader.workshop.apple.ReturnVisitApplePassive.ReturnVisitDto;

@Component
@ClassDescription("apple回访时间（天）")
public class ReturnVisitApplePassive extends SingleTaskMachineImpl implements PassiveWorkshop<ReturnVisitDto, Boolean>, Configable<Integer> {
	private LongRandomer millisRandomer;
	private long duringMillis = TimeUnit.DAYS.toMillis(7);

	public ReturnVisitApplePassive() {
		super(SimpleActionConfig.returnVisitApple, Website.apple, Channel.instant);
	}
	
	@Override
	public void init(Integer dateCount) {
		long millis = TimeUnit.DAYS.toMillis(dateCount);
		millisRandomer = new LongRandomer(millis*9/10, millis*11/10 + 1);
	}

	@Override
	public void work(ReturnVisitDto data, SingleTaskExporter exporter) {
		Long uid = data.getUid();
		exporter.setProperty("millionBite", data.getMillionBite());
		exporter.setProperty("signTime", data.getSignTime());
		exporter.setProperty("url", data.getUrl());
		long startMillis = System.currentTimeMillis()+millisRandomer.get();
		long expireMillis = startMillis + duringMillis;
		exporter.setTimes(new Date(startMillis), new Date(expireMillis));
		exporter.setUid(uid);
		exporter.send();
	}
	
	public static class ReturnVisitDto {
		private String url;
		private Double millionBite;
		private Long signTime;
		private Long uid;
		public ReturnVisitDto(String url, Double millionBite, Long signTime, Long uid) {
			super();
			this.url = url;
			this.millionBite = millionBite;
			this.signTime = signTime;
			this.uid = uid;
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
		public Long getSignTime() {
			return signTime;
		}
		public void setSignTime(Long signTime) {
			this.signTime = signTime;
		}
		public Long getUid() {
			return uid;
		}
		public void setUid(Long uid) {
			this.uid = uid;
		}

	}

	@Override
	public void handleResult(Date updateTime, Boolean resultObject) {
		//do nothing
	}
	
}
