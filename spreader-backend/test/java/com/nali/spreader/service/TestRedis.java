package com.nali.spreader.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("220-application-context-test.xml")
public class TestRedis {
	@Autowired
	private RedisTemplate<String, Long> redisTemplate;
	
	@Test
	public void test() {
		redisTemplate.delete("list_1");
		redisTemplate.opsForList().leftPush("list_1", 1L);
		redisTemplate.opsForList().rightPush("list_1", 2L);
		redisTemplate.opsForList().rightPush("list_1", 3L);
		redisTemplate.opsForList().rightPush("list_1", 4L);
		List<Long> list = redisTemplate.opsForList().range("list_1", 0, 3);
		System.out.println(list);
	}
}
