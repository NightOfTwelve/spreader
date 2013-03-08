package com.nali.spreader.analyzer.apple;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nali.spreader.config.CommentAppConfig;
import com.nali.spreader.constants.Website;
import com.nali.spreader.data.AppInfo;
import com.nali.spreader.factory.TaskProduceLine;
import com.nali.spreader.factory.config.Configable;
import com.nali.spreader.factory.config.desc.ClassDescription;
import com.nali.spreader.factory.passive.AutowireProductLine;
import com.nali.spreader.factory.regular.RegularAnalyzer;
import com.nali.spreader.service.IAppDownlodService;
import com.nali.spreader.util.random.RandomUtil;
import com.nali.spreader.util.random.WeightRandomer;
import com.nali.spreader.workshop.apple.DownloadApp;
import com.nali.spreader.workshop.apple.DownloadApp.AppCommentDto;

@Component
@ClassDescription("评论Apple应用")
public class CommentApp implements RegularAnalyzer,
		Configable<CommentAppConfig> {
	@Autowired
	private IAppDownlodService appDownlodService;
	@AutowireProductLine
	private TaskProduceLine<DownloadApp.AppCommentDto> downloadApp;
	private static WeightRandomer<Integer> starRandomer;
	private List<String> titles;
	private List<String> comments;
	private AppInfo appInfo;
	private int count;
	private Integer starOnlyRate;
	private Integer secondsDelay;
	
	@Override
	public String work() {
		List<Long> assignUids = appDownlodService.assignUids(
				Website.apple.getId(), appInfo.getAppSource(),
				appInfo.getAppId(), count);
		int startOnlyCount = starOnlyRate * assignUids.size() / 100;
		int normalCount = assignUids.size() - startOnlyCount;
		List<String> titles = RandomUtil.fakeRandomItems(this.titles, normalCount);
		List<String> comments = RandomUtil.fakeRandomItems(this.comments, normalCount);
		Collections.shuffle(assignUids);
		Date startTime = null;
		if(secondsDelay!=null) {
			startTime = DateUtils.addMinutes(new Date(), 10);
		}
		for (int i = 0; i < assignUids.size(); i++) {
			Long uid = assignUids.get(i);
			
			DownloadApp.AppCommentDto dto = new AppCommentDto();
			dto.setUid(uid);
			dto.setAppId(appInfo.getAppId());
			dto.setAppSource(appInfo.getAppSource());
			dto.setMillionBite(appInfo.getMillionBite());
			dto.setUrl(appInfo.getUrl());
			dto.setStars(starRandomer.get());
			if (i < startOnlyCount) {
				dto.setTitle(StringUtils.EMPTY);
				dto.setComment(StringUtils.EMPTY);
			} else {
				dto.setTitle(titles.get(i - startOnlyCount));
				dto.setComment(comments.get(i - startOnlyCount));
			}
			if(startTime!=null) {
				startTime = DateUtils.addSeconds(startTime, secondsDelay);
				dto.setCommentStartTime(startTime);
			}
			downloadApp.send(dto);
		}
		return "预计下载：" + count + ", 实际下载：" + assignUids.size();
	}

	@Override
	public void init(CommentAppConfig dto) {
		if (dto.getUrl() == null || dto.getCount() == null) {
			throw new IllegalArgumentException(
					"url and count must not be empty");
		}
		appInfo = appDownlodService.parseUrl(dto.getUrl());
		count = dto.getCount();
		Integer fourStar = dto.getFourStar();
		starOnlyRate = dto.getStarOnly();
		if (fourStar == null) {
			fourStar = 20;
		}
		if (starOnlyRate == null) {
			starOnlyRate = 0;
		}
		starRandomer = new WeightRandomer<Integer>();
		starRandomer.add(4, fourStar);
		starRandomer.add(5, 100 - fourStar);
		titles = dto.getTitle();
		comments = dto.getComments();
		secondsDelay=dto.getSecondsDelay();
	}

}
