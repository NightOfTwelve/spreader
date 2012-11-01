package com.nali.spreader.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.nali.common.model.Limit;
import com.nali.common.model.Shard;
import com.nali.common.util.CollectionUtils;
import com.nali.spreader.config.Range;
import com.nali.spreader.constants.Website;
import com.nali.spreader.dao.ICrudRobotUserDao;
import com.nali.spreader.dao.ICrudUserDao;
import com.nali.spreader.dao.ICrudUserRelationDao;
import com.nali.spreader.dao.ICrudUserTagDao;
import com.nali.spreader.dao.ICrudWeiboAppealDao;
import com.nali.spreader.dao.IUserDao;
import com.nali.spreader.data.User;
import com.nali.spreader.data.UserExample;
import com.nali.spreader.data.UserRelation;
import com.nali.spreader.data.UserRelationExample;
import com.nali.spreader.data.UserRelationExample.Criteria;
import com.nali.spreader.data.UserTag;
import com.nali.spreader.data.UserTagExample;
import com.nali.spreader.data.WeiboAppeal;
import com.nali.spreader.data.WeiboAppealExample;
import com.nali.spreader.dto.FilterUserDto;
import com.nali.spreader.dto.UserQueryParamDto;
import com.nali.spreader.model.RobotUser;
import com.nali.spreader.service.IGlobalRobotUserService;
import com.nali.spreader.service.IGlobalUserService;
import com.nali.spreader.service.IKeywordService;

@Service
public class GlobalUserService implements IGlobalUserService {
	private static Logger logger = Logger.getLogger(GlobalUserService.class);
	private static final Shard BAK_USER_SHARD;
	private static final Shard BAK_USER_EXPORT;
	// dao
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
	private ICrudWeiboAppealDao crudWeiboAppealDao;

	// service
	@Autowired
	private IGlobalRobotUserService globalRobotUserService;
	@Autowired
	private IKeywordService keywordService;

	static {
		BAK_USER_SHARD = new Shard();
		BAK_USER_SHARD.setTableSuffix("_delete");
		BAK_USER_SHARD.setDatabaseSuffix("");
		BAK_USER_EXPORT = new Shard();
		BAK_USER_EXPORT.setTableSuffix("_export");
		BAK_USER_EXPORT.setDatabaseSuffix("");
	}

	@Override
	public Long getOrAssignUid(Integer websiteId, Long websiteUid) {
		User user = findByUniqueKey(websiteId, websiteUid);
		if (user != null) {
			return user.getId();
		}
		user = new User();
		user.setWebsiteId(websiteId);
		user.setWebsiteUid(websiteUid);
		user.setIsRobot(false);
		try {
			return userDao.assignUser(user);
		} catch (DuplicateKeyException e) {
			return getOrAssignUid(websiteId, websiteUid);
		}
	}

	@Override
	public User findByUniqueKey(Integer websiteId, Long websiteUid) {
		UserExample example = new UserExample();
		example.createCriteria().andWebsiteIdEqualTo(websiteId).andWebsiteUidEqualTo(websiteUid);
		List<User> existUsers = crudUserDao.selectByExample(example);
		if (existUsers.size() != 0) {
			return existUsers.get(0);
		} else {
			return null;
		}
	}

