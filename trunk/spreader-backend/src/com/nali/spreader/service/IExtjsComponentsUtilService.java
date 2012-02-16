package com.nali.spreader.service;

import com.nali.common.model.Limit;
import com.nali.common.pagination.PageResult;
import com.nali.spreader.data.User;

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

}
