package com.nali.spreader.analyzer.apple;

import java.util.List;

import org.apache.commons.lang.StringUtils;
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
	private static WeightRandomer<Integer> starOnlyRandomer;
	private static final int START_ONLY = 1;
	private List<String> titles;
	private List<String> comments;
	private AppInfo appInfo;
	private int count;

	@Override
	public String work() {
		List<Long> assignUids = appDownlodService.assignUids(
				Website.apple.getId(), appInfo.getAppSource(),
				appInfo.getAppId(), count);
		for (Long uid : assignUids) {
			DownloadApp.AppCommentDto dto = new AppCommentDto();
			Integer startOnly = starOnlyRandomer.get();
			dto.setUid(uid);
			dto.setAppId(appInfo.getAppId());
			dto.setAppSource(appInfo.getAppSource());
			dto.setMillionBite(appInfo.getMillionBite());
			dto.setUrl(appInfo.getUrl());
			if (START_ONLY == startOnly.intValue()) {
				dto.setTitle(StringUtils.EMPTY);
				dto.setComment(StringUtils.EMPTY);
			} else {
				dto.setTitle(getRandomContent(titles));
				dto.setComment(getRandomContent(comments));
			}
			dto.setStars(starRandomer.get());
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
		if (dto.getMillionBite() == null) {
			throw new IllegalArgumentException("millionBite must not be empty");
		}
		appInfo = appDownlodService.parseUrl(dto.getUrl());
		appInfo.setMillionBite(dto.getMillionBite());
		count = dto.getCount();
		Integer fourStar = dto.getFourStar();
		Integer starOnly = dto.getStarOnly();
		if (fourStar == null) {
			fourStar = 20;
		}
		if (starOnly == null) {
			starOnly = 20;
		}
		starRandomer = new WeightRandomer<Integer>();
		starOnlyRandomer = new WeightRandomer<Integer>();
		starRandomer.add(4, fourStar);
		starRandomer.add(5, 100 - fourStar);
		starOnlyRandomer.add(START_ONLY, starOnly);
		starOnlyRandomer.add(2, 100 - starOnly);
		titles = dto.getTitle();
		comments = dto.getComments();
	}

	private String getRandomContent(List<String> list) {
		if (list == null) {
			return StringUtils.EMPTY;
		}
		return RandomUtil.randomItem(list);
	}
}
