package com.nali.spreader.workshop;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nali.common.util.CollectionUtils;
import com.nali.spreader.config.UserDto;
import com.nali.spreader.constants.Channel;
import com.nali.spreader.constants.Website;
import com.nali.spreader.data.Content;
import com.nali.spreader.data.KeyValue;
import com.nali.spreader.factory.SimpleActionConfig;
import com.nali.spreader.factory.config.Configable;
import com.nali.spreader.factory.config.desc.ClassDescription;
import com.nali.spreader.factory.exporter.SingleTaskComponentImpl;
import com.nali.spreader.factory.exporter.SingleTaskExporter;
import com.nali.spreader.factory.regular.SingleRegularTaskProducer;
import com.nali.spreader.service.IContentService;
import com.nali.spreader.service.IUserService;
import com.nali.spreader.service.IUserServiceFactory;
import com.nali.spreader.util.SpecialDateUtil;

@Component
@ClassDescription("发微博")
public class PostWeiboContent extends SingleTaskComponentImpl implements SingleRegularTaskProducer,Configable<UserDto> {
	private static Logger logger = Logger.getLogger(PostWeiboContent.class);
	private IUserService userService;
	private UserDto dto;
	@Autowired
	private IContentService contentService;

	public PostWeiboContent() {
		super(SimpleActionConfig.postWeiboContent, Website.weibo, Channel.normal);
	}
	
	@Autowired
	public void initUserService(IUserServiceFactory userServiceFactory) {
		userService = userServiceFactory.getUserService(websiteId);
	}

	@Override
	public void work(SingleTaskExporter exporter) {
		List<KeyValue<Long, Long>> uidToWebsiteUidMaps = userService.findUidToWebsiteUidMapByDto(dto);
		for (KeyValue<Long, Long> uidToWebsiteUidMap : uidToWebsiteUidMaps) {
			Long uid = uidToWebsiteUidMap.getKey();
//			Long websiteUid = uidToWebsiteUidMap.getValue();
			Map<String, Object> content = getContent(uid);
			if(content!=null) {
				exporter.createTask(content, uid, SpecialDateUtil.afterToday(3));
			}
		}
	}

	private Map<String, Object> getContent(Long uid) {
		Content matchedContent = contentService.getMatchedContent(uid);
		if(matchedContent==null) {
			logger.warn("not matchedContent, uid:" + uid);
			return null;
		}
		Map<String,Object> content = CollectionUtils.newHashMap(2);
		content.put("id", uid);
		content.put("content", matchedContent.getContent());
		return content;
	}

	@Override
	public void init(UserDto dto) {
		this.dto = dto;
		dto.setIsRobot(true);
	}

}
