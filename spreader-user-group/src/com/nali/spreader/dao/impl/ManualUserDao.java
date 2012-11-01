package com.nali.spreader.dao.impl;

import java.util.Date;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.nali.spreader.dao.IManualUserDao;

@Repository
public class ManualUserDao implements IManualUserDao {
	@Autowired
	private RedisTemplate<String, Long> redisTemplate;

	private String getManualGroupKey(long gid) {
		return "sp_m_" + gid;
	}

	@Override
	public void removeManualUser(long gid, long uid) {
		String key = getManualGroupKey(gid);
		redisTemplate.opsForZSet().remove(key, uid);
	}

	@Override
	public void addManualUsers(long gid, long uid) {
		String key = getManualGroupKey(gid);
		redisTemplate.opsForZSet().add(key, uid, new Date().getTime());
	}

	@Override
	public Set<Long> queryManualUsers(Long gid) {
		String key = getManualGroupKey(gid);
		return redisTemplate.opsForZSet().reverseRange(key, 0, -1);
	}
}
