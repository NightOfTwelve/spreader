/*
 *The code is written by NaLi, All rights reserved.
 */
package com.nali.stat.dc.key;

import java.util.Date;

import com.nali.common.util.StringUtil;
import com.nali.stat.dc.exception.DataCollectionException;
import com.nali.stat.dc.util.TimeUnit;

/**
 * @author gavin Created on 2010-12-13
 */
public class CountCollecterKeyBuilder implements KeyBuilder {
	private char moduleSeparator = '_';

	private String pattern = "yyyyMMddHHmmss";

	// private String timeUnitKey = "date";

	public CountCollecterKeyBuilder() {
	}

	@Override
	public String buildKey(String module, TimeUnit timeUnit, String id) {
		if (timeUnit == TimeUnit.NONE) {
			return new StringBuilder(module).append(moduleSeparator).append(id).toString();
		} else {
			Date date = new Date();
			return new StringBuilder(module).append(moduleSeparator).append(timeUnit.convert(date, pattern)).append(moduleSeparator).append(id).toString();
		}
	}

	@Override
	public String[] buildKeys(String module, TimeUnit timeUnit, String id, int limit) throws DataCollectionException {
		return this.buildKeys(module, timeUnit, id, new Date(), limit);
	}
	
	private String[] buildKeys(String module, TimeUnit timeUnit, String id, Date date, int limit) throws DataCollectionException {
		String[] keys = new String[limit];
		if (timeUnit == TimeUnit.NONE) {
			if (limit > 1) {
				throw new DataCollectionException("Limit and timeunit are not matched, limit: " + limit + ", timeunit: " + timeUnit
						+ " , its service name is: " + module);
			}
			keys[0] = new StringBuilder(module).append(moduleSeparator).append(id).toString();
			return keys;
		} else {
			for (int i = 0; i < limit; i++) {
				Date beforeDate = timeUnit.decr(date, i);
				keys[i] = new StringBuilder(module).append(moduleSeparator).append(timeUnit.convert(beforeDate, pattern)).append(moduleSeparator).append(id)
						.toString();
			}
		}
		return keys;
	}
	
	@Override
	public String buildKey(String module, TimeUnit timeUnit, String id, Date date) throws DataCollectionException {
		if (timeUnit == TimeUnit.NONE) {
			throw new DataCollectionException("Timeunit is configured none, but use date to build key!");
		} else {
			return new StringBuilder(module).append(moduleSeparator).append(timeUnit.convert(date, pattern)).append(moduleSeparator).append(id).toString();
		}
	}

	
	
	@Override
	public String[] parseTokens(String key) {
		return StringUtil.split(key, moduleSeparator);
	}

	@Override
	public String[] buildKeys(String module, TimeUnit timeUnit, String id,
			Date fromDate, Date toDate, int limit)
			throws DataCollectionException {
		if(limit < 0) {
			limit = Integer.MAX_VALUE;
		}
		
		long count = (long) timeUnit.between(fromDate, toDate);
		limit = (int) Math.min(limit, count);
		Date date = timeUnit.convert(toDate);
		return this.buildKeys(module, timeUnit, id, date, limit);
	}
}
