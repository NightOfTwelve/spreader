package com.nali.spreader.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nali.spreader.dao.ICrudRobotUserDao;
import com.nali.spreader.dao.ICrudUserDao;
import com.nali.spreader.dao.IUserDao;
import com.nali.spreader.data.User;
import com.nali.spreader.data.UserExample;
import com.nali.spreader.model.RobotUser;
import com.nali.spreader.service.IGlobalUserService;

@Service
public class GlobalUserService implements IGlobalUserService {
	@Autowired
	private ICrudRobotUserDao crudRobotUserDao;
	@Autowired
	private ICrudUserDao crudUserDao;
	@Autowired
	private IUserDao userDao;

	@Override
	public Long registerRobotUser(RobotUser robotUser, String nickname) {
		Long websiteUid = robotUser.getWebsiteUid();
		Integer websiteId = robotUser.getWebsiteId();
		
		User user = new User();
		user.setWebsiteId(websiteId);
		user.setWebsiteUid(websiteUid);
		user.setNickName(nickname);
		user.setIsRobot(true);

		UserExample example = new UserExample();
		example.createCriteria().andWebsiteIdEqualTo(websiteId)
				.andWebsiteUidEqualTo(websiteUid);
		List<User> existUsers = crudUserDao.selectByExample(example);
		Long uid;
		if (existUsers.size() != 0) {
			//可能被其他爬取任务爬到了
			uid = existUsers.get(0).getId();
			user.setId(uid);
			crudUserDao.updateByPrimaryKeySelective(user);
		} else {
			uid = userDao.assignUser(user);
			user.setId(uid);
		}
		
		robotUser.setUid(uid);
		crudRobotUserDao.insert(robotUser);
		return uid;
	}
}
