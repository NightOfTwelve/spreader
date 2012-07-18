package com.nali.spreader.dao.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Repository;

import com.nali.spreader.dao.IAppDownloadDao;

@Repository
public class AppDownloadDao implements IAppDownloadDao {
	private static Logger logger = Logger.getLogger(AppDownloadDao.class);
    private static final String APP_DOWNLOAD_PREFIX = "AppDownload_";
    private static final String LAST_ENDPOINT_PREFIX = "AppDownloadLastEndpoint_";
	private SetOperations<String, Long> set;
	private HashOperations<String, Long, Long> hash;
	
	@Autowired
	public void init(RedisTemplate<String, Long> redisTemplate) {
		set = redisTemplate.opsForSet();
		hash = redisTemplate.opsForHash();
	}
	
	@Override
	public void updateStatus(String appSource, Long appId, Long uid, Integer from, Integer to) {
		String fromKey = key(appSource, appId, from);
		Boolean remove = set.remove(fromKey, uid);
		if(remove==false) {
			logger.error("not find from status from uid, fromKey:" + fromKey + ", uid:" + uid);
		}
		String toKey = key(appSource, appId, to);
		set.add(toKey, uid);
	}

	@Override
	public void setStatus(String appSource, Long appId, Long uid, Integer status) {
		set.add(key(appSource, appId, status), uid);
	}
	
	@Override
	public boolean checkStatus(String appSource, Long appId, Long uid, Integer status) {
		return set.isMember(key(appSource, appId, status), uid);
	}

	@Override
	public Long getLastEndpoint(String appSource, Long appId) {
		return hash.get(endpointKey(appSource), appId);
	}

	@Override
	public void saveEndpoint(String appSource, Long appId, Long uid) {
		hash.put(endpointKey(appSource), appId, appId);
	}
	
	private String endpointKey(String appSource) {
		return LAST_ENDPOINT_PREFIX + appSource;
	}
	
	private String key(String appSource, Long appId, Integer status) {
		return APP_DOWNLOAD_PREFIX + appSource + "#" + appId + "#" + status;
	}
}
