package com.nali.spreader.util.data.impl;

import org.springframework.data.redis.core.RedisTemplate;

public class RawRedisWrites<V> extends RawWrites<String, V> {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public RawRedisWrites(RedisTemplate<String, Object> redisTemplate) {
		super(new RedisWrite(redisTemplate));
	}

}
