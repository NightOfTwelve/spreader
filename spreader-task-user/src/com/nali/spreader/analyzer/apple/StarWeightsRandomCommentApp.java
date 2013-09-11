package com.nali.spreader.analyzer.apple;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nali.spreader.analyzer.apple.StarWeightsRandomCommentApp.StarWeightsCommentAppConfig;
import com.nali.spreader.constants.Website;
import com.nali.spreader.data.AppInfo;
import com.nali.spreader.factory.TaskProduceLine;
import com.nali.spreader.factory.config.Configable;
import com.nali.spreader.factory.config.desc.ClassDescription;
import com.nali.spreader.factory.config.desc.PropertyDescription;
import com.nali.spreader.factory.passive.AutowireProductLine;
import com.nali.spreader.factory.regular.RegularAnalyzer;
import com.nali.spreader.service.IAppDownlodService;
import com.nali.spreader.util.random.RandomUtil;
import com.nali.spreader.util.random.WeightRandomer;
import com.nali.spreader.workshop.apple.DownloadApp;
import com.nali.spreader.workshop.apple.DownloadApp.AppCommentDto;

@Component
@ClassDescription("按星级比例随机评论Apple应用")
public class StarWeightsRandomCommentApp implements RegularAnalyzer,
		Configable<StarWeightsCommentAppConfig> {
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
	private Integer offset;

	@Override
	public String work() {
		List<Long> assignUids = appDownlodService.assignUids(
				Website.apple.getId(), appInfo.getAppSource(),
				appInfo.getAppId(), count, offset);
		int startOnlyCount = starOnlyRate * assignUids.size() / 100;
		int normalCount = assignUids.size() - startOnlyCount;
		List<String> titles = RandomUtil.fakeRandomItems(this.titles,
				normalCount);
		List<String> comments = RandomUtil.fakeRandomItems(this.comments,
				normalCount);
		Collections.shuffle(assignUids);
		Date startTime = null;
		if (secondsDelay != null) {
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
			if (startTime != null) {
				startTime = DateUtils.addSeconds(startTime, secondsDelay);
				dto.setCommentStartTime(startTime);
			}
			downloadApp.send(dto);
		}
		return "预计下载：" + count + ", 实际下载：" + assignUids.size();
	}

	private static Pattern lineSpliter = Pattern.compile("[\r\n]+");

	@Override
	public void init(StarWeightsCommentAppConfig dto) {
		if (dto.getUrl() == null || dto.getCount() == null) {
			throw new IllegalArgumentException(
					"url and count must not be empty");
		}
		appInfo = appDownlodService.parseUrl(dto.getUrl());
		count = dto.getCount();
		Integer fourStar = dto.getFourStar();
		Integer oneStar = dto.getOneStar();
		Integer twoStar = dto.getTwoStar();
		Integer threeStar = dto.getThreeStar();
		starOnlyRate = dto.getStarOnly();
		if (oneStar == null) {
			oneStar = 0;
		}
		if (twoStar == null) {
			twoStar = 0;
		}
		if (threeStar == null) {
			threeStar = 0;
		}
		if (fourStar == null) {
			fourStar = 0;
		}
		Integer fiveStar = 100 - oneStar - twoStar - threeStar - fourStar;
		if (fiveStar < 0 || fiveStar > 100) {
			throw new IllegalArgumentException("fiveStar:" + fiveStar);
		}
		if (starOnlyRate == null) {
			starOnlyRate = 0;
		}
		if (offset == null) {
			offset = 0;
		}
		starRandomer = new WeightRandomer<Integer>();
		starRandomer.add(1, oneStar);
		starRandomer.add(2, twoStar);
		starRandomer.add(3, threeStar);
		starRandomer.add(4, fourStar);
		starRandomer.add(5, fiveStar);
		titles = Arrays.asList(lineSpliter.split(dto.getTitle()));
		comments = Arrays.asList(lineSpliter.split(dto.getComments()));
		secondsDelay = dto.getSecondsDelay();
	}

	public static class StarWeightsCommentAppConfig implements Serializable {
		private static final long serialVersionUID = 260820349582375781L;
		@PropertyDescription("app的url")
		private String url;
		@PropertyDescription("下载次数")
		private Integer count;
		@PropertyDescription("app大小（MB）")
		private Double millionBite;
		@PropertyDescription("评论的标题")
		private String title;
		@PropertyDescription("评论内容")
		private String comments;
		@PropertyDescription("只评星不做评论的比例")
		private Integer starOnly;
		@PropertyDescription("一星的比例")
		private Integer oneStar;
		@PropertyDescription("二星的比例")
		private Integer twoStar;
		@PropertyDescription("三星的比例")
		private Integer threeStar;
		@PropertyDescription("四星的比例")
		private Integer fourStar;
		@PropertyDescription("每次回复间隔（秒）")
		private Integer secondsDelay;
		@PropertyDescription("跳过多少个帐号")
		private Integer offset;

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public Integer getCount() {
			return count;
		}

		public void setCount(Integer count) {
			this.count = count;
		}

		public Double getMillionBite() {
			return millionBite;
		}

		public void setMillionBite(Double millionBite) {
			this.millionBite = millionBite;
		}

		public Integer getStarOnly() {
			return starOnly;
		}

		public void setStarOnly(Integer starOnly) {
			this.starOnly = starOnly;
		}

		public Integer getFourStar() {
			return fourStar;
		}

		public void setFourStar(Integer fourStar) {
			this.fourStar = fourStar;
		}

		public Integer getSecondsDelay() {
			return secondsDelay;
		}

		public void setSecondsDelay(Integer secondsDelay) {
			this.secondsDelay = secondsDelay;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getComments() {
			return comments;
		}

		public void setComments(String comments) {
			this.comments = comments;
		}

		public Integer getOffset() {
			return offset;
		}

		public void setOffset(Integer offset) {
			this.offset = offset;
		}

		public Integer getOneStar() {
			return oneStar;
		}

		public void setOneStar(Integer oneStar) {
			this.oneStar = oneStar;
		}

		public Integer getTwoStar() {
			return twoStar;
		}

		public void setTwoStar(Integer twoStar) {
			this.twoStar = twoStar;
		}

		public Integer getThreeStar() {
			return threeStar;
		}

		public void setThreeStar(Integer threeStar) {
			this.threeStar = threeStar;
		}
	}
}
