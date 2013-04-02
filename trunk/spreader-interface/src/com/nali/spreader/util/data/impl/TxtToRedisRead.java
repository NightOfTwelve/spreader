package com.nali.spreader.util.data.impl;

import java.net.URL;

import org.springframework.data.redis.core.RedisTemplate;

import com.nali.spreader.util.data.CommonCachedReads;
import com.nali.spreader.util.data.CommonReads;
import com.nali.spreader.util.data.CommonWrites;
import com.nali.spreader.util.data.Converter;
import com.nali.spreader.util.data.WriteWrap;

public class TxtToRedisRead<V> {
	public final CommonReads<String,V> reads;
	public final CommonWrites<String,V> writes;

	public TxtToRedisRead(String redisPrefix, String txtDir, RedisTemplate<String, Object> redisTemplate, Converter<String, V> valueConverter) {
		TxtToRedisParameterConverters converters = new TxtToRedisParameterConverters(redisPrefix, txtDir);
		RedisWrite<String, V> redisWrite = new RedisWrite<String, V>(redisTemplate);
		writes = new RawWrites<String,V>(new WriteWrap<String, V, String, V>(redisWrite, converters.pToCp, null));
		TxtReads<V> txtReads = new TxtReads<V>(valueConverter);
		reads = new CommonCachedReads.SameValue<String, URL, String, V>(converters.pToOp, converters.pToCp, txtReads, new RawWrites<String,V>(redisWrite));
	}
}
