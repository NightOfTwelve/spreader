package com.nali.spreader.util.data.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisTemplate;

import com.nali.spreader.util.data.CommonWrites;
import com.nali.spreader.util.data.Write;

@SuppressWarnings({"unchecked", "rawtypes"})
public class RawRedisWrites<V> extends CommonWrites<String, V> {
	private RedisTemplate<String, Object> redisTemplate;

	public RawRedisWrites(RedisTemplate<String, Object> redisTemplate) {
		super();
		this.redisTemplate = redisTemplate;
	}

	public Write<String, Map<String, List<V>>> writeKeyListMap() {
		return new RawWrite();
	}

	public Write<String, Map<String, V>> writeKeyValueMap() {
		return new RawWrite();
	}

	public Write<String, List<V>> writeList() {
		return new RawWrite();
	}

	public Write<String, Set<V>> writeSet() {
		return new RawWrite();
	}
	
	public Write<String, V> writeValue() {
		return new RawWrite();
	}
	
	private class RawWrite implements Write {
		@Override
		public Object read(Object p) throws DataAccessException {
			return redisTemplate.opsForValue().get(p);
		}

		@Override
		public void write(Object p, Object v) throws DataAccessException {
			redisTemplate.opsForValue().set((String) p, v);
		}
	}
}
