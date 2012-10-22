package com.nali.spreader.analyzer.keyword;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nali.common.util.CollectionUtils;
import com.nali.spreader.analyzer.other.Words;
import com.nali.spreader.config.KeywordReplyAndAddConfig;
import com.nali.spreader.dto.KeywordUserDto;
import com.nali.spreader.dto.UserContentsDto;
import com.nali.spreader.factory.TaskProduceLine;
import com.nali.spreader.factory.config.Configable;
import com.nali.spreader.factory.config.desc.ClassDescription;
import com.nali.spreader.factory.passive.AutowireProductLine;
import com.nali.spreader.factory.regular.RegularAnalyzer;
import com.nali.spreader.group.config.UserGroupExtendedBeanImpl;
import com.nali.spreader.model.GrouppedUser;
import com.nali.spreader.model.ReplyDto;
import com.nali.spreader.service.IContentService;
import com.nali.spreader.service.IGlobalUserService;
import com.nali.spreader.service.IKeywordService;
import com.nali.spreader.service.IUserGroupFacadeService;
import com.nali.spreader.util.avg.Average;
import com.nali.spreader.util.avg.AverageHelper;
import com.nali.spreader.util.avg.ItemCount;
import com.nali.spreader.util.random.AvgRandomer;
import com.nali.spreader.util.random.RandomUtil;
import com.nali.spreader.util.random.Randomer;
import com.nali.spreader.workshop.other.AddUserFans;

