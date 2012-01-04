package com.nali.spreader.factory.exporter;

import java.util.Date;

import com.nali.spreader.factory.base.TaskMeta;

/**
 * Exporter<br>&nbsp;
 * 采用构造器模式
 * @author sam Created on 2011-12-22
 */
public interface Exporter<TM extends TaskMeta> {
	
	//void setActionId();
	void setBasePriority(int basePriority);
	void setTimes(Date startTime, Date expiredTime);
	void setExpiredTime(Date expiredTime);
	void setUid(Long uid);
	void setSystemProperty(String key, Object value);
	void setClientProperty(String key, Object value);
	void setProperty(String key, Object value);
	void reset();
	void send();
	/**
	 * setUid(Long uid), setExpiredTime(Date expiredTime), send()
	 */
	void send(Long uid, Date expiredTime);
}
