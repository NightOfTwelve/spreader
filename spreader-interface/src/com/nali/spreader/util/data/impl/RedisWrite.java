package com.nali.spreader.util.data.impl;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisTemplate;

import com.nali.spreader.util.data.Write;

public class RedisWrite<P, V> implements Write<P, V> {
	private RedisTemplate<? super P, ? super V> redisTemplate;

	public RedisWrite(RedisTemplate<? super P, ? super V> redisTemplate) {
		super();
		this.redisTemplate = redisTemplate;
	}

	@SuppressWarnings("unchecked")
	@Override
	public V read(P p) throws DataAccessException {
		return (V) redisTemplate.opsForValue().get(p);
	}

	@Override
	public void write(P p, V v) throws DataAccessException {
		redisTemplate.opsForValue().set(p, v);
	}
}