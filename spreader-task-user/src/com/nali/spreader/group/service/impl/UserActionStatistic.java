package com.nali.spreader.group.service.impl;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nali.spreader.group.service.IUserActionStatistic;
import com.nali.stat.dc.collecter.SimpleCountCollecter;
import com.nali.stat.dc.data.CountDateQuery;
import com.nali.stat.dc.data.CountResult;
import com.nali.stat.dc.data.DateCount;
import com.nali.stat.dc.data.SimpleCount;
import com.nali.stat.dc.data.SimpleCountQuery;
import com.nali.stat.dc.exception.DataCollectionException;

@Component
public class UserActionStatistic implements IUserActionStatistic{
	
	@Autowired
	private SimpleCountCollecter redisHashSimpleCountCollecter;

	@Override
	public long[] getCount(String taskCode, int limit, Long... uids) throws DataCollectionException {
		String[] uidStrs = com.nali.spreader.util.ArrayUtils.<Long>asStringArray(uids);
		return this.redisHashSimpleCountCollecter.get(taskCode, uidStrs, limit);
	}

	@Override
	public void incr(String taskCode, int value, Long... uids) throws DataCollectionException {
		SimpleCount simpleCount = this.newSimpleCount(taskCode, value, uids);
		this.redisHashSimpleCountCollecter.incr(simpleCount);
	}

	@Override
	public void setValue(String taskCode, int value, Long... uids) throws DataCollectionException {
		SimpleCount simpleCount = this.newSimpleCount(taskCode, value, uids);
		this.redisHashSimpleCountCollecter.set(simpleCount);
	}

	@Override
	public CountResult getCount(String[] taskCodes, int limit, Long... uids) throws DataCollectionException {
		SimpleCountQuery simpleCountQuery = SimpleCountQuery.newQuery();
		for(String taskCode : taskCodes) {
			for(long uid : uids) {
				simpleCountQuery.addQuery(taskCode, String.valueOf(uid), limit);
			}
		}
		return this.redisHashSimpleCountCollecter.get(simpleCountQuery);
	}
		
	private SimpleCount newSimpleCount(String[] taskCodes, int value, Long... uids) {
		SimpleCount simpleCount = SimpleCount.newCount();
		for(String taskCode : taskCodes) {
			for(Long uid : uids) {
				if(uid != null) {
					String uidStr = String.valueOf(uid);
					simpleCount.addCount(taskCode,uidStr, value);
				}
			}
		}
		
		return simpleCount;
	}
	
	private SimpleCount newSimpleCount(String taskCode, int value, Long... uids) {
		String[] taskCodes = new String[] {taskCode};
		return this.newSimpleCount(taskCodes, value, uids);
	}
	
	
	private DateCount getDateCount(String taskCode, int value, Date date, Long... uids) {
		DateCount count = DateCount.newCount();
		for(Long uid : uids) {
			if(uid != null) {
				count.addCount(String.valueOf(uid), taskCode, value, date);
			}
		}
		return count;
	}

	@Override
	public void incr(String taskCode, int value, Date date, Long... uids)
			throws DataCollectionException {
		DateCount count = this.getDateCount(taskCode, value, date, uids);
		this.redisHashSimpleCountCollecter.incr(count);
	}

	@Override
	public void setValue(String taskCode, int value, Date date, Long... uids)
			throws DataCollectionException {
		DateCount count = this.getDateCount(taskCode, value, date, uids);
		this.redisHashSimpleCountCollecter.set(count);
	}

	@Override
	public long[] getCount(String taskCode, int limit, Date fromDate, Date toDate, Long... uids)
			throws DataCollectionException {
		CountDateQuery query = CountDateQuery.newQuery();
		
		for(Long uid : uids) {
			query.addQuery(taskCode, String.valueOf(uid), fromDate, toDate, limit);
		}
		
		CountResult result = this.redisHashSimpleCountCollecter.get(query);
		Map<String, Long> uidMap = result.getResult(taskCode);
		
		long[] values = new long[uids.length];
		for(int i = 0; i < uids.length; i++) {
			Long value = uidMap.get(String.valueOf(uids[i]));
			if(value == null) {
				value = 0L;
			}
			values[i] = value;
		}
		
		return values;
	}
}
