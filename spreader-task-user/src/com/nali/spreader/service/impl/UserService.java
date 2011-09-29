package com.nali.spreader.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;

import com.nali.common.model.Limit;
import com.nali.spreader.dao.ICrudCareerDao;
import com.nali.spreader.dao.ICrudEducationDao;
import com.nali.spreader.dao.ICrudUserDao;
import com.nali.spreader.dao.ICrudUserRelationDao;
import com.nali.spreader.dao.ICrudUserTagDao;
import com.nali.spreader.dao.IUserDao;
import com.nali.spreader.data.Career;
import com.nali.spreader.data.CareerExample;
import com.nali.spreader.data.Education;
import com.nali.spreader.data.EducationExample;
import com.nali.spreader.data.User;
import com.nali.spreader.data.UserExample;
import com.nali.spreader.data.UserRelation;
import com.nali.spreader.data.UserTag;
import com.nali.spreader.data.UserTagExample;
import com.nali.spreader.exception.SpreaderDataException;
import com.nali.spreader.service.IUserService;
import com.nali.spreader.service.WebsiteBaseService;
import com.nali.spreader.util.SpecialDateUtil;

public class UserService extends WebsiteBaseService implements IUserService {
	private static final int EXPIRED_DAY = -10;
	private static Logger logger = Logger.getLogger(UserService.class);
	@Autowired
	private ICrudUserDao crudUserDao;
	@Autowired
	private IUserDao userDao;
	@Autowired
	private ICrudCareerDao crudCareerDao;
	@Autowired
	private ICrudEducationDao crudEducationDao;
	@Autowired
	private ICrudUserTagDao crudUserTagDao;
	@Autowired
	private ICrudUserRelationDao crudUserRelationDao;

	@Override
	public Long assignUser(Long websiteUid) {
		UserExample example = new UserExample();
		example.createCriteria().andWebsiteIdEqualTo(getWebsiteId())
				.andWebsiteUidEqualTo(websiteUid);
		List<User> existUsers = crudUserDao.selectByExample(example);
		if (existUsers.size() != 0) {
			return null;
		}
		User user = new User();
		user.setWebsiteId(getWebsiteId());
		user.setWebsiteUid(websiteUid);
		user.setIsRobot(false);
		try {
			return userDao.assignUser(user);
		} catch (DuplicateKeyException e) {
			logger.info("double insert.");
			return null;
		}
	}

	@Override
	public void updateUser(User user) {
		if (user.getId() == null) {
			throw new SpreaderDataException("updating user, but uid is null");
		}
		user.setWebsiteId(getWebsiteId());
		crudUserDao.updateByPrimaryKeySelective(user);
		Long uid = user.getId();
		List<Career> careers = user.getCareers();
		if (careers != null) {
			updateCareers(uid, careers);
		}
		List<Education> educations = user.getEducations();
		if (educations != null) {
			updateEducations(uid, educations);
		}
		List<UserTag> tags = user.getTags();
		if (tags != null) {
			updateUserTags(uid, tags);
		}
	}

	private void updateUserTags(Long uid, List<UserTag> tags) {
		UserTagExample example = new UserTagExample();
		example.createCriteria().andUidEqualTo(uid);
		crudUserTagDao.deleteByExample(example);
		for (UserTag userTag : tags) {
			userTag.setUid(uid);
			crudUserTagDao.insertSelective(userTag);
		}
	}

	private void updateEducations(Long uid, List<Education> educations) {
		EducationExample example = new EducationExample();
		example.createCriteria().andUidEqualTo(uid);
		crudEducationDao.deleteByExample(example);
		for (Education education : educations) {
			education.setUid(uid);
			crudEducationDao.insertSelective(education);
		}
	}

	private void updateCareers(Long uid, List<Career> careers) {
		CareerExample example = new CareerExample();
		example.createCriteria().andUidEqualTo(uid);
		crudCareerDao.deleteByExample(example);
		for (Career career : careers) {
			career.setUid(uid);
			crudCareerDao.insertSelective(career);
		}
	}

	@Override
	public long getExpiredUserCount() {
		return crudUserDao.countByExample(getExpiredUserExample());
	}

	private UserExample getExpiredUserExample() {
		UserExample example = new UserExample();
		example.createCriteria().andUpdateTimeLessThan(SpecialDateUtil.afterToday(EXPIRED_DAY)).andWebsiteIdEqualTo(getWebsiteId());
		return example;
	}

	@Override
	public List<User> getExpiredUser(long offset, int batchSize) {
		if(offset>Integer.MAX_VALUE) {
			throw new IllegalArgumentException("offset too large:" + offset);
		}
		UserExample example = getExpiredUserExample();
		example.setLimit(Limit.newInstanceForLimit((int) offset, batchSize));
		return crudUserDao.selectByExample(example);
	}

	@Override
	public List<User> getUninitializedUser(long offset, int batchSize) {
		if(offset>Integer.MAX_VALUE) {
			throw new IllegalArgumentException("offset too large:" + offset);
		}
		UserExample example = getUninitializedUserExample();
		example.setLimit(Limit.newInstanceForLimit((int) offset, batchSize));
		return crudUserDao.selectByExample(example);
	}
	
	@Override
	public long getUninitializedUserCount() {
		return crudUserDao.countByExample(getUninitializedUserExample());
	}
	
	@Override
	public long getUserCount() {
		UserExample example = new UserExample();
		example.createCriteria().andWebsiteIdEqualTo(getWebsiteId());
		return crudUserDao.countByExample(example);
	}

	private UserExample getUninitializedUserExample() {
		UserExample example = new UserExample();
		example.createCriteria().andUpdateTimeIsNull().andWebsiteIdEqualTo(getWebsiteId());
		return example;
	}

	@Override
	public User getUserById(Long id) {
		return crudUserDao.selectByPrimaryKey(id);
	}

	@Override
	public User getUserByWebsiteUid(Long websiteUid) {
		UserExample example = new UserExample();
		example.createCriteria().andWebsiteIdEqualTo(getWebsiteId()).andWebsiteUidEqualTo(websiteUid);
		List<User> list = crudUserDao.selectByExample(example);
		if(list.size()==0) {
			return null;
		}
		return list.get(0);
	}

	@Override
	public void createRelation(UserRelation relation) {
		relation.setWebsiteId(getWebsiteId());
		UserRelation rlt = crudUserRelationDao.selectByPrimaryKey(relation.getToUid(), relation.getType(), relation.getUid());
		if(rlt!=null) {
			return;
		}
		try {
			crudUserRelationDao.insertSelective(relation);
		} catch (DuplicateKeyException e) {
			logger.info("double insert.");
		}
	}
}
