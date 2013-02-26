package com.nali.spreader.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.nali.spreader.dao.IActionCooldownConfigDao;
import com.nali.spreader.util.data.BaseConverters;
import com.nali.spreader.util.data.Read;
import com.nali.spreader.util.data.Write;
import com.nali.spreader.util.data.impl.TxtToRedisRead;

@Repository
public class ActionCooldownConfigDao implements IActionCooldownConfigDao {
	private static final String DEFAULT = "default";
	private Read<String, List<Double>> rateRead;
	private Write<String, List<Double>> rateWrite;
	private Read<String, Integer> maxRead;
	private Write<String, Integer> maxWrite;

	@Autowired
	public void init(RedisTemplate<String, Object> redisTemplate) {
		TxtToRedisRead<Double> rateReads = new TxtToRedisRead<Double>(
				"front#clientconfig#cooldown#rate#", "txt/cooldown_rate",
				redisTemplate, BaseConverters.stringDouble);
		TxtToRedisRead<Integer> maxReads = new TxtToRedisRead<Integer>(
				"front#clientconfig#cooldown#max#", "txt/cooldown_max",
				redisTemplate, BaseConverters.stringInteger);
		rateRead = rateReads.reads.readList();
		rateWrite = rateReads.rawRedisWrites.writeList();
		maxRead = maxReads.reads.readValue();
		maxWrite = maxReads.rawRedisWrites.writeValue();
	}

	@Override
	public List<Double> getDownloadRate(int id) {
		return rateRead.read(DEFAULT);
	}
	
	@Override
	public void saveDownloadRate(int id, List<Double> rates) {
		rateWrite.write(DEFAULT, rates);
	}

	@Override
	public int getMaxDownloadPerHour(int id) {
		return maxRead.read(DEFAULT);
	}

	@Override
	public void saveMaxDownloadPerHour(int id, int count) {
		maxWrite.write(DEFAULT, count);
	}
	
}

