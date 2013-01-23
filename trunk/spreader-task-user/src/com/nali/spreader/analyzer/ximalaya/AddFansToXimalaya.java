package com.nali.spreader.analyzer.ximalaya;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nali.spreader.constants.Website;
import com.nali.spreader.data.User;
import com.nali.spreader.data.UserRelation;
import com.nali.spreader.factory.TaskProduceLine;
import com.nali.spreader.factory.config.Configable;
import com.nali.spreader.factory.config.desc.ClassDescription;
import com.nali.spreader.factory.config.desc.PropertyDescription;
import com.nali.spreader.factory.passive.AutowireProductLine;
import com.nali.spreader.factory.regular.RegularAnalyzer;
import com.nali.spreader.group.config.UserGroupExtendedBeanImpl;
import com.nali.spreader.service.IGlobalUserService;
import com.nali.spreader.service.IUserGroupFacadeService;
import com.nali.spreader.util.random.RandomUtil;
import com.nali.spreader.workshop.other.AddUserFans;
import com.nali.spreader.workshop.ximalaya.AddXimalayaFans.AddXimalayaWorkDto;

@Component
@ClassDescription("喜马拉雅·关注用户(加粉)")
public class AddFansToXimalaya extends UserGroupExtendedBeanImpl implements
		RegularAnalyzer, Configable<AddFansToXimalaya.AddFansToXimalayaConfig> {
	private static final Logger logger = Logger
			.getLogger(AddFansToXimalaya.class);
	private Integer addLimit;
	private Integer execuAddLimit;
	private Long attentionLimit;
	private Long fansLimit;
	private Integer execuInterval;
	@Autowired
	private IGlobalUserService globalUserService;
	@Autowired
	private IUserGroupFacadeService userGroupFacadeService;
	@AutowireProductLine
	private TaskProduceLine<AddXimalayaWorkDto> addXimalayaFans;

	public AddFansToXimalaya() {
		super("分组${fromGroup}关注${toGroup}");
	}

	@Override
	public String work() {
		Long fromGid = getFromUserGroup();
		Long toGid = getToUserGroup();
		List<Long> fromUids = globalUserService.getAttenLimitUids(
				Website.ximalaya.getId(),
				userGroupFacadeService.getUids(fromGid), attentionLimit);
		List<Long> toUids = globalUserService.getFansLimitUids(
				Website.ximalaya.getId(),
				userGroupFacadeService.getUids(toGid), fansLimit);
		List<Long> alreadyExecuUsers = Collections
				.synchronizedList(new ArrayList<Long>());
		Date addTime = new Date();
		ConcurrentHashMap<Long, Long> attentMap = new ConcurrentHashMap<Long, Long>();
		for (Long toUid : toUids) {
			User toUser = globalUserService.getUserById(toUid);
			long fans = toUser.getFans();
			// 获取toUid已有的粉丝
			List<Long> existsFans = globalUserService.findRelationUserId(toUid,
					UserRelation.TYPE_ATTENTION, Website.ximalaya.getId(),
					toUser.getIsRobot());
			List<Long> exceedFansUids = getExceedFansUids(alreadyExecuUsers,
					execuAddLimit.intValue());
			existsFans.addAll(exceedFansUids);
			// 粉丝要和机器人组做一次排重
			@SuppressWarnings("unchecked")
			List<Long> noExistsFans = (List<Long>) CollectionUtils.subtract(
					fromUids, existsFans);
			List<Long> addFans = RandomUtil.randomItems(noExistsFans, addLimit);
			for (Long fromUid : addFans) {
				if (fromUid.equals(toUid)) {
					continue;
				}
				User fromUser = globalUserService.getUserById(fromUid);
				Long userAttent = fromUser.getAttentions();
				Long attentions = attentMap.putIfAbsent(fromUid, userAttent);
				if (attentions == null) {
					attentions = userAttent;
				}
				int execuCount = CollectionUtils.cardinality(fromUid,
						alreadyExecuUsers);
				if (attentions >= attentionLimit) {
					continue;
				}
				if (execuCount + 1 <= execuAddLimit.intValue()) {
					if (fansLimit == null ? true : fansLimit > fans) {
						addFans(fromUid, toUid, addTime);
						logger.debug("fromUid:" + fromUid + ",toUid:" + toUid);
						addTime = DateUtils.addMinutes(addTime, execuInterval);
						alreadyExecuUsers.add(fromUid);
						attentMap.replace(fromUid, ++attentions);
						fans++;
					}
				}
			}
		}
		return null;
	}

	private List<Long> getExceedFansUids(List<Long> alreadyExecuUsers, int limit) {
		@SuppressWarnings("unchecked")
		Map<Long, Integer> map = Collections.synchronizedMap(CollectionUtils
				.getCardinalityMap(alreadyExecuUsers));
		List<Long> execeedList = new ArrayList<Long>();
		for (Map.Entry<Long, Integer> entry : map.entrySet()) {
			Long id = entry.getKey();
			Integer count = entry.getValue();
			if (limit > count.intValue()) {
				execeedList.add(id);
			}
		}
		return execeedList;
	}

	/**
	 * 机器人执行加粉的操作
	 * 
	 * @param robotId
	 * @param uid
	 */
	private void addFans(Long fromUid, Long toUid, Date addStartTime) {
		AddXimalayaWorkDto dto = new AddXimalayaWorkDto();
		dto.setFromUid(fromUid);
		dto.setToUid(toUid);
		dto.setStartTime(addStartTime);
		addXimalayaFans.send(dto);
	}

	@Override
	public void init(AddFansToXimalayaConfig config) {
		fansLimit = config.getFansLimit();
		attentionLimit = config.getAttentionLimit();
		if (attentionLimit == null || attentionLimit <= 0) {
			attentionLimit = 100L;
		}
		execuInterval = config.getExecuInterval();
		if (execuInterval == null || execuInterval <= 0) {
			execuInterval = AddUserFans.AddFansDto.DEFAULT_ADD_FANS_INTERVAL;
		}
		addLimit = config.getAddCount();
		if (addLimit == null || addLimit <= 0) {
			addLimit = 1;
		}
		execuAddLimit = config.getExecuAddCount();
		if (execuAddLimit == null || execuAddLimit <= 0) {
			execuAddLimit = 1;
		}
	}

	public static class AddFansToXimalayaConfig implements Serializable {
		private static final long serialVersionUID = 8861711636679339664L;
		@PropertyDescription("机器人执行关注的间隔时间(分钟，默认3分钟)")
		private Integer execuInterval;
		@PropertyDescription("机器人已关注的次数上限(超过此上限该机器人不再分派任务，默认100)")
		private Long attentionLimit;
		@PropertyDescription("被关注人的粉丝数上限(超过此上限该用户不再添加粉丝,不填则不受限)")
		private Long fansLimit;
		@PropertyDescription("单个账户被加粉次数上限(单次，默认1)")
		private Integer addCount;
		@PropertyDescription("机器人执行关注的次数上限(单次策略，默认1)")
		private Integer execuAddCount;

		public Integer getExecuInterval() {
			return execuInterval;
		}

		public void setExecuInterval(Integer execuInterval) {
			this.execuInterval = execuInterval;
		}

		public Long getAttentionLimit() {
			return attentionLimit;
		}

		public void setAttentionLimit(Long attentionLimit) {
			this.attentionLimit = attentionLimit;
		}

		public Long getFansLimit() {
			return fansLimit;
		}

		public void setFansLimit(Long fansLimit) {
			this.fansLimit = fansLimit;
		}

		public Integer getAddCount() {
			return addCount;
		}

		public void setAddCount(Integer addCount) {
			this.addCount = addCount;
		}

		public Integer getExecuAddCount() {
			return execuAddCount;
		}

		public void setExecuAddCount(Integer execuAddCount) {
			this.execuAddCount = execuAddCount;
		}
	}
}
