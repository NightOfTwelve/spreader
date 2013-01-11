package com.nali.spreader.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import com.nali.spreader.config.BaseRobotGroupContent;
import com.nali.spreader.config.Range;
import com.nali.spreader.dto.PostWeiboContentDto;
import com.nali.spreader.service.IRobotGroupContentService;
import com.nali.spreader.util.random.RandomUtil;

@Service
public class RobotGroupContentServiceImpl implements IRobotGroupContentService {
	@Override
	public PostWeiboContentDto getPostContentParams(BaseRobotGroupContent config, Long uid) {
		PostWeiboContentDto dto = new PostWeiboContentDto();
		Long minute = config.getExpiredSeconds();
		// 时间
		if (minute != null) {
			long millisecond = TimeUnit.MINUTES.toMillis(minute);
			Range<Date> pubDate = new Range<Date>();
			pubDate.setGte(new Date(System.currentTimeMillis() - millisecond));
			dto.setPubDate(pubDate);
		}
		// 内容长度
		Integer contlen = config.getMinLength();
		if (contlen != null) {
			Range<Integer> lenRange = new Range<Integer>();
			lenRange.setGte(contlen);
			dto.setContentLength(lenRange);
		}
		// 转发数
		Range<Integer> refCount = config.getRefCount();
		dto.setRefCount(refCount);
		// 回复数
		Range<Integer> replyCount = config.getReplyCount();
		dto.setReplyCount(replyCount);
		// UID
		dto.setUids(new Long[] { uid });
		return dto;
	}

	@Override
	public List<Long> getRandomContent(List<Long> contentIds, Integer randomCount) {
		List<Long> randomList = new ArrayList<Long>();
		if (contentIds != null) {
			randomList = RandomUtil.randomItems(contentIds, randomCount);
		}
		return randomList;
	}
}
