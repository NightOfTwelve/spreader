package com.nali.spreader.factory.config;

import java.io.IOException;
import java.io.InputStream;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.keyvalue.redis.connection.RedisConnectionFactory;
import org.springframework.data.keyvalue.redis.core.RedisTemplate;
import org.springframework.data.keyvalue.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Repository;

@Repository
public class ConfigStore implements IConfigStore {
	private static Logger logger = Logger.getLogger(ConfigStore.class);
    private static final String CONFIG_INIT_DIR = "configInit/";
	private static final String CONFIG_STORE_KEY = "ConfigStore";
	private RedisTemplate<String, Integer> redisTemplate;
	private ObjectMapper objectMapper = new ObjectMapper();
    
    @Autowired
    public void initRedisTemplate(RedisConnectionFactory connectionFactory) {
    	redisTemplate = new RedisTemplate<String, Integer>(connectionFactory);
    	redisTemplate.setKeySerializer(new StringRedisSerializer());
    	redisTemplate.setHashKeySerializer(new StringRedisSerializer());
    }

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getConfig(String name) {
		return (T) redisTemplate.opsForHash().get(CONFIG_STORE_KEY, name);
	}
	
	@Override
	public synchronized <T> T initConfig(String name, Class<T> clazz) {
		T config = getConfig(name);
		if(config==null) {
			config = initFromFile(name, clazz);
		}
		return config;
	}
	
	private <T> T initFromFile(String name, Class<T> clazz) {
		InputStream in = ConfigStore.class.getClassLoader().getResourceAsStream(CONFIG_INIT_DIR + name);
		if(in==null) {
			try {
				return clazz.newInstance();
			} catch (Exception e) {
				throw new IllegalArgumentException(e);//TODO
			}
		}
		try {
			return objectMapper.readValue(in, clazz);
		} catch (IOException e) {
			throw new IllegalArgumentException(e);//TODO
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				logger.error(e, e);
			}
		}
	}

	@Override
	public <T> void saveConfig(String name, T config) {
		redisTemplate.opsForHash().put(CONFIG_STORE_KEY, name, config);
	}

}
