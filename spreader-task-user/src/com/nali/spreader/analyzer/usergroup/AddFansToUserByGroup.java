package com.nali.spreader.analyzer.usergroup;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nali.log.MessageLogger;
import com.nali.log.impl.LoggerFactory;
import com.nali.spreader.config.GroupUserAddFansDto;
import com.nali.spreader.data.KeyValue;
import com.nali.spreader.data.User;
import com.nali.spreader.data.UserRelation;
import com.nali.spreader.factory.TaskProduceLine;
import com.nali.spreader.factory.config.Configable;
import com.nali.spreader.factory.config.desc.ClassDescription;
import com.nali.spreader.factory.passive.AutowireProductLine;
import com.nali.spreader.factory.regular.RegularAnalyzer;
import com.nali.spreader.group.config.UserGroupExtendedBeanImpl;
import com.nali.spreader.model.GrouppedUser;
import com.nali.spreader.service.IGlobalUserService;
import com.nali.spreader.service.IUserGroupFacadeService;
import com.nali.spreader.util.random.NumberRandomer;

@Component
@ClassDescription("分组·关注用户")
public class AddFansToUserByGroup extends UserGroupExtendedBeanImpl implements RegularAnalyzer,
		Configable<GroupUserAddFansDto> {
	private static final MessageLogger logger = LoggerFactory.getLogger(AddFansToUserByGroup.class);
	@Autowired
	private IUserGroupFacadeService userGroupFacadeService;
	@Autowired
	private IGlobalUserService globalUserService;
	@AutowireProductLine
	private TaskProduceLine<KeyValue<Long, Long>> addUserFans;

	private NumberRandomer random;

	public AddFansToUserByGroup() {
		super("${fromGroup}关注${toGroup}");
	}

	@Override
	public void init(GroupUserAddFansDto config) {
		Integer minValue = config.getMinUserValue();
		Integer maxValue = config.getMaxUserValue();
		// 下限可以为0
		if (minValue == null) {
			minValue = 0;
		}
		if (maxValue != null) {
			this.random = new NumberRandomer(minValue, maxValue + 1);
		} else {
			logger.error("关注数上限为null");
			throw new IllegalArgumentException();
		}
	}

	@Override
	public String work() {
		// 粉丝组ID
		Long fromGroup = this.getFromUserGroup();
		// 关注用户组ID
		Long toGroup = this.getToUserGroup();
		// 获取所有关注用户
		Iterator<GrouppedUser> toIterator = this.userGroupFacadeService.queryAllGrouppedUser(toGroup);
		while (toIterator.hasNext()) {
			GrouppedUser togu = toIterator.next();
			int randomLimit = random.get();
			Long toUid = togu.getUid();
			// 获取所有的粉丝
			List<Long> relationList = this.globalUserService.findRelationUserId(toUid,
					UserRelation.TYPE_ATTENTION, true);
			// 排重
			Set<Long> relationSet = new HashSet<Long>(relationList);
			// 随机获取粉丝组成员
			Iterator<User> fromIterator = this.userGroupFacadeService.queryLimitedRandomGrouppedUser(
					fromGroup, randomLimit, relationSet);
			while (fromIterator.hasNext()) {
				User fromUser = fromIterator.next();
				Long fromUid = fromUser.getId();
				if (!relationSet.contains(fromUid)) {
					addUserFans.send(new KeyValue<Long, Long>(fromUid, toUid));
				}
			}
		}
		return null;
	}
}
