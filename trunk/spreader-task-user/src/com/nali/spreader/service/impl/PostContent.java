package com.nali.spreader.service.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class PostContent {
	private static final String KEY_PREFIX = "PostContent_";
	@Autowired
    private RedisTemplate<String, Long> redisTemplate;

	public Set<Long> getPostContentIds(Long uid) {
		return redisTemplate.opsForSet().members(key(uid));
	}

	public void addPostContentId(Long uid, Long contentId) {
		redisTemplate.opsForSet().add(key(uid), contentId);
	}

	private String key(Long uid) {
		return KEY_PREFIX + uid;
	}
}
