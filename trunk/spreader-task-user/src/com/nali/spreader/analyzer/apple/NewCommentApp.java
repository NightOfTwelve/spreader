package com.nali.spreader.analyzer.apple;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nali.spreader.analyzer.apple.NewCommentApp.NewCommentAppConfig;
import com.nali.spreader.constants.Website;
import com.nali.spreader.data.AppInfo;
import com.nali.spreader.factory.TaskProduceLine;
import com.nali.spreader.factory.config.Configable;
import com.nali.spreader.factory.config.desc.ClassDescription;
import com.nali.spreader.factory.config.desc.PropertyDescription;
import com.nali.spreader.factory.passive.AutowireProductLine;
import com.nali.spreader.factory.regular.RegularAnalyzer;
import com.nali.spreader.service.IAppDownlodService;
import com.nali.spreader.util.random.WeightRandomer;
import com.nali.spreader.workshop.apple.DownloadApp;
import com.nali.spreader.workshop.apple.DownloadApp.AppCommentDto;

@Component
@ClassDescription("新评论Apple应用")
public class NewCommentApp implements RegularAnalyzer, Configable<NewCommentAppConfig> {
	@Autowired
	private IAppDownlodService appDownlodService;
	@AutowireProductLine
	private TaskProduceLine<DownloadApp.AppCommentDto> downloadApp;
	private static WeightRandomer<Integer> starRandomer;
	private List<String[]> comments;
	private AppInfo appInfo;
	private Integer starOnlyCount;
	private Integer offset;
	private Integer secondsDelay;

	@Override
	public String work() {
		int normalCount = comments.size();
		int count = normalCount + starOnlyCount;
		List<Long> assignUids = appDownlodService.assignUids(Website.apple.getId(), appInfo.getAppSource(),
				appInfo.getAppId(), count, offset);
		Collections.shuffle(assignUids);
		List<String[]> comments = new ArrayList<String[]>(this.comments);
		Collections.shuffle(comments);
		int errorCount=0;
		Date startTime = null;
		if(secondsDelay!=null) {
			startTime = DateUtils.addMinutes(new Date(), 10);//first delay
		}
		for (int i = 0; i < assignUids.size(); i++) {
			Long uid = assignUids.get(i);

			DownloadApp.AppCommentDto dto = new AppCommentDto();
			dto.setUid(uid);
			dto.setAppId(appInfo.getAppId());
			dto.setAppSource(appInfo.getAppSource());
			dto.setUrl(appInfo.getUrl());
			dto.setStars(starRandomer.get());
			if (i < normalCount) {
				String[] comment = comments.get(i);
				if(comment.length!=2) {
					errorCount++;
					if(comment.length<2) {
						continue;
					}
				}
				dto.setTitle(comment[0]);
				dto.setComment(comment[1]);
			} else {
				dto.setTitle(StringUtils.EMPTY);
				dto.setComment(StringUtils.EMPTY);
			}
			if(startTime!=null) {
				startTime = DateUtils.addSeconds(startTime, secondsDelay);
				dto.setCommentStartTime(startTime);
			}
			downloadApp.send(dto);
		}
		String errorMsg;
		if(errorCount==0) {
			errorMsg = "";
		} else {
			errorMsg = "警告！有" + errorCount + "行错误格式的评论内容，";
		}
		return errorMsg + "预计下载：" + assignUids.size();
	}

	@Override
	public void init(NewCommentAppConfig dto) {
		if (dto.getUrl() == null) {
			throw new IllegalArgumentException("url must not be empty");
		}
		appInfo = appDownlodService.parseUrl(dto.getUrl());
		Integer fourStar = dto.getFourStar();
		starOnlyCount = dto.getStarOnly();
		if (fourStar == null) {
			fourStar = 0;
		}
		if (starOnlyCount == null) {
			starOnlyCount = 0;
		}
		starRandomer = new WeightRandomer<Integer>();
		starRandomer.add(4, fourStar);
		starRandomer.add(5, 100 - fourStar);
		comments = Arrays.asList(parseComments(dto.getComments()));
		offset = dto.getOffset();
		if (offset == null) {
			offset = 0;
		}
		secondsDelay=dto.secondsDelay;
	}

	private static Pattern lineSpliter = Pattern.compile("[\r\n]+");
	private static Pattern cellSpliter = Pattern.compile("\t");

	private static String[][] parseComments(String comments) {
		String[] lines = lineSpliter.split(comments);
		String[][] rlt = new String[lines.length][];
		for (int i = 0; i < lines.length; i++) {
			rlt[i] = cellSpliter.split(lines[i]);
		}
		return rlt;
	}

	public static class NewCommentAppConfig implements Serializable {
		private static final long serialVersionUID = -2758996234131661L;
		@PropertyDescription("app的url")
		private String url;
		@PropertyDescription("评论的标题和内容（在excel中编辑，再粘贴进来）")
		private String comments;
		@PropertyDescription("只评星不做评论的数量")
		private Integer starOnly;
		@PropertyDescription("四星的比例")
		private Integer fourStar;
		@PropertyDescription("跳过多少个帐号")
		private Integer offset;
		@PropertyDescription("每次回复间隔（秒）")
		private Integer secondsDelay;

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public String getComments() {
			return comments;
		}

		public void setComments(String comments) {
			this.comments = comments;
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

		public Integer getOffset() {
			return offset;
		}

		public void setOffset(Integer offset) {
			this.offset = offset;
		}

		public Integer getSecondsDelay() {
			return secondsDelay;
		}

		public void setSecondsDelay(Integer secondsDelay) {
			this.secondsDelay = secondsDelay;
		}
	}
}
