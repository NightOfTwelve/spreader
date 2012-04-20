/*
 *The code is written by NaLi, All rights reserved.
 */
package com.nali.stat.dc.collecter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

import com.nali.common.util.CollectionUtils;
import com.nali.common.util.StringUtil;
import com.nali.stat.dc.data.CountQuery;
import com.nali.stat.dc.data.CountResult;
import com.nali.stat.dc.data.CountUnit;
import com.nali.stat.dc.data.DateCount;
import com.nali.stat.dc.data.DateCountUnit;
import com.nali.stat.dc.data.QueryDateUnit;
import com.nali.stat.dc.data.QueryUnit;
import com.nali.stat.dc.data.Record;
import com.nali.stat.dc.data.SimpleCount;
import com.nali.stat.dc.exception.DataCollectionException;
import com.nali.stat.dc.key.CountCollecterKeyBuilder;
import com.nali.stat.dc.key.KeyBuilder;
import com.nali.stat.dc.util.TimeUnit;

/**
 * @author gavin Created on 2010-12-13
 */
public class RedisHashSimpleCountCollecter implements SimpleCountCollecter,
		InitializingBean {
	@Autowired
	private RedisTemplate<String, Object> collecterTemplate;

	private String moduleName;

	private TimeUnit timeUnitEnum;

	private KeyBuilder simpleCountKeyBuilder;

	private HashOperations<String, String, String> hashOperations;

	private static final Logger logger = Logger
			.getLogger(RedisHashSimpleCountCollecter.class);

	private static final String COUNT_KEY = "count";

	private Long[] EMTPY_LONG_ARR = new Long[] {};

	public RedisHashSimpleCountCollecter(
			RedisTemplate<String, Object> redisTemplate, String moduleName,
			TimeUnit timeUnit) {
		this.moduleName = moduleName;
		this.timeUnitEnum = timeUnit;
		this.collecterTemplate = redisTemplate;
	}

	public RedisHashSimpleCountCollecter(RedisTemplate<String, Object> redisTemplate, String moduleName) {
		this(redisTemplate, moduleName, TimeUnit.DAY);
	}

	public RedisHashSimpleCountCollecter() {

	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public TimeUnit getTimeUnit() {
		return this.timeUnitEnum;
	}

	public void setTimeUnit(String timeUnit) {
		this.timeUnitEnum = TimeUnit.getTimeUnit(timeUnit);
	}
	
	private String toString(Long value) {
		if(value != null) {
			return String.valueOf(value);
		}
		return null;
	}
	
	private Long toValue(String value) {
		if(value != null) {
			return Long.valueOf(value);
		}
		return null;
	}

	@Override
	public long decr(String name, String id, int count)
			throws DataCollectionException {
		this.logEntryWithCount("DESC", name, id, count);
		this.checkParameter(name, id);
		this.checkIncrOrDecrCount(count);
		String key = this.simpleCountKeyBuilder.buildKey(this.moduleName,
				this.timeUnitEnum, id);
		long result = this.hashOperations.increment(key, name, -count);
		if (result < 0) {
			DataCollectionException ex = new DataCollectionException(
					DataCollectionException.NO_SERVICE_ERROR_OR_NOT_AVAILABLE
							+ this.getExceptionString(name, id));
			logger.error(ex.getMessage(), ex);
			throw ex;
		}
		this.logExit("DESC", name, id, result);
		return result;
	}

	@Override
	public long get(String name, String id) throws DataCollectionException {
		this.logEntry("Get", name, id);
		this.checkParameter(name, id);
		String key = this.simpleCountKeyBuilder.buildKey(this.moduleName,
				this.timeUnitEnum, id);
		Long value = this.toValue(this.hashOperations.get(key, name));
		// if (value == null) {
		// // throw new
		// DataCollectionException(DataCollectionException.NO_SERVICE_ERROR_OR_NOT_AVAILABLE
		// + this.getExceptionString(name, id));
		// return 0;
		// }
		this.logExit("Get", name, id, value);
		return value == null ? 0 : value.longValue();
	}

	@Override
	public long get(String name, String id, int limit)
			throws DataCollectionException {
		this.logReadEntryWithLimit(name, id, limit);
		this.checkParameter(name, id);
		this.checkLimit(limit);
		String[] keys = this.simpleCountKeyBuilder.buildKeys(moduleName,
				this.timeUnitEnum, id, limit);
		List<Long> values = new ArrayList<Long>(keys.length);
		for (String key : keys) {
			Long value = (Long) this.toValue(this.hashOperations.get(key, name));
			values.add(value);
		}
		long sum = this.getSum(values);
		this.logExit("Get", name, id, sum);
		return sum;
	}

	@Override
	public long decr(String name, String id) throws DataCollectionException {
		return this.decr(name, id, 1);
	}

	@Override
	public void decr(DateCount count) throws DataCollectionException {
		this.opByDateCount(count, DECR_DATE_CALLBACK, null);
	}

	@Override
	public long incr(String name, String id, int count)
			throws DataCollectionException {
		this.logEntryWithCount("Incr", name, id, count);
		this.checkParameter(name, id);
		this.checkIncrOrDecrCount(count);
		String key = this.simpleCountKeyBuilder.buildKey(this.moduleName,
				this.timeUnitEnum, id);
		long result = this.hashOperations.increment(key, name, count);
		if (result < 0) {
			DataCollectionException ex = new DataCollectionException(
					DataCollectionException.NO_SERVICE_ERROR_OR_NOT_AVAILABLE
							+ this.getExceptionString(name, id));
			logger.error(ex.getMessage(), ex);
			throw ex;
		}
		this.logExit("Incr", name, id, result);
		return result;
	}

	@Override
	public void incr(DateCount dateCount) throws DataCollectionException {
		this.checkDateCount(dateCount);
		this.opByDateCount(dateCount, INCR_DATE_CALLBACK, null);
	}

	@Override
	public long incr(String name, String id) throws DataCollectionException {
		return this.incr(name, id, 1);
	}

	@Override
	public CountResult decr(SimpleCount count) throws DataCollectionException {
		this.checkCount(count);
		CountResult countResult = new CountResult();
		this.opByCount(count, DECR_CALLBACK, countResult);
		return countResult;
	}

	@Override
	public CountResult incr(SimpleCount count) throws DataCollectionException {
		this.checkCount(count);
		CountResult countResult = new CountResult();
		this.opByCount(count, INCR_CALLBACK, countResult);
		return countResult;
	}

	@Override
	public void set(DateCount dateCount) throws DataCollectionException {
		this.checkDateCount(dateCount);
		Map<String, HashPair> valueMap = CollectionUtils.newHashMap(dateCount
				.getCountsSize());
		this.opByDateCount(dateCount, SET_DATE_CALLBACK, valueMap);
		this.mset(valueMap);
	}

	@Override
	public void set(SimpleCount count) throws DataCollectionException {
		this.checkCount(count);
		Map<String, HashPair> valueMap = new HashMap<String, HashPair>(
				count.getCountsSize());
		this.opByCount(count, SET_CALLBACK, valueMap);
		this.mset(valueMap);
	}

	private void mset(final Map<String, HashPair> valueMap) {
		this.collecterTemplate.execute(new RedisCallback<Object>() {

			@Override
			public Object doInRedis(RedisConnection connection)
					throws DataAccessException {
				for (Entry<String, HashPair> valueEntry : valueMap.entrySet()) {
					String key = valueEntry.getKey();
					String hkey = valueEntry.getValue().getName();
					long value = valueEntry.getValue().getValue();
					hashOperations.put(key, hkey, String.valueOf(value));
				}
				return null;
			}
		});
	}
	
	@Override
	public void set(String name, String id, long value)
			throws DataCollectionException {
		this.logEntryWithCount("Set", name, id, value);
		this.checkParameter(name, id);
		this.checkSetCount(value);
		String key = this.simpleCountKeyBuilder.buildKey(this.moduleName,
				this.timeUnitEnum, id);
		this.hashOperations.put(key, name, this.toString(value));
	}

	@Override
	public boolean del(String name, String id) throws DataCollectionException {
		this.logEntry("Del", name, id);
		this.checkParameter(name, id);
		String key = this.simpleCountKeyBuilder.buildKey(this.moduleName,
				this.timeUnitEnum, id);
		this.hashOperations.delete(key, name);

		Set<String> keys = this.hashOperations.keys(key);
		if (CollectionUtils.isEmpty(keys)) {
			this.collecterTemplate.delete(key);
		}
		return true;
	}

	@Override
	public void set(String name, String id, Record record)
			throws DataCollectionException {
		String strValue = record.getRecord(COUNT_KEY);
		try {
			int count = Integer.parseInt(strValue);
			this.set(name, id, count);
		} catch (NumberFormatException e) {
			throw new DataCollectionException(
					DataCollectionException.ILLEGAL_COUNT);
		}
	}

	@Override
	public <T extends QueryUnit> CountResult get(CountQuery<T> query)
			throws DataCollectionException {
		if (logger.isDebugEnabled()) {
			logger.debug("Get with query: " + query);
		}
		Map<String, List<T>> queries = query.getQueries();
		Map<String, Map<String, List<String>>> allTypeKeys = this
				.getAllQueryKeys(queries);
		CountResult countResult = new CountResult();
		for (Entry<String, Map<String, List<String>>> nameKeysEntry : allTypeKeys
				.entrySet()) {
			String name = nameKeysEntry.getKey();
			Map<String, List<String>> idKeys = nameKeysEntry.getValue();

			for (Entry<String, List<String>> idKeysEntry : idKeys.entrySet()) {
				String id = idKeysEntry.getKey();
				List<String> limitKeyList = idKeysEntry.getValue();
				List<Long> limits = new ArrayList<Long>(limitKeyList.size());
				for (String key : limitKeyList) {
					Long value = (Long) this.toValue(this.hashOperations.get(key, name));
					limits.add(value);
				}
				long limitSum = this.getSum(limits);
				countResult.addResult(name, id, limitSum);
			}
		}
		if (logger.isDebugEnabled()) {
			logger.debug("Get result: " + countResult);
		}
		return countResult;
	}

	public long incr(String name, String id, int count, Date date)
			throws DataCollectionException {
		this.checkParameter(name, id);
		this.checkIncrOrDecrCount(count);
		this.checkDate(date);
		String key = this.simpleCountKeyBuilder.buildKey(this.moduleName,
				this.timeUnitEnum, id, date);
		long result = this.hashOperations.increment(key, name, count);
		if (result < 0) {
			DataCollectionException ex = new DataCollectionException(
					DataCollectionException.NO_SERVICE_ERROR_OR_NOT_AVAILABLE
							+ this.getExceptionString(name, id));
			logger.error(ex.getMessage(), ex);
			throw ex;
		}
		return result;
	}

	@Override
	public <T extends QueryUnit> void del(CountQuery<T> query)
			throws DataCollectionException {
		Map<String, List<T>> queries = query.getQueries();
		final Map<String, Map<String, List<String>>> allTypeKeys = this
				.getAllQueryKeys(queries);
		this.collecterTemplate.execute(new RedisCallback<Object>() {

			@Override
			public Object doInRedis(RedisConnection connection)
					throws DataAccessException {
				for (Entry<String, Map<String, List<String>>> nameKeysEntry : allTypeKeys
						.entrySet()) {
					String name = nameKeysEntry.getKey();
					Map<String, List<String>> idKeys = nameKeysEntry.getValue();
					for (Entry<String, List<String>> idKeysEntry : idKeys
							.entrySet()) {
						List<String> limitKeyList = idKeysEntry.getValue();
						for (String key : limitKeyList) {
							RedisHashSimpleCountCollecter.this.hashOperations
									.delete(key, name);
						}
					}
				}
				return null;
			}

		});
	}

	@Override
	public long[] get(String[] names, String id, int limit)
			throws DataCollectionException {
		this.checkArray(names, "Name list is null or emty!");
		this.checkLimit(limit);
		this.checkId(id);

		String[] keys = this.simpleCountKeyBuilder.buildKeys(this.moduleName,
				this.timeUnitEnum, id, limit);
		long[] sums = new long[keys.length];
		for (String key : keys) {
			Collection<String> valueCol = this.hashOperations.multiGet(key,
					Arrays.asList(names));
			Iterator<String> iterator = valueCol.iterator();
			int i = 0;
			while (iterator.hasNext()) {
				String valueLong = iterator.next();
				long value = valueLong == null ? 0 : this.toValue(valueLong);
				sums[i++] += value;
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("Get service names: " + ArrayUtils.toString(names)
					+ " ,id: " + id + ", limit: " + limit + " , result: "
					+ ArrayUtils.toString(sums));
		}
		return sums;
	}

	@Override
	public long[] get(String[] names, String id) throws DataCollectionException {
		this.checkArray(names, "Name list is null or emty!");
		this.checkId(id);
		String key = this.simpleCountKeyBuilder.buildKey(this.moduleName,
				this.timeUnitEnum, id);
		Collection<String> values = this.hashOperations.multiGet(key,
				Arrays.asList(names));
		long[] sumResults = this.convertStringCollectionToArray(values);

		if (logger.isDebugEnabled()) {
			logger.debug("Get service names: " + ArrayUtils.toString(names)
					+ " ,id: " + id + " , result: "
					+ ArrayUtils.toString(sumResults));
		}

		return sumResults;
	}

	@Override
	public long[] get(String name, String[] ids) throws DataCollectionException {
		this.checkArray(ids, "id array is null or emty!");
		this.checkName(name);
		int size = ids.length;
		long[] results = new long[size];
		for (int i = 0; i < size; i++) {
			String key = this.simpleCountKeyBuilder.buildKey(this.moduleName,
					this.timeUnitEnum, ids[i]);
			Long valueLong = this.toValue(this.hashOperations.get(key, name));
			results[i] = valueLong == null ? 0 : valueLong.longValue();
		}

		if (logger.isDebugEnabled()) {
			logger.debug("Get service name: " + name + " ,ids: "
					+ ArrayUtils.toString(ids) + " , result: "
					+ ArrayUtils.toString(results));
		}

		return results;
	}

	@Override
	public long[] get(String name, String[] ids, int limit)
			throws DataCollectionException {
		this.checkArray(ids, "id array is null or emty!");
		this.checkName(name);
		this.checkLimit(limit);

		int length = ids.length;
		long[] results = new long[length];
		for (int i = 0; i < length; i++) {
			String[] keys = this.simpleCountKeyBuilder.buildKeys(
					this.moduleName, this.timeUnitEnum, ids[i], limit);
			List<Long> valueSums = new ArrayList<Long>(keys.length);
			for (String key : keys) {
				Long valueLong = this.toValue(this.hashOperations.get(key, name));
				if(valueLong != null) {
					valueSums.add(valueLong);
				}
			}
			long valueSum = this.getSum(valueSums);
			results[i] = valueSum;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("Get service name: " + name + " ,ids: "
					+ ArrayUtils.toString(ids) + " ,limit: " + limit
					+ " , result: " + ArrayUtils.toString(results));
		}
		return results;
	}

	@Override
	public long decr(String name, String id, int count, Date date)
			throws DataCollectionException {
		this.checkParameter(name, id);
		this.checkIncrOrDecrCount(count);
		this.checkDate(date);
		String key = this.simpleCountKeyBuilder.buildKey(this.moduleName,
				this.timeUnitEnum, id, date);
		long result = this.hashOperations.increment(key, name, -count);
		if (result < 0) {
			DataCollectionException ex = new DataCollectionException(
					DataCollectionException.NO_SERVICE_ERROR_OR_NOT_AVAILABLE
							+ this.getExceptionString(name, id));
			logger.error(ex.getMessage(), ex);
			throw ex;
		}
		return result;
	}

	@Override
	public void reset(final String name, String id, int limit)
			throws DataCollectionException {
		this.checkParameter(name, id);
		final String[] keys = this.simpleCountKeyBuilder.buildKeys(
				this.moduleName, this.timeUnitEnum, id, limit);
		this.collecterTemplate.execute(new RedisCallback<Object>() {
			@Override
			public Object doInRedis(RedisConnection connection)
					throws DataAccessException {
				for (String key : keys)
					RedisHashSimpleCountCollecter.this.hashOperations.put(key,
							name, String.valueOf(0L));
				return null;
			}
		});
	}

	@Override
	public void set(String name, String id, long value, Date date)
			throws DataCollectionException {
		this.checkParameter(name, id);
		this.checkDate(date);
		this.checkSetCount(value);
		String key = this.simpleCountKeyBuilder.buildKey(this.moduleName,
				this.timeUnitEnum, id, date);
		this.hashOperations.put(key, name, String.valueOf(value));
	}

	private static interface DateOpCallback {
		void op(String name, String id, int value, Date date, Object context)
				throws DataCollectionException;
	};

	private static interface OpCallback {
		void op(String name, String id, int value, Object context)
				throws DataCollectionException;
	};

	private final DateOpCallback DECR_DATE_CALLBACK = new DateOpCallback() {
		@Override
		public void op(String name, String id, int value, Date date,
				Object context) throws DataCollectionException {
			if (date == null) {
				decr(name, id, value);
			} else {
				decr(name, id, value, date);
			}
		}
	};

	private final DateOpCallback INCR_DATE_CALLBACK = new DateOpCallback() {
		@Override
		public void op(String name, String id, int value, Date date,
				Object context) throws DataCollectionException {
			if (date == null) {
				incr(name, id, value);
			} else {
				incr(name, id, value, date);
			}

		}
	};

	private final OpCallback DECR_CALLBACK = new OpCallback() {
		@Override
		public void op(String name, String id, int value, Object context)
				throws DataCollectionException {
			CountResult countResult = (CountResult) context;
			long rtnVal = decr(name, id, value);
			countResult.addResult(name, id, rtnVal);
		}
	};

	private final OpCallback INCR_CALLBACK = new OpCallback() {
		@Override
		public void op(String name, String id, int value, Object context)
				throws DataCollectionException {
			CountResult countResult = (CountResult) context;
			long rtnVal = incr(name, id, value);
			countResult.addResult(name, id, rtnVal);
		}
	};

	private final DateOpCallback SET_DATE_CALLBACK = new DateOpCallback() {

		@SuppressWarnings("unchecked")
		@Override
		public void op(String name, String id, int value, Date date,
				Object context) throws DataCollectionException {
			setFlow(name, id, value, date, context);
		}
	};

	private final OpCallback SET_CALLBACK = new OpCallback() {
		@SuppressWarnings("unchecked")
		@Override
		public void op(String name, String id, int value, Object context)
				throws DataCollectionException {
			setFlow(name, id, value, null, context);
		}
	};

	@SuppressWarnings("unchecked")
	private void setFlow(String name, String id, int value, Date date,
			Object context) throws DataCollectionException {
		checkName(name);
		checkSetCount(value);
		Map<String, HashPair> valueMap = (Map<String, HashPair>) context;
		String key = null;
		if (date == null) {
			key = simpleCountKeyBuilder.buildKey(this.moduleName,
					this.timeUnitEnum, id);
		} else {
			key = simpleCountKeyBuilder.buildKey(this.moduleName,
					this.timeUnitEnum, id, date);
		}
		valueMap.put(key, new HashPair(name, Long.valueOf(value)));
	}

	private void opByDateCount(DateCount count, DateOpCallback callback,
			Object context) throws DataCollectionException {
		Map<String, List<DateCountUnit>> counts = count.getCounts();
		for (Entry<String, List<DateCountUnit>> countUnitEntry : counts
				.entrySet()) {
			String name = countUnitEntry.getKey();
			List<DateCountUnit> countUnits = countUnitEntry.getValue();
			for (DateCountUnit countUnit : countUnits) {
				String id = countUnit.getId();
				Date date = countUnit.getDate();
				callback.op(name, id, countUnit.getCount(), date, context);
				// TODO 失败处理
			}
		}
	}

	private void opByCount(SimpleCount count, OpCallback opCallback,
			Object context) throws DataCollectionException {
		Map<String, List<CountUnit>> counts = count.getCounts();
		for (Entry<String, List<CountUnit>> countUnitEntry : counts.entrySet()) {
			String name = countUnitEntry.getKey();
			List<CountUnit> countUnits = countUnitEntry.getValue();
			for (CountUnit countUnit : countUnits) {
				String id = countUnit.getId();
				opCallback.op(name, id, countUnit.getCount(), context);
				// TODO 失败处理
			}
		}
	}

	private void checkName(String name) throws DataCollectionException {
		if (StringUtil.isEmpty(name)) {
			throw new DataCollectionException(
					DataCollectionException.ILLEGAL_SERVICE_NAME
							+ " , service name is null or empty");
		}
	}

	private void checkArray(Object[] array, String errorMsg)
			throws DataCollectionException {
		if (ArrayUtils.isEmpty(array)) {
			throw new DataCollectionException(errorMsg);
		}
	}

	private void checkId(String id) throws DataCollectionException {
		if (StringUtil.isEmpty(id)) {
			throw new DataCollectionException(
					DataCollectionException.ILLEGAL_ID
							+ " , id is null or empty");
		}
	}

	private void checkParameter(String name, String id)
			throws DataCollectionException {
		this.checkName(name);
		this.checkId(id);
	}

	private void checkIncrOrDecrCount(int count) throws DataCollectionException {
		if (count <= 0) {
			throw new DataCollectionException(
					DataCollectionException.ILLEGAL_COUNT
							+ " , count is less than 1");
		}
	}

	private void checkDateCount(DateCount dateCount)
			throws DataCollectionException {
		if (dateCount == null) {
			throw new DataCollectionException(
					DataCollectionException.ILLEGAL_COUNT + " , count is null");
		}
	}

	private void checkCount(SimpleCount count) throws DataCollectionException {
		if (count == null) {
			throw new DataCollectionException(
					DataCollectionException.ILLEGAL_COUNT + " , count is null");
		}
	}

	private void checkSetCount(long count) throws DataCollectionException {
		if (count < 0 || count > Long.MAX_VALUE) {
			throw new DataCollectionException(
					DataCollectionException.ILLEGAL_COUNT
							+ " count is less than 0 or larger than long.max_value");
		}
	}

	private <T extends QueryUnit> Map<String, Map<String, List<String>>> getAllQueryKeys(
			Map<String, List<T>> queries) throws DataCollectionException {
		// 第一层name，第二层id
		Map<String, Map<String, List<String>>> allTypeKeys = new HashMap<String, Map<String, List<String>>>();
		for (Entry<String, List<T>> countUnitEntry : queries.entrySet()) {
			String name = countUnitEntry.getKey();
			this.checkName(name);
			List<T> queryUnits = countUnitEntry.getValue();
			for (QueryUnit queryUnit : queryUnits) {
				String id = queryUnit.getId();
				this.checkId(id);
				String[] keys = null;
				if (queryUnit instanceof QueryDateUnit) {
					QueryDateUnit queryDateUnit = (QueryDateUnit) queryUnit;
					keys = this.simpleCountKeyBuilder
							.buildKeys(this.moduleName, this.timeUnitEnum, id,
									queryDateUnit.getFromDate(),
									queryDateUnit.getToDate(),
									queryDateUnit.getLimit());
				} else {
					keys = this.simpleCountKeyBuilder.buildKeys(
							this.moduleName, this.timeUnitEnum, id,
							queryUnit.getLimit());
				}

				Map<String, List<String>> nameKeyIdMap = allTypeKeys.get(name);
				if (nameKeyIdMap == null) {
					nameKeyIdMap = new HashMap<String, List<String>>();
					allTypeKeys.put(name, nameKeyIdMap);
				}
				nameKeyIdMap.put(id, Arrays.asList(keys));
			}
		}
		return allTypeKeys;
	}

	private long getSum(List<Long> values) {
		long sum = 0;
		for (Long value : values) {
			if (value != null) {
				sum += value.longValue();
			}
		}
		return sum;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		this.simpleCountKeyBuilder = new CountCollecterKeyBuilder();
		this.hashOperations = this.collecterTemplate.opsForHash();
		if(this.timeUnitEnum == null) {
			this.timeUnitEnum = TimeUnit.HOUR;
		}
	}

	@Override
	public void reset(String name, String id) throws DataCollectionException {
		this.set(name, id, 0);
	}

	private void checkDate(Date date) throws DataCollectionException {
		if (date == null) {
			throw new DataCollectionException(
					DataCollectionException.ILLEGAL_DATE + ", data is null.");
		}
	}

	private void checkLimit(int limit) throws DataCollectionException {
		if (limit < 1) {
			throw new DataCollectionException(
					DataCollectionException.ILLEGAL_LIMIT
							+ ", limit is less than 1");
		}
	}

	private long[] convertCollectionToArray(Collection<Long> result) {
		Long[] resultArr = result.toArray(EMTPY_LONG_ARR);
		return ArrayUtils.toPrimitive(resultArr, 0);
	}
	
	private long[] convertStringCollectionToArray(Collection<String> result) {
		int size = result.size();
		String[] valueStrs = new String[size];
		valueStrs = result.toArray(valueStrs);
		long[] values = new long[size];
		for(int i=0; i< size-1; i++) {
			if(valueStrs[i] != null) {
				values[i]=toValue(valueStrs[i]);
			}
		}
		return values;
	}

	private String getExceptionString(String name, String id) {
		return " Service name: " + name + " , id: " + id;
	}

	private void logEntryWithCount(String opName, String name, String id,
			long count) {
		if (logger.isDebugEnabled()) {
			logger.debug("Do " + opName + " on service: " + name + " , id: "
					+ id + " ,  count: " + count);
		}
	}

	private void logExit(String opName, String name, String id, Long result) {
		if (logger.isDebugEnabled()) {
			logger.debug("Do " + opName + " on service: " + name + " , id: "
					+ id + " , value: " + result);
		}
	}

	private void logReadEntryWithLimit(String name, String id, int limit) {
		if (logger.isDebugEnabled()) {
			logger.debug("Do get service: " + name + " , id: " + id
					+ " , limit: " + limit);
		}
	}

	private void logEntry(String opName, String name, String id) {
		logger.debug("Do " + opName + ", service: " + name + " , id: " + id);
	}

	private static class HashPair {
		private String name;
		private Long value;

		public HashPair(String name, Long value) {
			this.name = name;
			this.value = value;
		}

		public String getName() {
			return name;
		}

		public Long getValue() {
			return value;
		}
	}

	public RedisTemplate<String, Object> getRedisTemplate() {
		return collecterTemplate;
	}
}