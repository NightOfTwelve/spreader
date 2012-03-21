package com.nali.spreader.service.impl;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nali.spreader.config.BaseRobotGroupContent;
import com.nali.spreader.config.ContentDto;
import com.nali.spreader.config.Range;
import com.nali.spreader.dao.IUserDao;
import com.nali.spreader.data.Content;
import com.nali.spreader.service.IRobotGroupContentService;
import com.nali.spreader.util.random.RandomUtil;

@Service
public class RobotGroupContentServiceImpl implements IRobotGroupContentService {
	@Autowired
	private IUserDao userDao;

	@Override
	public List<Long> findContentIds(BaseRobotGroupContent config, Long uid, Integer contentCount) {
		ContentDto contentDto = new ContentDto();
		contentDto.setType(Content.TYPE_WEIBO);
		Long minute = config.getExpiredSeconds();
		// 时间
		if (minute != null) {
			long millisecond = TimeUnit.MINUTES.toMillis(minute);
			Range<Date> pubDate = new Range<Date>();
			pubDate.setGte(new Date(System.currentTimeMillis() - millisecond));
			contentDto.setPubDate(pubDate);
		}
		// 内容长度
		Integer contlen = config.getMinLength();
		if (contlen != null) {
			Range<Integer> lenRange = new Range<Integer>();
			lenRange.setGte(contlen);
			contentDto.setContentLength(lenRange);
		}
		// 转发数
		Range<Integer> refCount = config.getRefCount();
		contentDto.setRefCount(refCount);
		// 回复数
		Range<Integer> replyCount = config.getReplyCount();
		contentDto.setReplyCount(replyCount);
		// UID
		contentDto.setUid(uid);
		List<Long> cidList = this.findContentIdByDto(contentDto);
		List<Long> randomList = RandomUtil.randomItems(cidList, contentCount);
		return randomList;
	}

	@Override
	public List<Long> findContentIdByDto(ContentDto dto) {
		return userDao.findContentIdByDto(dto);
	}
}
