package com.nali.spreader.group.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nali.common.model.Limit;
import com.nali.log.MessageLogger;
import com.nali.log.impl.LoggerFactory;
import com.nali.spreader.dao.ICrudUserGroupDao;
import com.nali.spreader.dao.IUserDao;
import com.nali.spreader.data.UserGroup;
import com.nali.spreader.group.assembler.UserGroupAssembler;
import com.nali.spreader.group.exception.AssembleException;
import com.nali.spreader.group.exception.GroupUserQueryException;
import com.nali.spreader.group.exp.PropertyExpParser;
import com.nali.spreader.group.exp.PropertyExpression;
import com.nali.spreader.group.service.IPropertiesGrouppedUserService;

@Service
public class PropertiesUserGroupService implements
		IPropertiesGrouppedUserService {

	@Autowired
	private IUserDao userDao;

	@Autowired
	private ICrudUserGroupDao crudUserGroupDao;

	@Autowired
	private UserGroupAssembler userGroupAssembler;

	@Autowired
	private PropertyExpParser iBatisPropertyExpParser;

	private static final MessageLogger logger = LoggerFactory
			.getLogger(PropertiesUserGroupService.class);

	@Override
	public List<Long> queryGrouppedUids(long gid, int start, int limit)
			throws GroupUserQueryException {
		UserGroup userGroup = this.crudUserGroupDao.selectByPrimaryKey(gid);
		return this.queryGrouppedUids(userGroup, start, limit);
	}

	@Override
	public long getUserCount(long gid) throws GroupUserQueryException {
		UserGroup userGroup = this.crudUserGroupDao.selectByPrimaryKey(gid);
		return this.getUserCount(userGroup);
	}

	@Override
	public long getUserCount(UserGroup userGroup)
			throws GroupUserQueryException {
		Map<String, Object> propertyMap = this.assemblePropertyMap(userGroup);

		return this.userDao.countByProperties(propertyMap);
	}

	private Map<String, Object> assemblePropertyMap(UserGroup userGroup) {
		String propExp = userGroup.getPropExp();
		try {
			PropertyExpression propertyExpression = this.userGroupAssembler
					.toExpression(propExp);
			Map<String, Object> propertyMap = (Map<String, Object>) this.iBatisPropertyExpParser
					.parseQuery(propertyExpression);

			Integer webSiteId = userGroup.getWebsiteId();
			if (webSiteId != null) {
				propertyMap.put("websiteId", userGroup.getWebsiteId());
			}
			return propertyMap;
		} catch (AssembleException e) {
			logger
					.error(
							"Assemble expression for gid: {0} with expression: {1} fails",
							userGroup.getGid(), propExp);
			throw new GroupUserQueryException(e.getMessage(), e);
		}
	}

	@Override
	public List<Long> queryGrouppedUids(UserGroup userGroup, int start,
			int limit) throws GroupUserQueryException {
		Map<String, Object> propertyMap = this.assemblePropertyMap(userGroup);

		Limit limitObject = Limit.newInstanceForLimit(start, limit);

		List<Long> uids = this.userDao.queryUidsByProperties(propertyMap,
				limitObject);
		return uids;
	}
}
