package com.nali.spreader.group.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nali.center.service.IIdentityService;
import com.nali.common.serialization.json.IJsonSerializer;
import com.nali.common.serialization.json.JackSonSerializer;
import com.nali.common.serialization.json.JsonGenerationException;
import com.nali.common.serialization.json.JsonParseException;
import com.nali.spreader.constants.Website;
import com.nali.spreader.dao.ICrudUserGroupDao;
import com.nali.spreader.group.exception.AssembleException;
import com.nali.spreader.group.exp.PropertyExpressionDTO;
import com.nali.spreader.group.meta.UserGroupType;
import com.nali.spreader.group.service.IUserGroupPropertyService;
import com.nali.spreader.model.UserGroup;
import com.nali.spreader.model.UserGroupExample;
import com.nali.spreader.model.UserGroupExample.Criteria;

@Service
public class UserGroupPropertyService implements IUserGroupPropertyService {
	private IJsonSerializer jsonSerializer = new JackSonSerializer();
	@Autowired
	private ICrudUserGroupDao crudUserGroupDao;
	@Autowired
	private IIdentityService identityService;

	@Override
	public long createGroup(UserGroup userGroup) {
		Long gid = userGroup.getGid();
		if (userGroup.getGid() == null) {
			gid = this.identityService.getNextId("spreader.user.group");
			userGroup.setGid(gid);
		}
		Date now = new Date();
		if (userGroup.getCreateTime() == null) {
			userGroup.setCreateTime(now);
		}

		if (userGroup.getLastModifiedTime() == null) {
			userGroup.setLastModifiedTime(now);
		}

		this.crudUserGroupDao.insertSelective(userGroup);
		return gid;
	}

	@Override
	public boolean checkUserGroupUniqueByName(String gname) {
		boolean tag = false;
		if (StringUtils.isNotEmpty(gname)) {
			UserGroupExample ue = new UserGroupExample();
			Criteria c = ue.createCriteria();
			c.andGnameEqualTo(gname);
			List<UserGroup> list = crudUserGroupDao.selectByExampleWithoutBLOBs(ue);
			if (list.size() > 0) {
				tag = true;
			}
		}
		return tag;
	}

	@Override
	public UserGroup assembleUserGroup(Website webSite, String name, String description,
			UserGroupType gtype, PropertyExpressionDTO expressionDTO) throws AssembleException {
		UserGroup userGroup = new UserGroup();
		userGroup.setWebsiteId(webSite.getId());
		userGroup.setDescription(description);
		userGroup.setGname(name);
		expressionDTO.setWebsiteId(webSite.getId());
		try {
			if (gtype.getTypeVal() == UserGroupType.dynamic.getTypeVal()) {
				userGroup.setPropExp(jsonSerializer.toString(expressionDTO));
			} else {
				userGroup.setPropExp(null);
			}
			userGroup.setGtype(gtype.getTypeVal());
			userGroup.setDescription(description);
			userGroup.setPropVal(null);
			return userGroup;
		} catch (JsonGenerationException e) {
			throw new AssembleException("Json assemble user group error.", e);
		} catch (IllegalArgumentException ex) {
			throw new AssembleException("Conatins illegal input arguments, " + ex.getMessage(), ex);
		}
	}

	@Override
	public String toJson(PropertyExpressionDTO expression) throws AssembleException {
		try {
			return this.jsonSerializer.toString(expression);
		} catch (JsonGenerationException e) {
			throw new AssembleException("PropertyExpressionDTO expression to json string fails"
					+ expression, e);
		}
	}

	@Override
	public PropertyExpressionDTO toExpression(String expression) throws AssembleException {
		try {
			return this.jsonSerializer.toBean(expression, PropertyExpressionDTO.class);
		} catch (JsonParseException e) {
			throw new AssembleException("Can't parse string to PropertyExpressionDTO expression: "
					+ expression, e);
		}
	}

	@Override
	public void updateUserGroup(UserGroup userGroup) {
		Date now = new Date();
		userGroup.setLastModifiedTime(now);
		this.crudUserGroupDao.updateByPrimaryKeySelective(userGroup);
	}
}