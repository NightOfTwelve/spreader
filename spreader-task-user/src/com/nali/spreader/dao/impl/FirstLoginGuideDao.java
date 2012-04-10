package com.nali.spreader.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.keyvalue.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.nali.spreader.dao.IFirstLoginGuideDao;

@Repository
public class FirstLoginGuideDao implements IFirstLoginGuideDao {
	private static final String FIRST_LOGIN_GUIDE_FLAG = "FirstLoginGuideFlag";
	private static final String DELETED_USER_LOGIN_GUIDE_FLAG = "DeletedUserGuideFlag";
	private static final String FIRST_LOGIN_GUIDE= "FirstLoginGuide";
	@Autowired
	private RedisTemplate<String, Long> redisTemplate;
	
	@Override
	public void setInitFlagOn() {
		redisTemplate.opsForValue().set(FIRST_LOGIN_GUIDE_FLAG, System.currentTimeMillis());
	}

	@Override
	public boolean isInitFlagOn() {
		return redisTemplate.opsForValue().get(FIRST_LOGIN_GUIDE_FLAG)!=null;
	}
	
	@Override
	public boolean isUserGuide(Long uid) {
		return !redisTemplate.opsForSet().isMember(FIRST_LOGIN_GUIDE, uid);
	}

	@Override
	public void guide(Long uid) {
		redisTemplate.opsForSet().remove(FIRST_LOGIN_GUIDE, uid);
	}

	@Override
	public void init(Long uid) {
		redisTemplate.opsForSet().add(FIRST_LOGIN_GUIDE, uid);
	}

	@Override
	public boolean isDeletedUserInitFlagOn() {
		return redisTemplate.opsForValue().get(DELETED_USER_LOGIN_GUIDE_FLAG)!=null;
	}

	@Override
	public void setDeletedUserInitFlagOn() {
		redisTemplate.opsForValue().set(DELETED_USER_LOGIN_GUIDE_FLAG, System.currentTimeMillis());
	}
}
