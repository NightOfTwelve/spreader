package com.nali.spreader.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nali.common.model.Limit;
import com.nali.common.pagination.PageResult;
import com.nali.spreader.dao.ICrudUserDao;
import com.nali.spreader.data.User;
import com.nali.spreader.data.UserExample;
import com.nali.spreader.data.UserExample.Criteria;
import com.nali.spreader.service.IUserManageService;

@Service
public class UserManageServiceImpl implements IUserManageService {
	private static final Logger LOGGER = Logger
			.getLogger(UserManageServiceImpl.class);
	@Autowired
	private ICrudUserDao crudUserDao;

	@Override
	public PageResult<User> findUserInfo(Long id, String nickname,
			Integer start, Integer limit) {
		// TODO Auto-generated method stub
		UserExample ue = new UserExample();
		Criteria cr = ue.createCriteria();
		if (id != null && id > 0) {
			cr.andIdEqualTo(id);
		}
		if (StringUtils.isNotEmpty(nickname)) {
			cr.andNickNameEqualTo(nickname);
		}
		Limit lit = Limit.newInstanceForPage(start, limit);
		ue.setLimit(lit);
		List<User> uList = crudUserDao.selectByExample(ue);
		int cnt = crudUserDao.countByExample(ue);
		PageResult<User> pr = new PageResult<User>(uList, lit, cnt);
		return pr;
	}

}
