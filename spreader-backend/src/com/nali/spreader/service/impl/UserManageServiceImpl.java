package com.nali.spreader.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nali.common.model.Limit;
import com.nali.common.pagination.PageResult;
import com.nali.spreader.config.UserTagParamsDto;
import com.nali.spreader.dao.IUserDao;
import com.nali.spreader.data.User;
import com.nali.spreader.service.IUserManageService;

@Service
public class UserManageServiceImpl implements IUserManageService {
	private static final Logger LOGGER = Logger
			.getLogger(UserManageServiceImpl.class);
	@Autowired
	private IUserDao userDao;

	@Override
	public PageResult<User> findUserInfo(UserTagParamsDto utp, Integer start,
			Integer limit) {
		Limit lit = Limit.newInstanceForPage(start, limit);
		List<User> uList = userDao.findUserAndTagInfoList(utp);
		int cnt = userDao.countUserAndTagNumer(utp);
		PageResult<User> pr = new PageResult<User>(uList, lit, cnt);
		return pr;
	}

	@Override
	public PageResult<User> findUserFansInfo(Long id, Integer start,
			Integer limit) {
		// TODO Auto-generated method stub
		return null;
	}

}