@Component
@ClassDescription("关键字·关注并回复符合条件的微博用户")
public class AddFansAndReplyByKeyword extends UserGroupExtendedBeanImpl implements RegularAnalyzer,
		Configable<KeywordReplyAndAddConfig> {
	private KeywordReplyAndAddConfig config;
	private Integer addLimit;
	private Integer execuAddLimit;
	private Set<Long> keywords;
	private List<String> categories;
	private Long attentionLimit;
	private Boolean needReply;
	private Integer execuInterval;
	private Randomer<String> replyWords;
	@Autowired
	private IGlobalUserService globalUserService;
	@Autowired
	private IContentService contentService;
	@Autowired
	private IKeywordService keywordService;
	@Autowired
	private IUserGroupFacadeService userGroupFacadeService;
	@AutowireProductLine
	private TaskProduceLine<AddUserFans.AddFansDto> addUserFans;
	@AutowireProductLine
	private TaskProduceLine<ReplyDto> replyWeibo;

	public AddFansAndReplyByKeyword() {
		super("分组${fromGroup}的机器人关注并回复符合条件的微博用户");
	}

	@Override
	public String work() {
		Long gid = getFromUserGroup();
		Iterator<GrouppedUser> iter = userGroupFacadeService.queryAllGrouppedUser(gid);
		List<Long> robotIdList = getUidsByIterator(iter);
		if (!CollectionUtils.isEmpty(keywords)) {
			this.execue(keywords, robotIdList);
		} else {
			// 查找分组用户本身的关键字
			List<Map<String, Long>> robotKeyword = this.keywordService
					.findUsersKeyword(robotIdList);
			List<KeywordUserDto> data = this.getKeywordUserDtoData(robotKeyword);
			for (KeywordUserDto dto : data) {
				Long keyword = dto.getKeyword();
				List<Long> robots = dto.getUsers();
				Set<Long> set = new HashSet<Long>();
				set.add(keyword);
				this.execue(set, robots);
			}
		}
		return null;
	}

	/**
	 * 机器人执行加粉与回复微博的操作
	 * 
	 * @param keywords
	 * @param robotIdList
	 */
	private void execue(Set<Long> keywords, List<Long> robots) {
		Long[] opArray = new Long[keywords.size()];
		Long[] keywordArray = keywords.toArray(opArray);
		List<Map<String, Long>> allContent = this.contentService
				.findContentByPostContentDto(this.config.getPostWeiboContentDto(keywordArray));
		List<UserContentsDto> readyData = this.getUserContentsDtoData(allContent);
		if (!CollectionUtils.isEmpty(readyData)) {
			Integer userCount = readyData.size();
			List<ItemCount<Long>> execuData = AverageHelper.getItemCounts(this.execuAddLimit,
					robots);
			Map<String, Integer> execuParams = CollectionUtils.newHashMap(4);
			execuParams.put(AverageHelper.KEY_USER_NUMBER, userCount);
			execuParams.put(AverageHelper.KEY_ROBOT_USER_NUMBER, robots.size());
			execuParams.put(AverageHelper.KEY_ADD_FANS_LIMIT, this.addLimit);
			execuParams.put(AverageHelper.KEY_EXECU_ADD_FANS_LIMIT, this.execuAddLimit);
			Average<Long> avg = AverageHelper.selectAverageByParam(execuParams, execuData);
			Date addTime = new Date();
			Set<UserContentsDto> ucExists = new HashSet<UserContentsDto>();
			while (avg.hasNext()) {
				List<ItemCount<Long>> items = avg.next();
				List<UserContentsDto> workData = RandomUtil.randomItems(readyData, ucExists, 1);
				if (!CollectionUtils.isEmpty(workData)) {
					UserContentsDto uc = workData.get(0);
					Long uid = uc.getUid();
					List<Long> contents = uc.getContents();
					Map<Long, Set<Long>> existsReyply = new HashMap<Long, Set<Long>>();
					Map<Long, Set<Long>> existsAdd = new HashMap<Long, Set<Long>>();
					for (ItemCount<Long> item : items) {
						Long robotId = item.getItem();
						int count = item.getCount();
						Long contentId = RandomUtil.randomItem(contents);
						if (count > 0) {
							existsAdd = addFans(uid, robotId, existsAdd, addTime);
							if (needReply) {
								existsReyply = replyWeibo(robotId, contentId, existsReyply, addTime);
							}
							addTime = DateUtils.addMinutes(addTime, execuInterval);
						}
					}
					ucExists.add(uc);
				}
			}
		}
	}

	/**
	 * 判断是否需要生成任务
	 * 
	 * @param robotId
	 * @param contentId
	 * @param existsMap
	 * @return
	 */
	private boolean isExists(Long toId, Long execuId, Map<Long, Set<Long>> existsMap) {
		if (!existsMap.containsKey(toId)) {
			return false;
		} else {
			Set<Long> set = existsMap.get(toId);
			if (set == null) {
				return false;
			}
			return set.contains(execuId);
		}
	}

	/**
	 * 检查是否已经回复了该微博，没有就执行回复并记录
	 * 
	 * @param robotId
	 * @param contentId
	 * @param exists
	 * @return
	 */
	private Map<Long, Set<Long>> replyWeibo(Long robotId, Long contentId,
			Map<Long, Set<Long>> exists, Date startTime) {
		if (!isExists(robotId, contentId, exists)) {
			replyWeibo(robotId, contentId, replyWords.get(), startTime);
			Set<Long> set = exists.get(robotId);
			if (set == null) {
				set = new HashSet<Long>();
			}
			set.add(contentId);
			exists.put(robotId, set);
		}
		return exists;
	}

	/**
	 * 执行回复的基本操作，不判断是否已经回复该微博
	 * 
	 * @param robotId
	 * @param contentId
	 * @param replyText
	 */
	private void replyWeibo(Long robotId, Long contentId, String replyText, Date startTime) {
		ReplyDto dto = new ReplyDto();
		dto.setContentId(contentId);
		dto.setRobotId(robotId);
		dto.setForward(false);
		dto.setText(replyText);
		dto.setStartTime(startTime);
		replyWeibo.send(dto);
	}

	/**
	 * 记录已经生成该机器人是否已经关注了该用户的任务
	 * 
	 * @param uid
	 * @param robotId
	 * @param exists
	 * @param startTime
	 * @return
	 */
	private Map<Long, Set<Long>> addFans(Long uid, Long robotId, Map<Long, Set<Long>> exists,
			Date startTime) {
		if (!isExists(uid, robotId, exists)) {
			addFans(uid, robotId, startTime);
			Set<Long> set = exists.get(uid);
			if (set == null) {
				set = new HashSet<Long>();
			}
			set.add(robotId);
			exists.put(uid, set);
		}
		return exists;
	}

	/**
	 * 机器人执行加粉的操作
	 * 
	 * @param robotId
	 * @param uid
	 */
	private void addFans(Long uid, Long robotId, Date addStartTime) {
		AddUserFans.AddFansDto dto = new AddUserFans.AddFansDto();
		dto.setRobotUid(robotId);
		dto.setUid(uid);
		dto.setStartTime(addStartTime);
		addUserFans.send(dto);
	}

	@Override
	public void init(KeywordReplyAndAddConfig config) {
		this.config = config;
		List<String> configKeywords = this.config.getKeywords();
		categories = this.config.getCategories();
		keywords = this.keywordService.createMergerKeyword(configKeywords, categories);
		execuInterval = config.getExecuInterval();
		attentionLimit = config.getAttentionLimit();
		if (execuInterval == null || execuInterval <= 0) {
			execuInterval = AddUserFans.AddFansDto.DEFAULT_ADD_FANS_INTERVAL;
		}
		needReply = config.getNeedReply();
		// 默认需要回复
		if (needReply == null) {
			needReply = true;
		}
		if (CollectionUtils.isEmpty(config.getWords())) {
			replyWords = Words.defaultReplyWords;
		} else {
			replyWords = new AvgRandomer<String>(config.getWords());
		}
		addLimit = this.config.getAddCount();
		if (addLimit == null) {
			addLimit = KeywordReplyAndAddConfig.DEFAULT_ADD_LIMIT;
		}
		execuAddLimit = this.config.getExecuAddCount();
		if (execuAddLimit == null) {
			execuAddLimit = KeywordReplyAndAddConfig.DEFAULT_EXECU_ADD_LIMIT;
		}
	}

	/**
	 * 获取用户分组中所有的用户ID
	 * 
	 * @param iter
	 * @return
	 */
	private List<Long> getUidsByIterator(Iterator<GrouppedUser> iter) {
		List<Long> list = new ArrayList<Long>();
		while (iter.hasNext()) {
			GrouppedUser gu = iter.next();
			list.add(gu.getUid());
		}
		List<Long> resutl = this.globalUserService.getAttenLimitUids(list, attentionLimit);
		return resutl;
	}

	/**
	 * 用户与微博的分组数据
	 * 
	 * @param data
	 * @return
	 */
	private List<UserContentsDto> getUserContentsDtoData(List<Map<String, Long>> data) {
		List<UserContentsDto> result = new ArrayList<UserContentsDto>();
		Map<Long, Set<Long>> map = getTransformData(data, "uid", "contentId");
		if (map == null) {
			return result;
		}
		if (map.isEmpty()) {
			return result;
		}
		for (Map.Entry<Long, Set<Long>> m : map.entrySet()) {
			Long uid = m.getKey();
			Set<Long> contentSet = m.getValue();
			List<Long> cidList = new ArrayList<Long>(contentSet);
			UserContentsDto dto = new UserContentsDto();
			dto.setUid(uid);
			dto.setContents(cidList);
			result.add(dto);
		}
		return result;
	}

	/**
	 * 关键字与用户的分组数据
	 * 
	 * @param data
	 * @return
	 */
	private List<KeywordUserDto> getKeywordUserDtoData(List<Map<String, Long>> data) {
		List<KeywordUserDto> result = new ArrayList<KeywordUserDto>();
		Map<Long, Set<Long>> map = getTransformData(data, "keyword", "users");
		if (map == null) {
			return result;
		}
		if (map.isEmpty()) {
			return result;
		}
		for (Map.Entry<Long, Set<Long>> m : map.entrySet()) {
			Long keyword = m.getKey();
			Set<Long> set = m.getValue();
			List<Long> list = new ArrayList<Long>(set);
			KeywordUserDto dto = new KeywordUserDto();
			dto.setKeyword(keyword);
			dto.setUsers(list);
			result.add(dto);
		}
		return result;
	}

	/**
	 * 构建分组数据前的准备数据
	 * 
	 * @param data
	 * @param groupKey
	 * @param itemKey
	 * @return
	 */
	private Map<Long, Set<Long>> getTransformData(List<Map<String, Long>> data, String groupKey,
			String itemKey) {
		Map<Long, Set<Long>> readyMap = new HashMap<Long, Set<Long>>();
		if (CollectionUtils.isEmpty(data)) {
			return readyMap;
		}
		Set<Long> contentSet = new HashSet<Long>();
		for (int i = 0; i < data.size(); i++) {
			Map<String, Long> map = data.get(i);
			Long groupKeyId = map.get(groupKey);
			Long itemKeyId = map.get(itemKey);
			if (!readyMap.containsKey(groupKeyId)) {
				contentSet = new HashSet<Long>();
			}
			contentSet.add(itemKeyId);
			readyMap.put(groupKeyId, contentSet);
		}
		return readyMap;
	}
}
