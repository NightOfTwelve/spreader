package com.nali.spreader.dao.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.nali.spreader.dao.IAppRegisterDao;
import com.nali.spreader.util.KeyValuePair;

@Repository
public class AppRegisterDao implements IAppRegisterDao {
    private static final String APP_REGISTER_KEY = "AppRegisterEndPoint";
	@Autowired
    private RedisTemplate<String, long[]> redisTemplate;

    @Override
    public KeyValuePair<Date, Long> getLastEndPoint() {
    	long[] longs = redisTemplate.opsForValue().get(APP_REGISTER_KEY);
    	if(longs==null) {
    		return null;
    	} else {
    		return new KeyValuePair<Date, Long>(new Date(longs[0]), longs[1]);
    	}
    }

    @Override
    public void saveEndPoint(Date date, Long id) {
    	long[] longs = new long[] {date.getTime(), id};
    	redisTemplate.opsForValue().set(APP_REGISTER_KEY, longs);
    }
}
