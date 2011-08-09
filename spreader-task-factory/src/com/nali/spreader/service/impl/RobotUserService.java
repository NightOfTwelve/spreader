package com.nali.spreader.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.nali.common.model.Limit;
import com.nali.spreader.dao.ICrudRobotUserDao;
import com.nali.spreader.model.RobotUser;
import com.nali.spreader.model.RobotUserExample;
import com.nali.spreader.service.IRobotUserService;
import com.nali.spreader.service.WebsiteBaseService;

public class RobotUserService extends WebsiteBaseService implements IRobotUserService {
	@Autowired
	private ICrudRobotUserDao crudRobotUserDao;

	@Override
	public List<RobotUser> getUsers(int count) {
		RobotUserExample example = new RobotUserExample();
		example.createCriteria().andWebsiteIdEqualTo(getWebsiteId());
		Limit limit = Limit.newInstanceForLimit(0, count);//TODO 随机
		example.setLimit(limit);
		return crudRobotUserDao.selectByExample(example);
	}

	@Override
	public long getUserCount() {
		RobotUserExample example = new RobotUserExample();
		example.createCriteria().andWebsiteIdEqualTo(getWebsiteId());
		return crudRobotUserDao.countByExample(example);
	}

}
