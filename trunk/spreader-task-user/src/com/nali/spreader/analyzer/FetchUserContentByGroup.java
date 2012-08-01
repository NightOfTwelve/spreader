package com.nali.spreader.analyzer;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nali.log.MessageLogger;
import com.nali.log.impl.LoggerFactory;
import com.nali.spreader.KeywordUtil;
import com.nali.spreader.config.ContentKeywordsConfig;
import com.nali.spreader.config.UserGroupContentDto;
import com.nali.spreader.dto.FetchUserWeiboDto;
import com.nali.spreader.factory.TaskProduceLine;
import com.nali.spreader.factory.config.Configable;
import com.nali.spreader.factory.config.desc.ClassDescription;
import com.nali.spreader.factory.passive.AutowireProductLine;
import com.nali.spreader.factory.regular.RegularAnalyzer;
import com.nali.spreader.group.config.UserGroupExtendedBeanImpl;
import com.nali.spreader.group.service.IUserGroupService;
import com.nali.spreader.model.GrouppedUser;
import com.nali.spreader.service.IKeywordService;
import com.nali.spreader.util.DataIterator;
import com.nali.spreader.util.random.RandomUtil;
import com.nali.spreader.util.random.Randomer;

@Component
@ClassDescription("分组·用户内容爬取")
public class FetchUserContentByGroup extends UserGroupExtendedBeanImpl implements RegularAnalyzer,
		Configable<UserGroupContentDto> {
	private static final MessageLogger logger = LoggerFactory
			.getLogger(FetchUserContentByGroup.class);
	private Date lastFetchTime;
	@Autowired
	private IUserGroupService userGroupService;
	@Autowired
	private IKeywordService keywordService;
	@AutowireProductLine
	private TaskProduceLine<FetchUserWeiboDto> fetchWeiboContent;
	private Randomer<Integer> keywordRandom;
	private UserGroupContentDto config;

	public FetchUserContentByGroup() {
		super("爬取${fromGroup}的内容");
	}

	@Override
	public void init(UserGroupContentDto config) {
		this.config = config;
		Long minute = config.getLastFetchTime();
		Date time = null;
		if (minute != null) {
			long millisecond = TimeUnit.MINUTES.toMillis(minute);
			time = new Date(System.currentTimeMillis() - millisecond);
		} else {
			time = new Date();
		}
		this.lastFetchTime = time;
		this.keywordRandom = this.keywordService.createRandomer(config.getRandomKeywordsRange(),
				ContentKeywordsConfig.DEFAULT_RANDOM_GTE, ContentKeywordsConfig.DEFAULT_RANDOM_LTE);
	}

	@Override
	public String work() {
		Long gid = this.getFromUserGroup();
		if (gid != null) {
			DataIterator<GrouppedUser> data = this.userGroupService.queryGrouppedUserIterator(gid,
					100);
			List<String> randomList = RandomUtil.randomItems(config.getRandomKeywords(),
					this.keywordRandom.get());
			List<String> keywords = KeywordUtil.getKeywords(config.getKeywords(), config.getRandomKeywords(), config.getRandomKeywordsRange());
			if (data.hasNext()) {
				List<GrouppedUser> list = data.next();
				if (list.size() > 0) {
					for (GrouppedUser gu : list) {
						if (gu != null) {
							Long uid = gu.getUid();
							FetchUserWeiboDto sendDto = new FetchUserWeiboDto();
							sendDto.setUid(uid);
							sendDto.setKeywords(keywords);
							sendDto.setLastFetchTime(lastFetchTime);
							fetchWeiboContent.send(sendDto);
						}
					}
				}
			}
		} else {
			logger.error("爬取用户组ID为Null");
			throw new IllegalArgumentException();
		}
		return null;
	}
}
