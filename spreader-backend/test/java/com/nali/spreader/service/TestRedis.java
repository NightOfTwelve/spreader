package com.nali.spreader.service;

import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("220-application-context-test.xml")
public class TestRedis {
	@Autowired
	private RedisTemplate<String, Long> redisTemplate;

	@Test
	public void test() {
		// redisTemplate.delete("list_1");
		// redisTemplate.opsForList().leftPush("list_1", 1L);
		// redisTemplate.opsForList().rightPush("list_1", 2L);
		// redisTemplate.opsForList().rightPush("list_1", 3L);
		// redisTemplate.opsForList().rightPush("list_1", 4L);
		// List<Long> list = redisTemplate.opsForList().range("list_1", 0, 3);
		// boolean re = redisTemplate.opsForZSet().add("testzsets", 7L, 1);
		// Double re2 = redisTemplate.opsForZSet().incrementScore("testzsets",
		// 6L, 1);
		// Set<Long> set = redisTemplate.opsForZSet().range("testzsets", 0L,
		// -1L);
		// Double d2 = redisTemplate.opsForZSet().score("testzsets", 7L);
		// for (int i = 0; i < 5; i++) {
		// Double score = redisTemplate.opsForZSet().score("testzsets", 7L);
		// if(score == null) {
		// score = 0D;
		// }
		// System.out.println("取出的分数：" + score);
		// boolean res = redisTemplate.opsForZSet().add("testzsets", 7L, score +
		// 1);
		// System.out.println("执行结果:" + res);
		// }
		// for (int i = 0; i < 5; i++) {
		// Double score = redisTemplate.opsForZSet().score("testzsets", 8L);
		// if(score == null) {
		// score = 0D;
		// }
		// System.out.println("取出的分数：" + score);
		// Double res = redisTemplate.opsForZSet().incrementScore("testzsets",
		// 7L, 1);
		// System.out.println("执行结果:" + res);
		// }
//		double s = redisTemplate.opsForZSet().incrementScore(
//				"spreader_content_segmen_" + 276L, 235L, 1);
//		System.out.println(s);
		Set<TypedTuple<Long>> set = redisTemplate.opsForZSet().reverseRangeWithScores(
				"spreader_content_segmen_" + 276L, 0, 5);
		for (TypedTuple<Long> t : set) {
			System.out.println("id:" + t.getValue() + ".score:" + t.getScore());
		}
		// System.out.println(re +","+d2);
	}
}
