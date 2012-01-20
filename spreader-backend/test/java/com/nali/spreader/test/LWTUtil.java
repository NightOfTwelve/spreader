package com.nali.spreader.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nali.lwtmq.core.queue.redis.RedisQueue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:application-context-test.xml")
public class LWTUtil {
	@Autowired
	RedisQueue taskQueue;
	@Autowired
	RedisQueue weiboNormalPassiveTaskQueue;

	@Test
	public void getAccont() {
		System.out.println(taskQueue.size());
		System.out.println(weiboNormalPassiveTaskQueue.size());
	}
	
	@Test
	public void cleanPassive101() {//去掉注释清除，清完再加回注释（避免误操作）
		//cleanQueue(weiboNormalPassiveTaskQueue);
	}
	
	void cleanQueue(RedisQueue queue) {
		while(queue.size() > 0) {
			queue.pop();
		}
	}
}
