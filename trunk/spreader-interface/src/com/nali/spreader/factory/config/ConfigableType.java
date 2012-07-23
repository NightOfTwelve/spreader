package com.nali.spreader.factory.config;

public enum ConfigableType {
	normal(1),
	system(0),
	noticeRelated(2),
	;
	public final Integer jobType;
	private ConfigableType(Integer jobType) {
		this.jobType = jobType;
	}
}
