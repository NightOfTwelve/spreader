package com.nali.spreader.analyzer;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nali.spreader.config.ContentDto;
import com.nali.spreader.config.Range;
import com.nali.spreader.config.RobotForwardDto;
import com.nali.spreader.config.UserDto;
import com.nali.spreader.config.UserRelatedDto;
import com.nali.spreader.constants.Website;
import com.nali.spreader.data.Content;
import com.nali.spreader.data.KeyValue;
import com.nali.spreader.factory.TaskProduceLine;
import com.nali.spreader.factory.config.Configable;
import com.nali.spreader.factory.config.desc.ClassDescription;
import com.nali.spreader.factory.passive.AutowireProductLine;
import com.nali.spreader.factory.regular.RegularAnalyzer;
import com.nali.spreader.service.IContentService;
import com.nali.spreader.service.IUserService;
import com.nali.spreader.service.IUserServiceFactory;
import com.nali.spreader.util.random.RandomUtil;

@Component
@ClassDescription("机器人转发")
public class RobotForward implements RegularAnalyzer,Configable<RobotForwardDto> {//TODO
	private Integer websiteId = Website.weibo.getId();
	private IUserService userService;
	private RobotForwardDto dto;
	@Autowired
	private IContentService contentService;
	@AutowireProductLine
	private TaskProduceLine<KeyValue<Long, Long>> forwardWeiboContent;
	
	@Autowired
	public void initUserService(IUserServiceFactory userServiceFactory) {
		userService = userServiceFactory.getUserService(websiteId);
	}

	@Override
	public String work() {
		UserDto userDto = UserDto.genUserDtoFrom(dto);
		userDto.setCategories(Arrays.asList(dto.getCategory()));
		userDto.setIsRobot(true);
		userDto.setWebsiteId(websiteId);
		List<KeyValue<Long, Long>> uidToWebsiteUidMaps = userService.findUidToWebsiteUidMapByDto(userDto);
		for (KeyValue<Long, Long> uidToWebsiteUidMap : uidToWebsiteUidMaps) {
			Long uid = uidToWebsiteUidMap.getKey();
			recommendContentToUid(uid);
		}
		return null;
	}

	protected void recommendContentToUid(Long uid) {
		ContentDto contentDto = new ContentDto();
		contentDto.setType(Content.TYPE_WEIBO);
		if(dto.getExpiredSeconds()!=null) {
			long millis = TimeUnit.MINUTES.toMillis(dto.getExpiredSeconds());
			Range<Date> pubDate = new Range<Date>();
			pubDate.setGte(new Date(System.currentTimeMillis()-millis));
			contentDto.setPubDate(pubDate);
		}
		if(dto.getMinLength()!=null) {
			Range<Integer> contentLength = new Range<Integer>();
			contentLength.setGte(dto.getMinLength());
			contentDto.setContentLength(contentLength);
		}
		contentDto.setRefCount(dto.getReplyCount());
		contentDto.setReplyCount(dto.getRefCount());
		
		UserRelatedDto userRelated = new UserRelatedDto();
		userRelated.setFansUid(uid);
		contentDto.setUserRelated(userRelated);
		
		List<Long> contentIds = contentService.findContentIdByDto(contentDto);
		Set<Long> postContentIds = contentService.getPostContentIds(uid);
		List<Long> randomIds = RandomUtil.randomItemsShuffle(contentIds, postContentIds, dto.getCount());
		for (Long contentId : randomIds) {
			forwardWeiboContent.send(new KeyValue<Long, Long>(uid, contentId));
		}
	}

	@Override
	public void init(RobotForwardDto dto) {
		if(dto.getCategory()==null) {
			throw new IllegalArgumentException("category must be set");
		}
		if(dto.getCount()==null) {
			throw new IllegalArgumentException("count must be set");
		}
		this.dto = dto;
	}

}
