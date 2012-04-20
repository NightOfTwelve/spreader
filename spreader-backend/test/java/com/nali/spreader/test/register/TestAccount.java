package com.nali.spreader.test.register;

import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nali.spreader.dao.ICrudRobotRegisterDao;
import com.nali.spreader.data.RobotRegister;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:application-context-test.xml")
public class TestAccount {
	@Autowired
	RedisConnectionFactory connectionFactory;
	@Autowired
	ICrudRobotRegisterDao crudRobotRegisterDao;
	@Test
	public void getAccont() {
    	RedisTemplate<String, Integer> redisTemplate = new RedisTemplate<String, Integer>();
    	redisTemplate.setConnectionFactory(connectionFactory);
    	redisTemplate.setKeySerializer(new StringRedisSerializer());
    	Map<Object, Object> entries = redisTemplate.opsForHash().entries("RegisteringAccount_1");
    	Set<Entry<Object, Object>> entrySet = entries.entrySet();
    	ArrayList<String> list = new ArrayList<String>();
    	for (Entry<Object, Object> entry : entrySet) {
    		RobotRegister robotRegister = crudRobotRegisterDao.selectByPrimaryKey((Long) entry.getKey());
			list.add(robotRegister.getEmail() + "\t" + robotRegister.getPwd() + "\t" + entry.getValue());
		}
    	for (String string : list) {
			System.out.println(string);
		}
	}
}
