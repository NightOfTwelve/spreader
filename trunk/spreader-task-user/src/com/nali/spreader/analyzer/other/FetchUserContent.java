package com.nali.spreader.analyzer.other;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nali.spreader.config.ContentKeywordsConfig;
import com.nali.spreader.config.ConfigDataUtil;
import com.nali.spreader.constants.Website;
import com.nali.spreader.data.KeyValue;
import com.nali.spreader.dto.FetchUserWeiboDto;
import com.nali.spreader.factory.TaskProduceLine;
import com.nali.spreader.factory.config.Configable;
import com.nali.spreader.factory.config.desc.ClassDescription;
import com.nali.spreader.factory.passive.AutowireProductLine;
import com.nali.spreader.factory.regular.RegularAnalyzer;
import com.nali.spreader.service.IUserService;
import com.nali.spreader.service.IUserServiceFactory;
import com.nali.spreader.util.random.Randomer;

@Component
@ClassDescription("抓取微博内容")
public class FetchUserContent implements RegularAnalyzer, Configable<ContentKeywordsConfig> {
	private Integer websiteId = Website.weibo.getId();
	private IUserService userService;
	private ContentKeywordsConfig dto;
	private Randomer<Integer> keywordRandom;
	@AutowireProductLine
	private TaskProduceLine<FetchUserWeiboDto> fetchWeiboContent;

	@Autowired
	public void initUserService(IUserServiceFactory userServiceFactory) {
		userService = userServiceFactory.getUserService(websiteId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nali.spreader.factory.regular.RegularAnalyzer#work()
	 */
	@Override
	public String work() {
		List<KeyValue<Long, Long>> uidToWebsiteUidMaps = userService
				.findUidToWebsiteUidMapByDto(dto);
		List<String> keywords = ConfigDataUtil.getKeywords(dto.getKeywords(), dto.getRandomKeywords(), keywordRandom);
		for (KeyValue<Long, Long> uidToWebsiteUidMap : uidToWebsiteUidMaps) {
			Long uid = uidToWebsiteUidMap.getKey();
			FetchUserWeiboDto sendDto = new FetchUserWeiboDto();
			sendDto.setUid(uid);
			sendDto.setKeywords(keywords);
			fetchWeiboContent.send(sendDto);
		}
		return null;
	}

	@Override
	public void init(ContentKeywordsConfig dto) {
		this.dto = dto;
		dto.setIsRobot(false);
		this.keywordRandom = ConfigDataUtil.createGteLteRandomer(dto.getRandomKeywordsRange(), false);
	}
}
