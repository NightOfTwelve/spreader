package com.nali.spreader.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nali.common.model.Limit;
import com.nali.common.pagination.PageResult;
import com.nali.spreader.config.UserTagParamsDto;
import com.nali.spreader.dao.ICrudPhotoDao;
import com.nali.spreader.dao.ICrudRobotRegisterDao;
import com.nali.spreader.dao.IUserDao;
import com.nali.spreader.data.Photo;
import com.nali.spreader.data.RobotRegister;
import com.nali.spreader.data.RobotRegisterExample;
import com.nali.spreader.data.RobotRegisterExample.Criteria;
import com.nali.spreader.data.User;
import com.nali.spreader.data.UserTag;
import com.nali.spreader.service.IUserManageService;
import com.nali.spreader.utils.PhotoHelper;

@Service
public class UserManageServiceImpl implements IUserManageService {
	private static final Logger LOGGER = Logger
			.getLogger(UserManageServiceImpl.class);
	@Autowired
	private IUserDao userDao;
	@Autowired
	private ICrudRobotRegisterDao regDao;
	@Autowired
	private ICrudPhotoDao photoDao;

	@Override
	public PageResult<User> findUserInfo(UserTagParamsDto utp, Integer start,
			Integer limit) {
		Limit lit = Limit.newInstanceForLimit(start, limit);
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
	public PageResult<User> findUserFansInfo(UserTagParamsDto utp,
			Integer start, Integer limit) {
		Limit lit = Limit.newInstanceForPage(start, limit);
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
	public PageResult<RobotRegister> findRobotRegisterInfo(String nickName,
			String province, Integer start, Integer limit) {
		RobotRegisterExample exp = new RobotRegisterExample();
		Criteria cr = exp.createCriteria();
		if (StringUtils.isNotEmpty(nickName)) {
			cr.andNickNameEqualTo(nickName);
		}
		if (StringUtils.isNotEmpty(province)) {
			cr.andProvinceEqualTo(province);
		}
		Limit lit = Limit.newInstanceForLimit(start, limit);
		exp.setLimit(lit);
		List<RobotRegister> list = regDao.selectByExample(exp);
		int count = regDao.countByExample(exp);
		return new PageResult<RobotRegister>(list, lit, count);
	}
}
