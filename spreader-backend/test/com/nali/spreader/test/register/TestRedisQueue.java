package com.nali.spreader.test.register;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nali.lwtmq.core.queue.redis.RedisQueue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:application-context-test.xml")
public class TestRedisQueue {
	@Autowired
	private RedisQueue weiboNormalPassiveTaskQueue;
	@Autowired
	private RedisQueue weiboRegisterPassiveTaskQueue;
	@Test
	public void getNormalPassive() {
		System.out.println(weiboNormalPassiveTaskQueue.size());
		Object obj;
		while((obj = weiboRegisterPassiveTaskQueue.pop(1))!=null) {
			System.out.println(obj);
		};
	}
}
