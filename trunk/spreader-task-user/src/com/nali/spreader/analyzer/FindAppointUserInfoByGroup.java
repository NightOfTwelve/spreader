package com.nali.spreader.analyzer;

import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nali.log.MessageLogger;
import com.nali.log.impl.LoggerFactory;
import com.nali.spreader.config.UserGroupContentDto;
import com.nali.spreader.data.KeyValue;
import com.nali.spreader.factory.TaskProduceLine;
import com.nali.spreader.factory.config.Configable;
import com.nali.spreader.factory.config.desc.ClassDescription;
import com.nali.spreader.factory.passive.AutowireProductLine;
import com.nali.spreader.factory.regular.RegularAnalyzer;
import com.nali.spreader.group.config.UserGroupExtendedBeanImpl;
import com.nali.spreader.model.GrouppedUser;
import com.nali.spreader.service.IUserGroupFacadeService;

/**
 * 爬取指定用户分组的用户信息
 * 
 * @author xiefei
 * 
 */
@Component
@ClassDescription("分组·爬取指定用户分组的用户信息")
public class FindAppointUserInfoByGroup extends UserGroupExtendedBeanImpl implements RegularAnalyzer,
		Configable<UserGroupContentDto> {
	private static final MessageLogger logger = LoggerFactory.getLogger(FindAppointUserInfoByGroup.class);
	private Date lastFetchTime;
	@Autowired
	private IUserGroupFacadeService userGroupService;
	@AutowireProductLine
	private TaskProduceLine<KeyValue<Long, Date>> fetchWeiboUserMainPage;

	public FindAppointUserInfoByGroup() {
		super("爬取${fromGroup}的用户信息");
	}

	@Override
	public String work() {
		Long gid = this.getFromUserGroup();
		if (gid == null) {
			logger.error("爬去指定分组用户信息失败,分组ID为null");
			throw new IllegalArgumentException();
		} else {
			Iterator<GrouppedUser> iter = this.userGroupService.queryAllGrouppedUser(gid);
			while (iter.hasNext()) {
				GrouppedUser gu = iter.next();
				if (gu != null) {
					Long uid = gu.getUid();
					KeyValue<Long, Date> data = new KeyValue<Long, Date>();
					data.setKey(uid);
					data.setValue(lastFetchTime);
					fetchWeiboUserMainPage.send(data);
				}
			}
		}
		return null;
	}

	@Override
	public void init(UserGroupContentDto config) {
		Long minute = config.getLastFetchTime();
		Date time = null;
		if (minute != null) {
			long millisecond = TimeUnit.MINUTES.toMillis(minute);
			time = new Date(System.currentTimeMillis() - millisecond);
		} else {
			time = new Date();
		}
		this.lastFetchTime = time;
	}
}
