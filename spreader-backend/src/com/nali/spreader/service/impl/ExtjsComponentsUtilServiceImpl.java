package com.nali.spreader.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.nali.common.model.Limit;
import com.nali.common.pagination.PageResult;
import com.nali.common.util.CollectionUtils;
import com.nali.spreader.constants.Website;
import com.nali.spreader.dao.IAppleAppTopDao;
import com.nali.spreader.dao.ICrudCategoryDao;
import com.nali.spreader.dao.ICrudHelpEnumInfoDao;
import com.nali.spreader.dao.ICrudUserDao;
import com.nali.spreader.dao.ICrudUserGroupDao;
import com.nali.spreader.data.Category;
import com.nali.spreader.data.CategoryExample;
import com.nali.spreader.data.HelpEnumInfo;
import com.nali.spreader.data.HelpEnumInfoExample;
import com.nali.spreader.data.User;
import com.nali.spreader.data.UserExample;
import com.nali.spreader.data.UserExample.Criteria;
import com.nali.spreader.dto.UserComboxDisplayDto;
import com.nali.spreader.factory.config.ConfigableType;
import com.nali.spreader.factory.config.IConfigService;
import com.nali.spreader.factory.config.desc.ConfigableInfo;
import com.nali.spreader.model.UserGroup;
import com.nali.spreader.model.UserGroupExample;
import com.nali.spreader.service.IExtjsComponentsUtilService;

@Service
public class ExtjsComponentsUtilServiceImpl implements
		IExtjsComponentsUtilService {
	@Autowired
	private ICrudUserDao crudUserDao;
	@Autowired
	private ICrudUserGroupDao crudUserGroupDao;
	@Autowired
	private ICrudCategoryDao crudCategoryDao;
	@Autowired
	private IConfigService<Long> regularConfigService;
	@Autowired
	private ICrudHelpEnumInfoDao crudHelpEnumInfoDao;
	@Autowired
	private IAppleAppTopDao appleAppTopDao;

	@Override
	public PageResult<UserComboxDisplayDto> findUserByNameAndWebsite(
			String name, int websiteId, Limit limit) {
		UserExample ue = new UserExample();
		Criteria c = ue.createCriteria();
		c.andWebsiteIdEqualTo(websiteId);
		if (StringUtils.isNotEmpty(name)) {
			c.andNickNameLike(name + "%");
		}
		ue.setLimit(limit);
		List<User> list = this.crudUserDao.selectByExample(ue);
		int cnt = this.crudUserDao.countByExample(ue);
		List<UserComboxDisplayDto> viewList = new ArrayList<UserComboxDisplayDto>();
		for (User u : list) {
			UserComboxDisplayDto dto = new UserComboxDisplayDto();
			dto.setId(u.getId());
			dto.setWebsiteId(u.getWebsiteId());
			dto.setNickName(u.getNickName());
			String websiteDesc = Website.valueOf(u.getWebsiteId())
					.getDescriptions()[0];
			dto.setViewName(u.getNickName() + "·<span style=\"color:blue;\">"
					+ websiteDesc + "</span>");
			viewList.add(dto);
		}
		return new PageResult<UserComboxDisplayDto>(viewList, limit, cnt);
	}

	@Override
	public PageResult<UserGroup> findUserGroupByName(String name, Limit limit) {
		UserGroupExample exp = new UserGroupExample();
		com.nali.spreader.model.UserGroupExample.Criteria c = exp
				.createCriteria();
		if (StringUtils.isNotEmpty(name)) {
			c.andGnameLike("%" + name + "%");
		}
		exp.setLimit(limit);
		List<UserGroup> list = this.crudUserGroupDao
				.selectByExampleWithoutBLOBs(exp);
		int cnt = this.crudUserGroupDao.countByExample(exp);
		return new PageResult<UserGroup>(list, limit, cnt);
	}

	@Override
	public PageResult<Category> findCategoryByName(String name, Limit limit) {
		CategoryExample exp = new CategoryExample();
		com.nali.spreader.data.CategoryExample.Criteria c = exp
				.createCriteria();
		if (StringUtils.isNotEmpty(name)) {
			c.andNameLike("%" + name + "%");
		}
		exp.setLimit(limit);
		List<Category> list = this.crudCategoryDao.selectByExample(exp);
		int cnt = this.crudCategoryDao.countByExample(exp);
		return new PageResult<Category>(list, limit, cnt);
	}

	@Override
	public List<Map<String, Object>> findWebsite() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Website[] websites = Website.values();
		for (Website w : websites) {
			Map<String, Object> m = CollectionUtils.newHashMap(2);
			m.put("id", w.getId());
			m.put("name", w.getName());
			list.add(m);
		}
		return list;
	}

	public List<ConfigableInfo> getAllStrategyDisplayName() {
		List<ConfigableInfo> data = new ArrayList<ConfigableInfo>();
		List<ConfigableInfo> normalList = regularConfigService
				.listConfigableInfo(ConfigableType.normal);
		data.addAll(normalList);
		List<ConfigableInfo> systemList = regularConfigService
				.listConfigableInfo(ConfigableType.system);
		data.addAll(systemList);
		List<ConfigableInfo> noticeList = regularConfigService
				.listConfigableInfo(ConfigableType.noticeRelated);
		data.addAll(noticeList);
		return data;
	}

	@Override
	public PageResult<HelpEnumInfo> findHelpEnumInfoByName(String enumName,
			Limit limit) {
		HelpEnumInfoExample exp = new HelpEnumInfoExample();
		com.nali.spreader.data.HelpEnumInfoExample.Criteria c = exp
				.createCriteria();
		if (StringUtils.isNotBlank(enumName)) {
			c.andEnumNameLike("%" + enumName + "%");
		}
		exp.setOrderByClause(" sort_id ");
		exp.setLimit(limit);
		List<HelpEnumInfo> list = crudHelpEnumInfoDao.selectByExample(exp);
		int count = crudHelpEnumInfoDao.countByExample(exp);
		return new PageResult<HelpEnumInfo>(list, limit, count);
	}

	@Override
	public void updateEnum(HelpEnumInfo help) {
		Assert.notNull(help, " HelpEnumInfo is null");
		String name = help.getEnumName();
		String values = help.getEnumValues();
		if (StringUtils.isBlank(name)) {
			throw new IllegalArgumentException(" EnumName is blank ");
		}
		if (StringUtils.isBlank(values)) {
			throw new IllegalArgumentException(" EnumValues is blank ");
		}
		Long id = help.getId();
		if (id == null) {
			crudHelpEnumInfoDao.insertSelective(help);
		} else {
			crudHelpEnumInfoDao.updateByPrimaryKey(help);
		}
	}

	@Override
	public void deleteEnum(Long id) {
		Assert.notNull(id, " id is null");
		crudHelpEnumInfoDao.deleteByPrimaryKey(id);
	}

	@Override
	public PageResult<Map<String, Object>> findAppInfoLikeName(String appName,
			Limit limit) {
		List<Map<String, Object>> list = appleAppTopDao.getAppInfoLikeName(
				appName, limit.offset, limit.maxRows);
		int count = appleAppTopDao.countAppInfoLikeName(appName);
		return new PageResult<Map<String, Object>>(list, limit, count);
	}

	@Override
	public PageResult<Map<String, Object>> findAppInfoById(Long appId,
			Limit limit) {
		List<Map<String, Object>> list = appleAppTopDao.getAppInfoById(appId,
				limit.offset, limit.maxRows);
		int count = appleAppTopDao.countAppInfoById(appId);
		return new PageResult<Map<String, Object>>(list, limit, count);
	}
}