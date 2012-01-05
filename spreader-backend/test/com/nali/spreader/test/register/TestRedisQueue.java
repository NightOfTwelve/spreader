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
	private RedisQueue weiboNormalPassiveTaskQueue;//微博普通任务队列:
	@Autowired
	private RedisQueue weiboRegisterPassiveTaskQueue;//微博注册任务队列:
	@Autowired
	private RedisQueue weiboInstantPassiveTaskQueue;//微博实时任务队列:
	@Test
	public void getNormalPassive() {
		Object obj;
		while((obj = weiboNormalPassiveTaskQueue.pop(1))!=null) {
		};
		while((obj = weiboRegisterPassiveTaskQueue.pop(1))!=null) {
		};
		while((obj = weiboInstantPassiveTaskQueue.pop(1))!=null) {
		};
	}
}
