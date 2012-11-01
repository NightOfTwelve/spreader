package com.nali.spreader.group.service;

import com.nali.spreader.constants.Website;
import com.nali.spreader.group.exception.AssembleException;
import com.nali.spreader.group.exp.PropertyExpressionDTO;
import com.nali.spreader.group.meta.UserGroupType;
import com.nali.spreader.model.UserGroup;

/**
 * <code>IUserGroupService</code> contains all the service about user group. A
 * user group contain number of users groupped by manual or their properties;
 * 
 * @author gavin
 * 
 */
public interface IUserGroupPropertyService {

	/**
	 * 更新一个用户分组
	 * 
	 * @param userGroup
	 */
	void updateUserGroup(UserGroup userGroup);

	/**
	 * 保存一个用户分组
	 * 
	 * @param userGroup
	 */
	long createGroup(UserGroup userGroup);

	/**
	 * 验证是否重命名
	 * 
	 * @param gname
	 * @return
	 */
	boolean checkUserGroupUniqueByName(String gname);

	/**
	 * 创建一个usergroup
	 * 
	 * @param webSite
	 * @param name
	 * @param description
	 * @param gtype
	 * @param expressionDTO
	 * @return
	 */
	UserGroup assembleUserGroup(Website webSite, String name, String description,
			UserGroupType gtype, PropertyExpressionDTO expressionDTO) throws AssembleException;

	String toJson(PropertyExpressionDTO expression) throws AssembleException;

	PropertyExpressionDTO toExpression(String expression) throws AssembleException;
}