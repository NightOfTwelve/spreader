package com.nali.spreader.util.data.impl;

import java.net.URL;

import org.springframework.data.redis.core.RedisTemplate;

import com.nali.spreader.util.data.CommonCachedReads;
import com.nali.spreader.util.data.CommonReads;
import com.nali.spreader.util.data.Converter;

public class TxtToRedisRead<V> {
	public final CommonReads<String,V> reads;
	public final RawRedisWrites<V> rawRedisWrites;

	public TxtToRedisRead(String redisPrefix, String txtDir, RedisTemplate<String, Object> redisTemplate, Converter<String, V> valueConverter) {
		TxtToRedisParameterConverters converters = new TxtToRedisParameterConverters(redisPrefix, txtDir);
		rawRedisWrites = new RawRedisWrites<V>(redisTemplate);
		TxtReads<V> txtReads = new TxtReads<V>(valueConverter);
		reads = new CommonCachedReads.SameValue<String, URL, String, V>(converters.pToOp, converters.pToCp, txtReads, rawRedisWrites);
	}
}
