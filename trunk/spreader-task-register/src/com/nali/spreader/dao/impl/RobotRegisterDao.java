package com.nali.spreader.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.nali.spreader.dao.IRobotRegisterDao;

@Repository
public class RobotRegisterDao implements IRobotRegisterDao {
    private static final String REGISTERING_ACCOUNT_KEY_PREFIX = "RegisteringAccount_";
	@Autowired
    private RedisTemplate<String, Integer> redisTemplate;

	@Override
	public Integer countRegisteringAccount(Integer websiteId) {
		return redisTemplate.opsForHash().size(registeringAccountKey(websiteId)).intValue();
	}
	
	@Override
	public void addRegisteringAccount(Integer websiteId, Long registerId, String nickname) {
		redisTemplate.opsForHash().put(registeringAccountKey(websiteId), registerId, nickname);
	}
	
	@Override
	public void removeRegisteringAccount(Integer websiteId, Long registerId) {
		redisTemplate.opsForHash().delete(registeringAccountKey(websiteId), registerId);
	}

	private String registeringAccountKey(Integer websiteId) {
		return REGISTERING_ACCOUNT_KEY_PREFIX + websiteId;
	}

	@Override
	public String getRegisteringAccount(Integer websiteId, Long registerId) {
		return (String) redisTemplate.opsForHash().get(registeringAccountKey(websiteId), registerId);
	}

}
