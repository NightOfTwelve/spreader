package com.nali.spreader.test;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import org.apache.commons.beanutils.BeanUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import com.nali.spreader.model.RegularJob;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:application-context-dao.xml")
public class TestRedis {
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	public Date getTestTime(Integer id,Date d){
		Assert.notNull(id,"id is not null");
		Assert.notNull(d,"date is not null");
		return (Date) this.redisTemplate.opsForValue().getAndSet("Test_Time_"+id, d);
	}
	@Test
	public void test(){
		Long x = (Long) redisTemplate.opsForValue().getAndSet("spreader_user_spider_last_content_id"+788L, 799L);
		System.out.println("id>>"+x);
	}
	
	public static void main(String[] args) {
		try {
			System.out.println(BeanUtils.describe(new RegularJob()));
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
