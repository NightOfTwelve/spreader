package com.nali.spreader.config;

import java.io.Serializable;

import com.nali.spreader.factory.config.desc.PropertyDescription;

public class CategoryUserMatchDto implements Serializable {
	private static final long serialVersionUID = -8064717832508346311L;
	@PropertyDescription("机器人")
	private BaseUserDto robot;
	@PropertyDescription("用户")
	private BaseUserDto user;
	@PropertyDescription("分类")
	private String category;
	@PropertyDescription("网站Id")
	private Integer websiteId;
	@PropertyDescription("机器人比率")
	private Double robotRate;
	@PropertyDescription("转发人数上限")
	private Long maxUserValue;

	public BaseUserDto getRobot() {
		return robot;
	}

	public void setRobot(BaseUserDto robot) {
		this.robot = robot;
	}

	public BaseUserDto getUser() {
		return user;
	}

	public void setUser(BaseUserDto user) {
		this.user = user;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Integer getWebsiteId() {
		return websiteId;
	}

	public void setWebsiteId(Integer websiteId) {
		this.websiteId = websiteId;
	}

	public Double getRobotRate() {
		return robotRate;
	}

	public void setRobotRate(Double robotRate) {
		this.robotRate = robotRate;
	}

	public Long getMaxUserValue() {
		return maxUserValue;
	}

	public void setMaxUserValue(Long maxUserValue) {
		this.maxUserValue = maxUserValue;
	}
}
