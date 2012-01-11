package com.nali.spreader.workshop;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.keyvalue.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.nali.common.model.Limit;
import com.nali.spreader.dao.ICrudRobotRegisterDao;
import com.nali.spreader.dao.ICrudRobotUserDao;
import com.nali.spreader.data.RobotRegister;
import com.nali.spreader.data.RobotRegisterExample;
import com.nali.spreader.factory.TaskProduceLine;
import com.nali.spreader.factory.passive.AutowireProductLine;
import com.nali.spreader.factory.passive.PassiveAnalyzer;
import com.nali.spreader.model.RobotUser;
import com.nali.spreader.model.RobotUserExample;
import com.nali.spreader.util.SpecialDateUtil;

@Component
public class ReRegisterPassive implements PassiveAnalyzer<Object> {//TODO temp code
	@Autowired
	private ICrudRobotRegisterDao crudRobotRegisterDao;
	@Autowired
	private ICrudRobotUserDao crudRobotUserDao;
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	@AutowireProductLine
	private TaskProduceLine<Long> registerWeiboAccount;

	@Override
	public void work(Object data) {
		final int batchSize=5000;
		HashSet<Long> existIds = new HashSet<Long>();
		
		int lastCount=0;
		while (true) {
			RobotUserExample example = new RobotUserExample();
			example.setLimit(Limit.newInstanceForLimit(lastCount, batchSize));
			List<RobotUser> list = crudRobotUserDao.selectByExample(example);
			for (RobotUser robotUser : list) {
				Long robotRegisterId = robotUser.getRobotRegisterId();
				if(robotRegisterId!=null) {
					existIds.add(robotRegisterId);
				}
			}
			if(list.size()<batchSize) {
				break;
			} else {
				lastCount+=batchSize;
			}
		}
		
    	Map<Object, Object> entries = redisTemplate.opsForHash().entries("RegisteringAccount_1");
    	Set<Entry<Object, Object>> entrySet = entries.entrySet();
    	for (Entry<Object, Object> entry : entrySet) {
    		Long id = (Long) entry.getKey();
    		existIds.add(id);
    	}

		lastCount=0;
		while (true) {
			RobotRegisterExample example = new RobotRegisterExample();
			example.setLimit(Limit.newInstanceForLimit(lastCount, batchSize));
			example.createCriteria().andEmailIsNotNull().andUpdateTimeLessThan(SpecialDateUtil.afterToday(-1, false));
			List<RobotRegister> list = crudRobotRegisterDao.selectByExample(example);
			for (RobotRegister robotRegister : list) {
				Long robotRegisterId = robotRegister.getId();
				if(!existIds.contains(robotRegisterId)) {
					registerWeiboAccount.send(robotRegisterId);
				}
			}
			if(list.size()<batchSize) {
				break;
			} else {
				lastCount+=batchSize;
			}
		}
	}

}
