/*
 *The code is written by NaLi, All rights reserved.
 */
package com.nali.center.dao;

import java.util.List;

import com.nali.center.model.IdentityMeta;

/**
 * @author kenny Created on 2010-6-10
 */
public interface IdentityDao {

	/**
	 * 获取指定应用的详细信息。
	 * 
	 * @param appName
	 *            应用名称
	 */
	IdentityMeta getIdentityMeta(String appName);

	/**
	 * 获取指定应用的详细信息。
	 * 
	 * @param appName
	 *            应用名称
	 */
	List<IdentityMeta> getAllIdentityMeta();

	/**
	 * 更新指定应用的ID。
	 * 
	 * @param appId
	 *            应用ID
	 * @param toValue
	 *            ID需要被更新的值。
	 * @return 修改的数据库记录数。
	 */
	int updateIdentity(int appId, long toValue);
}
