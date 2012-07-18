package com.nali.spreader.dao;

import java.util.Date;

import com.nali.spreader.util.KeyValuePair;

public interface IAppRegisterDao {

	KeyValuePair<Date, Long> getLastEndPoint();

	void saveEndPoint(Date date, Long id);

}
