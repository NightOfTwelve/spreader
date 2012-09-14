package com.nali.spreader.service;

import java.util.List;
import java.util.Map;

import com.nali.common.model.Limit;
import com.nali.common.pagination.PageResult;
import com.nali.spreader.data.Category;
import com.nali.spreader.data.User;
import com.nali.spreader.data.UserGroup;
import com.nali.spreader.factory.config.desc.ConfigableInfo;

/**
 * Extjs相关控件的Service
 * 
 * @author xiefei
 * 
 */
public interface IExtjsComponentsUtilService {
	/**
	 * 根据NAME模糊查询用户基本信息
	 * 
	 * @param name
	 * @param limit
	 * @return
	 */
	PageResult<User> findUserByName(String name, Limit limit);

	/**
	 * 根据Name模糊查询用户分组基本信息
	 * 
	 * @param name
	 * @param limit
	 * @return
	 */
	PageResult<UserGroup> findUserGroupByName(String name, Limit limit);

	/**
	 * 根据名称模糊查询分类信息
	 * 
	 * @param name
	 * @param limit
	 * @return
	 */
	PageResult<Category> findCategoryByName(String name, Limit limit);

	/**
	 * 获取所有website
	 * 
	 * @return
	 */
	List<Map<String, Object>> findWebsite();

	/**
	 * 获取所有策略中文名
	 * 
	 * @return
	 */
	List<ConfigableInfo> getAllStrategyDisplayName();
}
