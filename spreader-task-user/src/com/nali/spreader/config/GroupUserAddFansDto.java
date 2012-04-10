package com.nali.spreader.config;

import java.io.Serializable;

import com.nali.spreader.factory.config.desc.PropertyDescription;

public class GroupUserAddFansDto implements Serializable {
	private static final long serialVersionUID = 4995588216370590441L;
	@PropertyDescription("最小关注人数")
	private Integer minUserValue;
	@PropertyDescription("最大关注人数")
	private Integer maxUserValue;

	// @PropertyDescription("机器人比例")
	// private Double robotRate;

	public Integer getMaxUserValue() {
		return maxUserValue;
	}

	public void setMaxUserValue(Integer maxUserValue) {
		this.maxUserValue = maxUserValue;
	}

	public Integer getMinUserValue() {
		return minUserValue;
	}

	public void setMinUserValue(Integer minUserValue) {
		this.minUserValue = minUserValue;
	}

	// public Double getRobotRate() {
	// return robotRate;
	// }
	//
	// public void setRobotRate(Double robotRate) {
	// this.robotRate = robotRate;
	// }
}
