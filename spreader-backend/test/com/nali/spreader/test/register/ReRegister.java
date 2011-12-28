package com.nali.spreader.test.register;

import java.util.HashSet;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.keyvalue.redis.connection.RedisConnection;
import org.springframework.data.keyvalue.redis.core.RedisCallback;
import org.springframework.data.keyvalue.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nali.spreader.dao.ICrudRobotRegisterDao;
import com.nali.spreader.dao.ICrudRobotUserDao;
import com.nali.spreader.data.RobotRegister;
import com.nali.spreader.data.RobotRegisterExample;
import com.nali.spreader.factory.TaskProduceLine;
import com.nali.spreader.factory.passive.AutowireProductLine;
import com.nali.spreader.model.RobotUser;
import com.nali.spreader.model.RobotUserExample;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:application-context-test.xml")
public class ReRegister {//重新注册帐号
	@Autowired
	private ICrudRobotRegisterDao crudRobotRegisterDao;
	@Autowired
	private ICrudRobotUserDao crudRobotUserDao;
	@AutowireProductLine
	private TaskProduceLine<Long> registerWeiboAccount;
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	@Test
	public void test() {
		redisTemplate.execute(new RedisCallback<Boolean>() {
			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				connection.flushDb();
				return true;
			}
		});
		RobotUserExample example2 = new RobotUserExample();
		List<RobotUser> list2 = crudRobotUserDao.selectByExample(example2);
		HashSet<Long> existIds = new HashSet<Long>();
		for (RobotUser robotUser : list2) {
			Long robotRegisterId = robotUser.getRobotRegisterId();
			if(robotRegisterId!=null) {
				existIds.add(robotRegisterId);
			}
		}
		
		RobotRegisterExample example = new RobotRegisterExample();
		example.createCriteria().andEmailIsNotNull();
		List<RobotRegister> list = crudRobotRegisterDao.selectByExample(example);
		for (RobotRegister robotRegister : list) {
			Long robotRegisterId = robotRegister.getId();
			if(!existIds.contains(robotRegisterId)) {
				registerWeiboAccount.send(robotRegisterId);
			}
		}
	}

}
