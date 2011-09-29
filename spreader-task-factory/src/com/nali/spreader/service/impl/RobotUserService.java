package com.nali.spreader.service.impl;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.nali.common.model.Limit;
import com.nali.spreader.dao.ICrudRobotUserDao;
import com.nali.spreader.model.RobotUser;
import com.nali.spreader.model.RobotUserExample;
import com.nali.spreader.service.IRobotUserService;
import com.nali.spreader.service.WebsiteBaseService;
import com.nali.spreader.util.CachedIterator;

public class RobotUserService extends WebsiteBaseService implements IRobotUserService {
	@Autowired
	private ICrudRobotUserDao crudRobotUserDao;

	@Override
	public List<RobotUser> getUsers(int count) {
		return getUsers(0, count);//TODO random
	}
	
	private List<RobotUser> getUsers(int start, int count) {
		RobotUserExample example = new RobotUserExample();
		example.createCriteria().andWebsiteIdEqualTo(getWebsiteId())
				.andAccountStateEqualTo(RobotUser.ACCOUNT_STATE_NORMAL);
		Limit limit = Limit.newInstanceForLimit(start, count);
		example.setLimit(limit);
		return crudRobotUserDao.selectByExample(example);
	}

	@Override
	public long getUserCount() {
		RobotUserExample example = new RobotUserExample();
		example.createCriteria().andWebsiteIdEqualTo(getWebsiteId())
				.andAccountStateEqualTo(RobotUser.ACCOUNT_STATE_NORMAL);
		return crudRobotUserDao.countByExample(example);
	}

	@Override
	public Iterator<RobotUser> browseAllUser(boolean cycle) {
		final int batchSize=20;
		return new CachedIterator<RobotUser>(cycle, true) {
			@Override
			protected int getCount() {
				return (int) getUserCount();
			}
			@Override
			protected Iterable<RobotUser> query(int offset) {
				return getUsers(offset, batchSize);
			}
		};
	}
}
