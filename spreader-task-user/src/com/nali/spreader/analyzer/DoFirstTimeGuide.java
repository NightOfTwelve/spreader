package com.nali.spreader.analyzer;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nali.spreader.data.KeyValue;
import com.nali.spreader.factory.TaskProduceLine;
import com.nali.spreader.factory.config.Configable;
import com.nali.spreader.factory.config.desc.ClassDescription;
import com.nali.spreader.factory.passive.AutowireProductLine;
import com.nali.spreader.factory.regular.RegularAnalyzer;
import com.nali.spreader.group.config.UserGroupExtendedBeanImpl;
import com.nali.spreader.model.GrouppedUser;
import com.nali.spreader.service.IFirstLoginGuideService;
import com.nali.spreader.service.IUserGroupFacadeService;

@Component
@ClassDescription("用户引导补跑")
public class DoFirstTimeGuide extends UserGroupExtendedBeanImpl implements RegularAnalyzer, Configable<Integer> {
	@AutowireProductLine
	private TaskProduceLine<KeyValue<Long, Date>> firstTimeGuide;
	@Autowired
	private IUserGroupFacadeService userGroupFacadeService;
	@Autowired
	private IFirstLoginGuideService firstLoginGuideService;
	private int timeIntervalInMills = 1000*120;
	
	public DoFirstTimeGuide() {
		super("${fromGroup}重新执行新用户引导");
	}

	@Override
	public void init(Integer config) {
		timeIntervalInMills=config;
	}

	@Override
	public String work() {
		Iterator<GrouppedUser> userIterator = this.userGroupFacadeService.queryAllGrouppedUser(getFromUserGroup());
		int count=0;
		Calendar calendar = Calendar.getInstance();
		while (userIterator.hasNext()) {
			GrouppedUser grouppedUser = (GrouppedUser) userIterator.next();
			Long uid = grouppedUser.getUid();
			boolean userGuide = firstLoginGuideService.isUserGuide(uid);
			if(userGuide) {
				continue;
			}
			Date date = calendar.getTime();
			calendar.add(Calendar.MILLISECOND, timeIntervalInMills);
			KeyValue<Long, Date> uidDate = new KeyValue<Long, Date>(uid, date);
			firstTimeGuide.send(uidDate);
		}
		return "重新执行新用户引导:" + count;
	}

}
