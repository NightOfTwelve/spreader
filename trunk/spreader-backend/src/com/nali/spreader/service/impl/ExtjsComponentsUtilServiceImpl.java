package com.nali.spreader.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.nali.common.model.Limit;
import com.nali.common.pagination.PageResult;
import com.nali.common.util.CollectionUtils;
import com.nali.common.util.DateUtils;
import com.nali.spreader.constants.Website;
import com.nali.spreader.dao.IAppleAppTopDao;
import com.nali.spreader.dao.ICrudCategoryDao;
import com.nali.spreader.dao.ICrudHelpEnumInfoDao;
import com.nali.spreader.dao.ICrudRegularJobResultDao;
import com.nali.spreader.dao.ICrudUserDao;
import com.nali.spreader.dao.ICrudUserGroupDao;
import com.nali.spreader.dao.IGrouppedUserDao;
import com.nali.spreader.dao.ITaskDao;
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
import com.nali.spreader.model.RegularJobResult;
import com.nali.spreader.model.RegularJobResultExample;
import com.nali.spreader.model.TaskStatusCountDto;
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
	@Autowired
	private IGrouppedUserDao grouppedUserDao;
	@Autowired
	private ITaskDao taskDao;
	@Autowired
	private ICrudRegularJobResultDao crudRegularJobResultDao;

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

	@Override
	public String ximalayaExecute() {
		int threeAgo = grouppedUserDao.queryAllGroupUids(562L).size();
		int twoAgo = grouppedUserDao.queryAllGroupUids(561L).size();
		int yest = grouppedUserDao.queryAllGroupUids(542L).size();
		Date createTime = DateUtils.truncateTime(DateUtils.addDays(new Date(),
				-1));
		Date today = DateUtils.truncateTime(new Date());
		UserExample ex = new UserExample();
		ex.createCriteria().andWebsiteIdEqualTo(Website.ximalaya.getId())
				.andIsRobotEqualTo(false)
				.andCreateTimeGreaterThanOrEqualTo(createTime)
				.andCreateTimeLessThan(today);
		int impUsers = crudUserDao.selectByExample(ex).size();
		List<TaskStatusCountDto> addFans1240 = taskDao
				.selectTaskStatusCount(getResultId(1240L));
		List<TaskStatusCountDto> addFans1390 = taskDao
				.selectTaskStatusCount(getResultId(1390L));
		List<TaskStatusCountDto> addFans1391 = taskDao
				.selectTaskStatusCount(getResultId(1391L));
		StringBuilder sb = new StringBuilder();
		sb.append("<html><body>").append("<h4>用户分组导入情况</h4>").append("<ul>")
				.append("<li>").append("562三天前的用户分组人数:").append(threeAgo)
				.append("</li>").append("<li>").append("561前天用户分组人数:")
				.append(twoAgo).append("</li>").append("<li>")
				.append("昨天用户分组人数:").append(yest).append("</li>")
				.append("</ul>").append("<h4>新用户导入情况</h4>").append("<ul>")
				.append("<li>").append("一共导入新用户个数:").append(impUsers)
				.append("</li>").append("<ul>").append("<h4>加粉任务执行情况</h4>")
				.append("<ul>").append("<li>").append("执行加昨天用户粉丝的任务情况(1240):")
				.append(addFans1240).append("</li>").append("<li>")
				.append("执行加前天用户粉丝的任务情况(1390):").append(addFans1390)
				.append("</li>").append("<li>")
				.append("执行加三天前用户粉丝的任务情况(1391):").append(addFans1391)
				.append("</li>").append("</ul>").append("</body></html>");
		return sb.toString();
	}

	private Long getResultId(Long jobId) {
		Date today = DateUtils.truncateTime(new Date());
		RegularJobResultExample ex = new RegularJobResultExample();
		ex.createCriteria().andJobIdEqualTo(jobId)
				.andStartTimeGreaterThanOrEqualTo(today);
		List<RegularJobResult> list = crudRegularJobResultDao
				.selectByExampleWithoutBLOBs(ex);
		if (list.size() > 0) {
			return list.get(0).getId();
		}
		return 0L;
	}
}