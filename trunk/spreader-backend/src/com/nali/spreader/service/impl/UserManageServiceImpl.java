package com.nali.spreader.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nali.common.model.Limit;
import com.nali.common.pagination.PageResult;
import com.nali.common.util.CollectionUtils;
import com.nali.spreader.config.ContentKeywordsConfig;
import com.nali.spreader.config.Range;
import com.nali.spreader.config.UserTagParamsDto;
import com.nali.spreader.dao.ICrudPhotoDao;
import com.nali.spreader.dao.ICrudRobotRegisterDao;
import com.nali.spreader.dao.ICrudUserDao;
import com.nali.spreader.dao.IUserDao;
import com.nali.spreader.data.Photo;
import com.nali.spreader.data.RobotRegister;
import com.nali.spreader.data.RobotRegisterExample;
import com.nali.spreader.data.RobotRegisterExample.Criteria;
import com.nali.spreader.data.User;
import com.nali.spreader.data.UserTag;
import com.nali.spreader.service.IUserManageService;
import com.nali.spreader.util.random.NumberRandomer;
import com.nali.spreader.util.random.RandomUtil;
import com.nali.spreader.util.random.Randomer;
import com.nali.spreader.utils.PhotoHelper;

@Service
public class UserManageServiceImpl implements IUserManageService {
	private static final Logger LOGGER = Logger.getLogger(UserManageServiceImpl.class);
	@Autowired
	private IUserDao userDao;
	@Autowired
	private ICrudRobotRegisterDao regDao;
	@Autowired
	private ICrudPhotoDao photoDao;
	@Autowired
	private ICrudUserDao crudUserDao;

	@Override
	public PageResult<User> findUserInfo(UserTagParamsDto utp, Limit lit) {
		utp.setLimit(lit);
		List<User> uList = userDao.findUserAndTagInfoList(utp);
		for (User u : uList) {
			StringBuffer buff = new StringBuffer();
			List<UserTag> utList = u.getTags();
			Long pid = u.getPid();
			if (pid != null) {
				Photo p = photoDao.selectByPrimaryKey(pid);
				if (p != null) {
					String pUri = p.getPicUrl();
					u.setAvatarUrl(PhotoHelper.formatPhotoUrl(pUri));
				} else {
					LOGGER.info("未找到对应头像信息");
				}
			}
			if (utList.size() > 0) {
				for (UserTag ut : utList) {
					buff.append(ut.getTag());
					buff.append(",");
				}
			}
			u.setTag(buff.toString());
		}
		int cnt = userDao.countUserAndTagNumer(utp);
		PageResult<User> pr = new PageResult<User>(uList, lit, cnt);
		return pr;
	}

	@Override
	public PageResult<User> findUserFansInfo(UserTagParamsDto utp, Limit lit) {
		utp.setLimit(lit);
		List<User> uList = userDao.findUserFansInfoList(utp);
		for (User u : uList) {
			StringBuffer buff = new StringBuffer();
			List<UserTag> utList = u.getTags();
			Long pid = u.getPid();
			if (pid != null) {
				Photo p = photoDao.selectByPrimaryKey(pid);
				if (p != null) {
					String pUri = p.getPicUrl();
					u.setAvatarUrl(PhotoHelper.formatPhotoUrl(pUri));
				} else {
					LOGGER.info("未找到对应头像信息");
				}
			}
			if (utList.size() > 0) {
				for (UserTag ut : utList) {
					buff.append(ut.getTag());
					buff.append(",");
				}
			}
			u.setTag(buff.toString());
		}
		int cnt = userDao.countUserFansNumer(utp);
		PageResult<User> pr = new PageResult<User>(uList, lit, cnt);
		return pr;
	}

	@Override
	public PageResult<RobotRegister> findRobotRegisterInfo(String nickName, String province,
			Limit lit) {
		RobotRegisterExample exp = new RobotRegisterExample();
		Criteria cr = exp.createCriteria();
		if (StringUtils.isNotEmpty(nickName)) {
			cr.andNickNameEqualTo(nickName);
		}
		if (StringUtils.isNotEmpty(province)) {
			cr.andProvinceEqualTo(province);
		}
		exp.setLimit(lit);
		List<RobotRegister> list = regDao.selectByExample(exp);
		int count = regDao.countByExample(exp);
		return new PageResult<RobotRegister>(list, lit, count);
	}

	@Override
	public int updateUserProprietor(User user) {
		int rows = this.crudUserDao.updateByPrimaryKeySelective(user);
		return rows;
	}

	@Override
	public String findUserRegisterPassword(Long uid) {
		String pwd = "";
		if (uid == null) {
			throw new IllegalArgumentException("参数为空，无法获取用户密码");
		} else {
			User u = new User();
			u.setId(uid);
			pwd = this.userDao.getUserPassword(u);
		}
		return pwd;
	}

	@Override
	public <T> List<T> getRandomList(List<T> list, Range<Integer> range) {
		Randomer<Integer> randomer;
		if (range.checkNotNull()) {
			randomer = new NumberRandomer(range.getGte(), range.getLte() + 1);
		} else {
			randomer = new NumberRandomer(ContentKeywordsConfig.DEFAULT_RANDOM_GTE,
					ContentKeywordsConfig.DEFAULT_RANDOM_LTE + 1);
		}
		List<T> result;
		if (!CollectionUtils.isEmpty(list)) {
			result = RandomUtil.randomItems(list, randomer.get());
			return result;
		} else {
			return Collections.emptyList();
		}
	}

	@Override
	public List<UserTag> createUserTags(List<String> keywordList) {
		List<UserTag> tagList = new ArrayList<UserTag>();
		if (!CollectionUtils.isEmpty(keywordList)) {
			for (String tag : keywordList) {
				UserTag ut = new UserTag();
				ut.setTag(tag);
				tagList.add(ut);
			}
		}
		return tagList;
	}
}
