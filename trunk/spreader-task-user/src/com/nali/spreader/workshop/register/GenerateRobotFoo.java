package com.nali.spreader.workshop.register;

import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nali.common.model.Limit;
import com.nali.common.pagination.PageResult;
import com.nali.spreader.config.NamedGroupUserAddFansDto;
import com.nali.spreader.constants.Website;
import com.nali.spreader.data.KeyValue;
import com.nali.spreader.data.User;
import com.nali.spreader.data.UserGroup;
import com.nali.spreader.factory.TaskProduceLine;
import com.nali.spreader.factory.config.Configable;
import com.nali.spreader.factory.config.desc.ClassDescription;
import com.nali.spreader.factory.passive.AutowireProductLine;
import com.nali.spreader.factory.passive.PassiveAnalyzer;
import com.nali.spreader.group.meta.UserGroupType;
import com.nali.spreader.group.service.IUserGroupService;
import com.nali.spreader.model.ClientTask;
import com.nali.spreader.model.PriorityData;
import com.nali.spreader.service.IUserGroupFacadeService;
import com.nali.spreader.util.random.NumberRandomer;

@Component
@ClassDescription("注册后关注用户")
public class GenerateRobotFoo implements PassiveAnalyzer<Long>, Configable<NamedGroupUserAddFansDto> {
	private static final int basePriority = ClientTask.BASE_PRIORITY_MAX+1;
	@Autowired
	private IUserGroupService userGroupService;
	@Autowired
	private IUserGroupFacadeService userGroupFacadeService;
	@AutowireProductLine
	private TaskProduceLine<PriorityData<KeyValue<Long, Long>>> addUserFans;
	private Long gid;
	private NumberRandomer limitRandom;

	@Override
	public void work(Long robotId) {
		if(gid==null) {
			throw new IllegalStateException("GenerateRobotFoo hasn't been configured");
		}
		Iterator<User> targetUsers = userGroupFacadeService.queryLimitedRandomGrouppedUser(gid, limitRandom.get());
		while (targetUsers.hasNext()) {
			User user = targetUsers.next();
			addUserFans.send(new PriorityData<KeyValue<Long,Long>>(basePriority, new KeyValue<Long,Long>(robotId, user.getId())));
		}
	}

	@Override
	public void init(NamedGroupUserAddFansDto config) {
		String gname = config.getGroupName();
		if(gname==null) {
			throw new IllegalArgumentException("missing group name");
		}
		PageResult<UserGroup> userGroups = userGroupService.queryUserGroups(Website.weibo, gname, UserGroupType.dynamic, 0, null, null, Limit.newInstanceForLimit(0, 1));
		if(userGroups.getList().size()==0) {
			throw new IllegalArgumentException("cannot find group:" + gname);
		}
		gid = userGroups.getList().get(0).getGid();
		Integer minValue = config.getMinUserValue();
		Integer maxValue = config.getMaxUserValue();
		if (minValue != null && maxValue != null) {
			limitRandom = new NumberRandomer(minValue, maxValue + 1);
		} else {
			throw new IllegalArgumentException("minValue or maxValue is null:[" + minValue + ", " + maxValue + "]");
		}
	}
}
