/*
 *The code is written by NaLi, All rights reserved.
 */
package com.nali.center.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.nali.center.common.AbstractDAO;
import com.nali.center.dao.IdentityDao;
import com.nali.center.model.IdentityMeta;

/**
 * @author kenny Created on 2010-6-11
 */
@Repository("identityDao")
public class IdentityDaoImpl extends AbstractDAO implements IdentityDao {

	@Override
	public IdentityMeta getIdentityMeta(String appName) {
		return (IdentityMeta) dbSdbSqlMap.queryForObject(
				"IdentityDAO.getIdentityMeta", appName);
	}

	@Override
	public int updateIdentity(int appId, long toValue) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("appId", appId);
		params.put("id", toValue);

		return dbSdbSqlMap.update("IdentityDAO.updateIdentity", params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<IdentityMeta> getAllIdentityMeta() {
		return (List<IdentityMeta>) dbSdbSqlMap
				.queryForList("IdentityDAO.getAllIdentityMeta");
	}

}
