package com.nali.spreader.test.productenv;

import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"239-application-context-test.xml"})
public class GroupUtil {
	@Autowired
	private RedisTemplate<String, Long> redisTemplate;
	
	@Test
	public void testGuser() {
		Set<Long> members = redisTemplate.opsForZSet().range("sp_m_343", 0, -1);
		System.out.println(members);
	}

}
