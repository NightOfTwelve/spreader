package com.nali.spreader.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nali.spreader.dao.ICrudRobotUserDao;
import com.nali.spreader.dao.ICrudUserDao;
import com.nali.spreader.dao.ICrudUserRelationDao;
import com.nali.spreader.dao.ICrudUserTagDao;
import com.nali.spreader.dao.IUserDao;
import com.nali.spreader.data.User;
import com.nali.spreader.data.UserExample;
import com.nali.spreader.data.UserRelation;
import com.nali.spreader.data.UserRelationExample;
import com.nali.spreader.data.UserTag;
import com.nali.spreader.data.UserTagExample;
import com.nali.spreader.data.UserRelationExample.Criteria;
import com.nali.spreader.model.RobotUser;
import com.nali.spreader.service.ICategoryService;
import com.nali.spreader.service.IGlobalUserService;

@Service
public class GlobalUserService implements IGlobalUserService {
	@Autowired
	private ICrudRobotUserDao crudRobotUserDao;
	@Autowired
	private ICrudUserDao crudUserDao;
	@Autowired
	private IUserDao userDao;
	@Autowired
	private ICrudUserRelationDao crudUserRelationDao;
	@Autowired
	private ICrudUserTagDao crudUserTagDao;
	@Autowired
	private ICategoryService categoryService;

	@Override
	public Long registerRobotUser(RobotUser robotUser, String nickname) {
		Long websiteUid = robotUser.getWebsiteUid();
		Integer websiteId = robotUser.getWebsiteId();

		User user = new User();
		user.setWebsiteId(websiteId);
		user.setWebsiteUid(websiteUid);
		user.setNickName(nickname);
		user.setIsRobot(true);
		user.setGender(robotUser.getGender());

		UserExample example = new UserExample();
		example.createCriteria().andWebsiteIdEqualTo(websiteId)
				.andWebsiteUidEqualTo(websiteUid);
		List<User> existUsers = crudUserDao.selectByExample(example);
		Long uid;
		if (existUsers.size() != 0) {
			// 可能被其他爬取任务爬到了
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

	@Override
	public List<Long> findRelationUserId(Long toUid, Integer attentionType,
			Boolean isRobot) {
		UserRelationExample example = new UserRelationExample();
		Criteria criteria = example.createCriteria().andToUidEqualTo(toUid)
				.andTypeEqualTo(attentionType);
		if (isRobot != null) {
			criteria.andIsRobotUserEqualTo(isRobot);
		}
		List<UserRelation> relations = crudUserRelationDao
				.selectByExample(example);
		List<Long> rlt = new ArrayList<Long>(relations.size());
		for (UserRelation relation : relations) {
			rlt.add(relation.getUid());
		}
		return rlt;
	}

	@Override
	public User getUserById(Long id) {
		return crudUserDao.selectByPrimaryKey(id);
	}

	@Override
	@Transactional
	public void updateUserTags(Long uid, List<UserTag> tags) {
		UserTagExample example = new UserTagExample();
		example.createCriteria().andUidEqualTo(uid);
		crudUserTagDao.deleteByExample(example);
		for (UserTag userTag : tags) {
			userTag.setUid(uid);
			if (userTag.getCategoryId() == null) {
				Long cid = categoryService.getIdByName(userTag.getTag());
				userTag.setCategoryId(cid);
			}
			crudUserTagDao.insertSelective(userTag);
		}
	}

	@Override
	public Long getWebsiteUid(Long uid) {// TODO cache
		User user = crudUserDao.selectByPrimaryKey(uid);
		return user == null ? null : user.getWebsiteUid();
	}

	@Override
	public Long registerRobotUser(RobotUser robotUser, User user) {
		Long websiteUid = robotUser.getWebsiteUid();
		Integer websiteId = robotUser.getWebsiteId();
		user.setWebsiteId(websiteId);
		user.setWebsiteUid(websiteUid);
		user.setCreateTime(new Date());
		user.setIsRobot(true);

		UserExample example = new UserExample();
		example.createCriteria().andWebsiteIdEqualTo(websiteId)
				.andWebsiteUidEqualTo(websiteUid);
		List<User> existUsers = crudUserDao.selectByExample(example);
		Long uid;
		if (existUsers.size() != 0) {
			// 可能被其他爬取任务爬到了
			uid = existUsers.get(0).getId();
			user.setId(uid);
		} else {
			uid = userDao.assignUser(user);
			user.setId(uid);
		}
		crudUserDao.updateByPrimaryKeySelective(user);

		robotUser.setUid(uid);
		crudRobotUserDao.insert(robotUser);
		return uid;
	}
}
