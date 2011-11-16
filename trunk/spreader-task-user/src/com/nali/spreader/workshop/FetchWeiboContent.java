package com.nali.spreader.workshop;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nali.common.util.CollectionUtils;
import com.nali.spreader.config.UserDto;
import com.nali.spreader.constants.Channel;
import com.nali.spreader.constants.Website;
import com.nali.spreader.data.KeyValue;
import com.nali.spreader.factory.SimpleActionConfig;
import com.nali.spreader.factory.config.Configable;
import com.nali.spreader.factory.config.desc.ClassDescription;
import com.nali.spreader.factory.exporter.SingleTaskComponentImpl;
import com.nali.spreader.factory.exporter.TaskExporter;
import com.nali.spreader.factory.regular.RegularTaskProducer;
import com.nali.spreader.service.IContentService;
import com.nali.spreader.service.IUserService;
import com.nali.spreader.service.IUserServiceFactory;
import com.nali.spreader.util.SpecialDateUtil;

@Component
@ClassDescription("抓取微博内容")
public class FetchWeiboContent extends SingleTaskComponentImpl implements RegularTaskProducer,Configable<UserDto> {
	private IUserService userService;
	private UserDto dto;
	@Autowired
	private IContentService contentService;
	@Autowired
	private WeiboRobotUserHolder robotUserHolder;

	public FetchWeiboContent() {
		super(SimpleActionConfig.fetchWeiboContent, Website.weibo, Channel.normal);
	}
	
	@Autowired
	public void initUserService(IUserServiceFactory userServiceFactory) {
		userService = userServiceFactory.getUserService(websiteId);
	}

	@Override
	public void work(TaskExporter exporter) {
		List<KeyValue<Long, Long>> uidToWebsiteUidMaps = userService.findUidToWebsiteUidMapByDto(dto);
		for (KeyValue<Long, Long> uidToWebsiteUidMap : uidToWebsiteUidMaps) {
			Long uid = uidToWebsiteUidMap.getKey();
			Long websiteUid = uidToWebsiteUidMap.getValue();
			Map<String, Object> content = getContent(uid, websiteUid);
			if(content!=null) {
				exporter.createTask(content, robotUserHolder.getRobotUid(), SpecialDateUtil.afterToday(2));//TODO 不分配具体的人
			}
		}
	}

	private Map<String, Object> getContent(Long uid, Long websiteUid) {
		Date lastFetchTime= contentService.getAndTouchLastFetchTime(uid);
		Map<String,Object> content = CollectionUtils.newHashMap(2);
		content.put("websiteUid", websiteUid);
		content.put("lastFetchTime", lastFetchTime);
		return content;
	}

	@Override
	public void init(UserDto dto) {
		this.dto = dto;
		dto.setIsRobot(false);
	}

}
