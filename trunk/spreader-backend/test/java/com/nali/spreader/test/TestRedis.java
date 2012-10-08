package com.nali.spreader.test;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:application-context-dao.xml")
public class TestRedis {
	@Autowired
	private RedisTemplate<String, Date> redisTemplate;
	
	public Date getTestTime(Integer id,Date d){
		Assert.notNull(id,"id is not null");
		Assert.notNull(d,"date is not null");
		return this.redisTemplate.opsForValue().getAndSet("Test_Time_"+id, d);
	}
	@Test
	public void test(){

	}
}
