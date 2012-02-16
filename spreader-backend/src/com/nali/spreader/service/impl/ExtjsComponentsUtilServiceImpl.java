package com.nali.spreader.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nali.common.model.Limit;
import com.nali.common.pagination.PageResult;
import com.nali.spreader.dao.ICrudUserDao;
import com.nali.spreader.data.User;
import com.nali.spreader.data.UserExample;
import com.nali.spreader.data.UserExample.Criteria;
import com.nali.spreader.service.IExtjsComponentsUtilService;

@Service
public class ExtjsComponentsUtilServiceImpl implements
		IExtjsComponentsUtilService {
	@Autowired
	private ICrudUserDao crudUserDao;

	@Override
	public PageResult<User> findUserByName(String name, Limit limit) {
		UserExample ue = new UserExample();
		Criteria c = ue.createCriteria();
		if (StringUtils.isNotEmpty(name)) {
			c.andNickNameLike("%" + name + "%");
		}
		ue.setLimit(limit);
		List<User> list = this.crudUserDao.selectByExample(ue);
		int cnt = this.crudUserDao.countByExample(ue);
		return new PageResult<User>(list, limit, cnt);
	}

}
