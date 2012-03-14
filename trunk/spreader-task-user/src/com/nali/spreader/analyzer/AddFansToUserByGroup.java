package com.nali.spreader.analyzer;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nali.log.MessageLogger;
import com.nali.log.impl.LoggerFactory;
import com.nali.spreader.config.GroupUserAddFansDto;
import com.nali.spreader.data.KeyValue;
import com.nali.spreader.data.UserRelation;
import com.nali.spreader.factory.TaskProduceLine;
import com.nali.spreader.factory.config.Configable;
import com.nali.spreader.factory.config.desc.ClassDescription;
import com.nali.spreader.factory.passive.AutowireProductLine;
import com.nali.spreader.factory.regular.RegularAnalyzer;
import com.nali.spreader.group.config.UserGroupExtendedBeanImpl;
import com.nali.spreader.group.service.IUserGroupService;
import com.nali.spreader.model.GrouppedUser;
import com.nali.spreader.service.IGlobalUserService;
import com.nali.spreader.util.DataIterator;
import com.nali.spreader.util.random.NumberRandomer;

@Component
@ClassDescription("分组关注用户")
public class AddFansToUserByGroup extends UserGroupExtendedBeanImpl implements RegularAnalyzer,
		Configable<GroupUserAddFansDto> {
	private static final MessageLogger logger = LoggerFactory.getLogger(AddFansToUserByGroup.class);
	@SuppressWarnings("unused")
	private GroupUserAddFansDto config;
	@Autowired
	private IUserGroupService userGroupService;
	@Autowired
	private IGlobalUserService globalUserService;
	@AutowireProductLine
	private TaskProduceLine<KeyValue<Long, Long>> addUserFans;
	private final int BATCHSIZE = 100;

	private NumberRandomer random;

	public AddFansToUserByGroup() {
		super("${fromGroup}关注${toGroup}");
	}

	@Override
	public void init(GroupUserAddFansDto config) {
		this.config = config;
		Integer minValue = config.getMinUserValue();
		Integer maxValue = config.getMaxUserValue();
		if (minValue != null && maxValue != null) {
			this.random = new NumberRandomer(minValue, maxValue + 1);
		} else {
			logger.error("关注数上限为null");
			throw new NullPointerException();
		}
	}

	@Override
	public void work() {
		Long fromGroup = this.getFromUserGroup();
		Long toGroup = this.getToUserGroup();
		// Double robotRate = config.getRobotRate();
		DataIterator<GrouppedUser> toIterator = this.userGroupService.queryGrouppedUserIterator(toGroup,
				BATCHSIZE);
		while (toIterator.hasNext()) {
			List<GrouppedUser> toidList = toIterator.next();
			for (GrouppedUser togu : toidList) {
				int randomLimit = random.get();
				DataIterator<GrouppedUser> fromIterator = this.userGroupService.queryGrouppedUserIterator(
						fromGroup, BATCHSIZE, randomLimit);
				Long toUid = togu.getUid();
				List<Long> relationList = this.globalUserService.findRelationUserId(toUid,
						UserRelation.TYPE_ATTENTION, true);
				Set<Long> relationSet = new HashSet<Long>(relationList);
				while (fromIterator.hasNext()) {
					List<GrouppedUser> fromidList = fromIterator.next();
					for (GrouppedUser fromgu : fromidList) {
						Long fromUid = fromgu.getUid();
						if (!relationSet.contains(fromUid)) {
							addUserFans.send(new KeyValue<Long, Long>(fromUid, toUid));
						}
					}
				}
			}
		}
	}

	@SuppressWarnings("unused")
	private Number nvl(Number n) {
		return n == null ? 0 : n;
	}
}
