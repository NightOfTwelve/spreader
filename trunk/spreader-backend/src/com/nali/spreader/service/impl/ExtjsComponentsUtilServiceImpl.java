package com.nali.spreader.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nali.common.model.Limit;
import com.nali.common.pagination.PageResult;
import com.nali.spreader.dao.ICrudCategoryDao;
import com.nali.spreader.dao.ICrudUserDao;
import com.nali.spreader.dao.ICrudUserGroupDao;
import com.nali.spreader.data.Category;
import com.nali.spreader.data.CategoryExample;
import com.nali.spreader.data.User;
import com.nali.spreader.data.UserExample;
import com.nali.spreader.data.UserExample.Criteria;
import com.nali.spreader.data.UserGroup;
import com.nali.spreader.data.UserGroupExample;
import com.nali.spreader.service.IExtjsComponentsUtilService;

@Service
public class ExtjsComponentsUtilServiceImpl implements IExtjsComponentsUtilService {
	@Autowired
	private ICrudUserDao crudUserDao;
	@Autowired
	private ICrudUserGroupDao crudUserGroupDao;
	@Autowired
	private ICrudCategoryDao crudCategoryDao;

	@Override
	public PageResult<User> findUserByName(String name, Limit limit) {
		UserExample ue = new UserExample();
		Criteria c = ue.createCriteria();
		if (StringUtils.isNotEmpty(name)) {
			c.andNickNameLike(name + "%");
		}
		ue.setLimit(limit);
		List<User> list = this.crudUserDao.selectByExample(ue);
		int cnt = this.crudUserDao.countByExample(ue);
		return new PageResult<User>(list, limit, cnt);
	}

	@Override
	public PageResult<UserGroup> findUserGroupByName(String name, Limit limit) {
		UserGroupExample exp = new UserGroupExample();
		com.nali.spreader.data.UserGroupExample.Criteria c = exp.createCriteria();
		if (StringUtils.isNotEmpty(name)) {
			c.andGnameLike("%" + name + "%");
		}
		exp.setLimit(limit);
		List<UserGroup> list = this.crudUserGroupDao.selectByExampleWithoutBLOBs(exp);
		int cnt = this.crudUserGroupDao.countByExample(exp);
		return new PageResult<UserGroup>(list, limit, cnt);
	}

	@Override
	public PageResult<Category> findCategoryByName(String name, Limit limit) {
		CategoryExample exp = new CategoryExample();
		com.nali.spreader.data.CategoryExample.Criteria c = exp.createCriteria();
		if (StringUtils.isNotEmpty(name)) {
			c.andNameLike("%" + name + "%");
		}
		exp.setLimit(limit);
		List<Category> list = this.crudCategoryDao.selectByExample(exp);
		int cnt = this.crudCategoryDao.countByExample(exp);
		return new PageResult<Category>(list, limit, cnt);
	}
}
