package com.nali.spreader.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.keyvalue.redis.connection.RedisConnectionFactory;
import org.springframework.data.keyvalue.redis.core.RedisTemplate;
import org.springframework.data.keyvalue.redis.serializer.StringRedisSerializer;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.nali.spreader.dao.IRobotRegisterDao;
import com.nali.spreader.data.RobotRegister;

@Repository
public class RobotRegisterDao implements IRobotRegisterDao {
    private static final String REGISTERING_ACCOUNT_KEY_PREFIX = "RegisteringAccount_";
	@Autowired
    private SqlMapClientTemplate sqlMap;
    private RedisTemplate<String, Integer> redisTemplate;
    
    @Autowired
    public void initRedisTemplate(RedisConnectionFactory connectionFactory) {
    	redisTemplate = new RedisTemplate<String, Integer>(connectionFactory);//TODO stringSerializer
    	redisTemplate.setKeySerializer(new StringRedisSerializer());
    }
    
	@Override
	public Long saveRobotRegister(RobotRegister robotRegister) {
		return (Long) sqlMap.insert("spreader_register.insertRobotRegister", robotRegister);
	}

	@Override
	public Integer countRegisteringAccount(Integer websiteId) {
		return redisTemplate.opsForHash().size(registeringAccountKey(websiteId)).intValue();
	}
	
	@Override
	public void addRegisteringAccount(Integer websiteId, Long registerId, String nickname) {
		redisTemplate.opsForHash().put(registeringAccountKey(websiteId), registerId, nickname);
	}
	
	@Override
	public void removeRegisteringAccount(Integer websiteId, Long registerId) {
		redisTemplate.opsForHash().delete(registeringAccountKey(websiteId), registerId);
	}

	private String registeringAccountKey(Integer websiteId) {
		return REGISTERING_ACCOUNT_KEY_PREFIX + websiteId;
	}

	@Override
	public String getRegisteringAccount(Integer websiteId, Long registerId) {
		return (String) redisTemplate.opsForHash().get(registeringAccountKey(websiteId), registerId);
	}

}
