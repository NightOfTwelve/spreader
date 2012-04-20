package com.nali.stat.dc.data;

import java.io.Serializable;

import com.nali.stat.dc.util.TimeUnit;


public class StatService implements Serializable{
	protected String name;
	protected TimeUnit timeUnitEnum = TimeUnit.NONE;

	public StatService() {
	}

	public StatService(String name, TimeUnit timeUnit) {
		this.name = name;
		this.timeUnitEnum = timeUnit;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String serviceName) {
		this.name = serviceName;
	}

	public TimeUnit getTimeUnitEnum() {
		return timeUnitEnum;
	}

	public void setTimeUnitEnum(TimeUnit timeUnitEnum) {
		this.timeUnitEnum = timeUnitEnum;
	}

	public void setTimeUnit(String timeUnit) {
		this.timeUnitEnum = TimeUnit.getTimeUnit(timeUnit);
	}
}