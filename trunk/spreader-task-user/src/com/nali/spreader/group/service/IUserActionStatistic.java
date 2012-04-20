package com.nali.spreader.group.service;

import java.util.Date;

import com.nali.stat.dc.data.CountResult;
import com.nali.stat.dc.exception.DataCollectionException;


public interface IUserActionStatistic {
	long[] getCount(String taskCode, int limit, Long... uids) throws DataCollectionException;
	
	long[] getCount(String taskCode, int limit, Date fromDate, Date toDate, Long... uids) throws DataCollectionException;
	
    CountResult getCount(String[] taskCodes, int limit, Long...uids) throws DataCollectionException;
	
	void incr(String taskCode, int value, Long... uids) throws DataCollectionException;
	
	void incr(String taskCode, int value, Date date, Long... uids) throws DataCollectionException;
	
	void setValue(String taskCode, int value, Long... uids) throws DataCollectionException;
	
	void setValue(String taskCode, int value, Date date, Long... uids) throws DataCollectionException;
}
