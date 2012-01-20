/*
 *The code is written by NaLi, All rights reserved.
 */
package com.nali.center.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.StringUtils;

import com.nali.center.common.InvalidAppNameException;
import com.nali.center.common.InvalidAppNameRuntimeException;
import com.nali.center.dao.IdentityDao;
import com.nali.center.model.IdentityMeta;
import com.nali.center.service.IIdentityService;
import com.nali.common.util.PropertyConfig;

/**
 * @author Gavin Created on 2012-01-13
 */
public class IdentityService implements IIdentityService {

	private final Map<String, LinkedList<Long>> idMap = new ConcurrentHashMap<String, LinkedList<Long>>();
	private final Map<String, IdentityMeta> APPMETA_MAP = new ConcurrentHashMap<String, IdentityMeta>();

	private IdentityDao identityDao;

	public void init() {
		List<IdentityMeta> identityMetaList = identityDao.getAllIdentityMeta();
		for (IdentityMeta im : identityMetaList) {
			idMap.put(im.getAppName(), new LinkedList<Long>());
			APPMETA_MAP.put(im.getAppName(), im);
		}
	}

	@Override
	public long getNextId(String appName) throws InvalidAppNameRuntimeException {
		if (StringUtils.isEmpty(appName)) {
			throw new InvalidAppNameRuntimeException("app name is empty.");
		}
		LinkedList<Long> idList = idMap.get(appName);
		if (idList == null) {
			throw new InvalidAppNameRuntimeException("can not find app name");
		}
		synchronized (idList) {
			if (idList.size() == 0) {
				preloadResource(idList, PropertyConfig.getInstance()
						.getIntProperty("identity_preload_count", 10), appName);
			}
			return idList.remove();
		}
	}

	@Override
	public Map<String, List<Long>> getMultiNextId(
			Map<String, Integer> appNameCntMap) throws InvalidAppNameRuntimeException {
		Map<String, List<Long>> appIdMap = new HashMap<String, List<Long>>(
				appNameCntMap.size());
		for (Map.Entry<String, Integer> entry : appNameCntMap.entrySet()) {
			appIdMap.put(entry.getKey(), getNextIds(entry.getKey(), entry
					.getValue()));
		}
		return appIdMap;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Long> getNextIds(String appName, int count)
			throws InvalidAppNameRuntimeException {
		if (StringUtils.isEmpty(appName)) {
			throw new InvalidAppNameRuntimeException("app name is empty.");
		}
		if (count <= 0) {
			return Collections.EMPTY_LIST;
		}
		LinkedList<Long> idList = idMap.get(appName);
		if (idList == null) {
			throw new InvalidAppNameRuntimeException("can not find app name");
		}
		List<Long> returnIds = new ArrayList<Long>();
		synchronized (idList) {
			for (int i = 0; i < count; i++) {
				if (idList.size() == 0) {
					preloadResource(idList, PropertyConfig.getInstance()
							.getIntProperty("identity_preload_count", 10),
							appName);
				}
				returnIds.add(idList.remove());
			}
		}
		return returnIds;
	}

	/**
	 * 根据appName加载Id。
	 * 
	 * @param idList
	 *            放入ID的LinkedList
	 * @param count
	 *            获取数量
	 * @param appName
	 *            获取ID的appName
	 * @throws InvalidAppNameException
	 *             appName不合法异常
	 */
	private void preloadResource(LinkedList<Long> idList, int count,
			String appName) throws InvalidAppNameRuntimeException {
		IdentityMeta im = APPMETA_MAP.get(appName);
		if (null == im) {
			throw new InvalidAppNameRuntimeException(
					"can not find IdentityMeta by appName[" + appName + "].");
		}
		
		long currentId = im.getId() + count;
		identityDao.updateIdentity(im.getAppId(), currentId);
		
		im.setId(currentId);
		for (int i = 1; i <= count; i++) {
			idList.add(im.getId() + i);
		}
	}

	public void setIdentityDao(IdentityDao identityDao) {
		this.identityDao = identityDao;
	}
}