	@Override
	public List<Long> findRelationUserId(Long toUid, Integer attentionType, Boolean isRobot) {
		UserRelationExample example = new UserRelationExample();
		Criteria criteria = example.createCriteria().andToUidEqualTo(toUid)
				.andTypeEqualTo(attentionType);
		if (isRobot != null) {
			criteria.andIsRobotUserEqualTo(isRobot);
		}
		List<UserRelation> relations = crudUserRelationDao.selectByExample(example);
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
	public void removeUser(Long id) {
		User user = crudUserDao.selectByPrimaryKey(id);
		if (user != null) {
			user.setShard(BAK_USER_SHARD);
			try {
				crudUserDao.insertSelective(user);
			} catch (DuplicateKeyException e) {
				logger.debug("double insert");
			}
			crudUserDao.deleteByPrimaryKey(id);
		}
	}

	@Override
	public void updateUserTags(Long uid, List<UserTag> tags) {
		UserTagExample example = new UserTagExample();
		example.createCriteria().andUidEqualTo(uid);
		crudUserTagDao.deleteByExample(example);
		for (UserTag userTag : tags) {
			userTag.setUid(uid);
			// tagId如果为null需根据名称查找keyword
			if (userTag.getTagId() == null) {
				String keywordName = userTag.getTag();
				Long keywordId = this.keywordService.getOrAssignKeywordIdByName(keywordName);
				userTag.setTagId(keywordId);
			}
			crudUserTagDao.insertSelective(userTag);
		}
	}

	@Override
	public Long getWebsiteUid(Long uid) {// TODO cache
		User user = crudUserDao.selectByPrimaryKey(uid);
		if (user == null) {
			user = getDeletedUser(uid);
		}
		return user == null ? null : user.getWebsiteUid();
	}

	@Override
	public User getDeletedUser(Long uid) {
		return getBakUser(uid, BAK_USER_SHARD);
	}

	private User getBakUser(Long uid, Shard userShard) {
		UserExample userExample = new UserExample();
		userExample.setShard(userShard);
		userExample.createCriteria().andIdEqualTo(uid);
		List<User> rlt = crudUserDao.selectByExample(userExample);
		if (rlt.size() == 0) {
			return null;
		} else {
			return rlt.get(0);
		}
	}

	@Override
	public Long registerRobotUser(RobotUser robotUser, User user) {
		Long websiteUid = robotUser.getWebsiteUid();
		Integer websiteId = robotUser.getWebsiteId();
		user.setWebsiteId(websiteId);
		user.setWebsiteUid(websiteUid);
		user.setCreateTime(new Date());
		user.setIsRobot(true);

		User existUser = findByUniqueKey(websiteId, websiteUid);
		Long uid;
		if (existUser != null) {
			// 可能被其他爬取任务爬到了
			uid = existUser.getId();
			user.setId(uid);
		} else {
			uid = userDao.assignUser(user);
			user.setId(uid);
		}
		crudUserDao.updateByPrimaryKeySelective(user);
		robotUser.setUid(uid);
		RobotUser robot = crudRobotUserDao.selectByPrimaryKey(uid);
		if (robot != null) {
			crudRobotUserDao.updateByPrimaryKeySelective(robotUser);
		} else {
			crudRobotUserDao.insert(robotUser);
		}
		return uid;
	}

	@Override
	public void mergeWeiboAppeal(WeiboAppeal weiboAppeal) {
		int updateCount = crudWeiboAppealDao.updateByPrimaryKeySelective(weiboAppeal);
		if (updateCount == 0) {
			try {
				crudWeiboAppealDao.insertSelective(weiboAppeal);
			} catch (DuplicateKeyException e) {
				logger.error("duplicate insert weiboAppeal, uid:" + weiboAppeal.getUid());
				mergeWeiboAppeal(weiboAppeal);
			}
		}
	}

	@Override
	public void removeWeiboAppeal(Long uid) {
		crudWeiboAppealDao.deleteByPrimaryKey(uid);
	}

	@Override
	public boolean initWeiboAppeal(Long uid, boolean forceMerge) {
		WeiboAppeal weiboAppeal = new WeiboAppeal();
		weiboAppeal.setUid(uid);
		weiboAppeal.setStatus(WeiboAppeal.STATUS_INIT);
		if (forceMerge) {
			mergeWeiboAppeal(weiboAppeal);
			return true;
		} else {
			try {
				crudWeiboAppealDao.insertSelective(weiboAppeal);
				return true;
			} catch (DuplicateKeyException e) {
				return false;
			}
		}
	}

	@Override
	public User recoverDeletedUser(Long uid) {
		User user = getDeletedUser(uid);
		if (user == null) {
			return getUserById(uid);
		} else {
			user.setShard(null);
			try {
				crudUserDao.insertSelective(user);
			} catch (DuplicateKeyException e) {
				logger.error("double insert on recoverDeletedUser, uid:" + uid);
			}
			UserExample example = new UserExample();
			example.setShard(BAK_USER_SHARD);
			example.createCriteria().andIdEqualTo(uid);
			crudUserDao.deleteByExample(example);
			return user;
		}
	}

	@Override
	public List<WeiboAppeal> findUncheckedWeiboAppeal(Date startDate) {
		WeiboAppealExample example = new WeiboAppealExample();
		com.nali.spreader.data.WeiboAppealExample.Criteria criteria = example.createCriteria()
				.andStatusEqualTo(WeiboAppeal.STATUS_START);
		if (startDate != null) {
			criteria.andStartTimeLessThan(startDate);
		}
		return crudWeiboAppealDao.selectByExample(example);
	}

	@Override
	public List<WeiboAppeal> findInitedWeiboAppeal(int limit) {
		WeiboAppealExample example = new WeiboAppealExample();
		example.createCriteria().andStatusEqualTo(WeiboAppeal.STATUS_INIT);
		example.setLimit(Limit.newInstanceForLimit(0, limit));
		return crudWeiboAppealDao.selectByExample(example);
	}

	@Transactional
	@Override
	public RobotUser exportUser(Long uid) {
		User user = crudUserDao.selectByPrimaryKey(uid);
		if (user != null) {
			user.setShard(BAK_USER_EXPORT);
			try {
				crudUserDao.insertSelective(user);
			} catch (DuplicateKeyException e) {
				logger.debug("double insert");
				user = null;
			}
			crudUserDao.deleteByPrimaryKey(uid);
		}
		if (user == null) {
			return null;
		}
		globalRobotUserService.changeAccountStatus(uid, RobotUser.ACCOUNT_STATE_EXPORT);
		return globalRobotUserService.getRobotUser(uid);
	}

	@Override
	public User assignUserByWebsiteUid(Integer websiteId, Long websiteUid) {
		Assert.notNull(websiteId, "websiteId is null");
		Assert.notNull(websiteUid, "websiteUid is null");
		Long uid = getOrAssignUid(websiteId, websiteUid);
		return this.getUserById(uid);
	}

	@Override
	public Long[] findPostContentUids(Integer vType, Range<Long> fans, Range<Long> articles) {
		if (vType == null && (fans == null || fans.checkAllNull())
				&& (articles == null || articles.checkAllNull())) {
			return null;
		}
		FilterUserDto query = new FilterUserDto();
		query.setvType(vType);
		if (fans != null) {
			query.setGteFans(fans.getGte());
			query.setLteFans(fans.getLte());
		}
		if (articles != null) {
			query.setGteArticles(articles.getGte());
			query.setLteArticles(articles.getLte());
		}
		query.setWebsiteId(Website.weibo.getId());
		List<Long> uidList = this.userDao.queryPostContentUids(query);
		Long[] tmp = new Long[uidList.size()];
		return uidList.toArray(tmp);
	}

	@Override
	public String getNickNameByWebsiteUid(Integer websiteId, Long websiteUid) {
		Assert.notNull(websiteId, " websiteId is null");
		Assert.notNull(websiteUid, " websiteUid is null");
		Map<String, Object> param = CollectionUtils.newHashMap(2);
		param.put("websiteId", websiteId);
		param.put("websiteUid", websiteUid);
		return this.userDao.queryNickNameByWebsiteUid(param);
	}

	@Override
	public List<Long> getAttenLimitUids(List<Long> uids, Long attenLimit) {
		List<Long> result = new ArrayList<Long>();
		if (!CollectionUtils.isEmpty(uids)) {
			UserQueryParamDto param = new UserQueryParamDto();
			param.setAttentionLimit(attenLimit);
			param.setUids(uids);
			result = this.userDao.queryAttenLimitUids(param);
		}
		return result;
	}

	@Override
	public List<User> getUserMapByUids(List<Long> data) {
		List<User> result = new ArrayList<User>();
		if (!CollectionUtils.isEmpty(data)) {
			for (Long uid : data) {
				User user = this.crudUserDao.selectByPrimaryKey(uid);
				result.add(user);
			}
		}
		return result;
	}

	@Override
	public List<User> getUsersByIds(List<Long> uids) {
		if (CollectionUtils.isEmpty(uids)) {
			return Collections.emptyList();
		}
		UserExample example = new UserExample();
		example.createCriteria().andIdIn(uids);
		return this.crudUserDao.selectByExample(example);
	}
}
