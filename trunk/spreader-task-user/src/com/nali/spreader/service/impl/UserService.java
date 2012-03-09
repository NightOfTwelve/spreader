package com.nali.spreader.service.impl;

import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.nali.common.model.Limit;
import com.nali.common.util.CollectionUtils;
import com.nali.spreader.config.UserDto;
import com.nali.spreader.dao.ICrudCareerDao;
import com.nali.spreader.dao.ICrudEducationDao;
import com.nali.spreader.dao.ICrudUserDao;
import com.nali.spreader.dao.ICrudUserRelationDao;
import com.nali.spreader.dao.IUserDao;
import com.nali.spreader.data.Career;
import com.nali.spreader.data.CareerExample;
import com.nali.spreader.data.Education;
import com.nali.spreader.data.EducationExample;
import com.nali.spreader.data.KeyValue;
import com.nali.spreader.data.User;
import com.nali.spreader.data.UserExample;
import com.nali.spreader.data.UserRelation;
import com.nali.spreader.data.UserTag;
import com.nali.spreader.exception.SpreaderDataException;
import com.nali.spreader.service.IGlobalUserService;
import com.nali.spreader.service.IUserService;
import com.nali.spreader.service.WebsiteBaseService;
import com.nali.spreader.util.SpecialDateUtil;

@Service
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
	private ICrudUserRelationDao crudUserRelationDao;
	@Autowired
	private IGlobalUserService globalUserService;

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
			globalUserService.updateUserTags(uid, tags);
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
		if(relation.getWebsiteUid()==null) {
			Long uid = relation.getUid();
			if(uid==null) {
				throw new IllegalArgumentException("uid is null");
			}
			User user = crudUserDao.selectByPrimaryKey(uid);
			if(user==null) {
				throw new IllegalArgumentException("cannot find user:" + uid);
			}
			relation.setWebsiteUid(user.getWebsiteUid());
		}
		if(relation.getToWebsiteUid()==null) {
			Long toUid = relation.getToUid();
			if(toUid==null) {
				throw new IllegalArgumentException("toUid is null");
			}
			User user = crudUserDao.selectByPrimaryKey(toUid);
			if(user==null) {
				throw new IllegalArgumentException("cannot find user:" + toUid);
			}
			relation.setToWebsiteUid(user.getWebsiteUid());
		}
		UserRelation rlt = crudUserRelationDao.selectByPrimaryKey(relation.getToUid(), relation.getType(), relation.getUid());
		if(rlt!=null) {
			return;
		}
		try {
			crudUserRelationDao.insertSelective(relation);
		} catch (DuplicateKeyException e) {
			logger.info("double insert.");
		}
		Boolean isRobotUser = relation.getIsRobotUser();
		if(isRobotUser!=null && isRobotUser && UserRelation.TYPE_ATTENTION.equals(relation.getType())) {
			increaseRobotFans(relation.getToUid());
		}
	}

	private void increaseRobotFans(Long uid) {
		userDao.increaseRobotFans(uid);
	}

	@Override
	public List<KeyValue<Long, Long>> findUidToWebsiteUidMapByDto(UserDto dto) {
		dto.setWebsiteId(getWebsiteId());
		return userDao.findUidToWebsiteUidMapByDto(dto);
	}

	@Override
	public List<User> findUserFansInfoByDto(UserDto dto) {
		dto.setWebsiteId(getWebsiteId());
		return userDao.findUserFansInfoByDto(dto);
	}

	@Override
	public List<User> getUsersByIds(List<Long> ids) {
		if(CollectionUtils.isEmpty(ids)) {
			return Collections.emptyList();
		}
		UserExample example = new UserExample();
		example.createCriteria().andIdIn(ids);
		return this.crudUserDao.selectByExample(example);
	}

	@Override
	public List<User> findNoAvatarRobotUserList() {
		return this.userDao.queryNoAvatarRobotUser();
	}
}
