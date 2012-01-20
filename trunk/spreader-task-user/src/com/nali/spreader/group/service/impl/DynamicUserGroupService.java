package com.nali.spreader.group.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.keyvalue.redis.connection.RedisConnection;
import org.springframework.data.keyvalue.redis.core.RedisCallback;
import org.springframework.data.keyvalue.redis.core.RedisTemplate;
import org.springframework.data.keyvalue.redis.core.ZSetOperations;
import org.springframework.data.keyvalue.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;

import com.nali.spreader.group.service.IDynamicUserGroupService;

@Service
public class DynamicUserGroupService implements IDynamicUserGroupService, InitializingBean{
	
	@Autowired
	private RedisTemplate<String, Long> redisTemplate; 
	
	private ZSetOperations<String, Long> zSetOperations;
	
	private RedisSerializer<String> keySerializer;
	
	private RedisSerializer<Object> valueSerializer;

	@Override
	public List<Long> queryExcludedUids(long gid, int start, int limit) {
		String key = getExcludeGroupKey(gid);
		Set<Long> excludeUidSet = zSetOperations.range(key, start, start + limit - 1);
		return excludeUidSet == null ? Collections.<Long>emptyList() : new ArrayList<Long>(excludeUidSet);
	}

	
	@Override
	public List<Long> queryGrouppedUids(long gid, int start, int limit) {
		String key = this.getManualGroupKey(gid);
		Set<Long> uids =  this.zSetOperations.range(key, start, start + limit - 1);
		return new ArrayList<Long>(uids);
	}
	
	@Override
	public long getUserCount(long gid) {
		String key = this.getManualGroupKey(gid);
		return this.zSetOperations.size(key);
	}

	private String getManualGroupKey(long gid) {
		return "sp_m_" + gid;
	}
	
	private String getExcludeGroupKey(long gid) {
		return "sp_e_" + gid;
	}
	
	@Override
	public void addExcludeUsers(long gid, final long... uids) {
		final String key = this.getExcludeGroupKey(gid);
		
		this.redisTemplate.execute(new RedisCallback<Object>() {

			@Override
			public Boolean doInRedis(RedisConnection connection)
					throws DataAccessException {
				for(long uid : uids) {
					connection.zAdd(keySerializer.serialize(key), new Date().getTime(), valueSerializer.serialize(uid));
				}
				return null;
			}
			
		}, true, true);
	}


	@Override
	public void addManualUsers(long gid, final long... uids) {
		final String key = this.getManualGroupKey(gid);
		
		this.redisTemplate.execute(new RedisCallback<Object>() {

			@Override
			public Object doInRedis(RedisConnection connection)
					throws DataAccessException {
				for(long uid : uids) {
					connection.zAdd(keySerializer.serialize(key), new Date().getTime(), valueSerializer.serialize(uid));
				}
				return null;
			}
			
		}, true, true);
	}


	@Override
	public void removeExcludeUsers(long gid, final long... uids) {
		final String key = this.getExcludeGroupKey(gid);
		
		this.redisTemplate.execute(new RedisCallback<Object>() {

			@Override
			public Object doInRedis(RedisConnection connection)
					throws DataAccessException {
				for(long uid : uids) {
					connection.zRem(keySerializer.serialize(key), valueSerializer.serialize(uid));
				}
				return null;
			}
			
		}, true, true);
	}


	@Override
	public void removeManualUsers(long gid, final long... uids) {
		final String key = this.getManualGroupKey(gid);
		this.redisTemplate.execute(new RedisCallback<Object>() {

			@Override
			public Object doInRedis(RedisConnection connection)
					throws DataAccessException {
				for(long uid : uids) {
					connection.zRem(keySerializer.serialize(key), valueSerializer.serialize(uid));
				}
				return null;
			}
			
		}, true, true);
	}
	
	@Override
	public boolean isExclude(long gid, long uid) {
		String key = this.getExcludeGroupKey(gid);
		Long rank =  this.zSetOperations.rank(key, uid);
		return rank != null;
	}
	
	@Override
	public long getExcludeUserCount(long gid) {
		String key = this.getExcludeGroupKey(gid);
		return this.zSetOperations.size(key);
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		this.zSetOperations = this.redisTemplate.opsForZSet();
		this.keySerializer = (RedisSerializer<String>) this.redisTemplate.getKeySerializer();
		this.valueSerializer = (RedisSerializer<Object>) this.redisTemplate.getValueSerializer();
	}
}
