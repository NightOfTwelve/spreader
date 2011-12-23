package com.nali.spreader.config;

import java.io.Serializable;

import com.nali.common.model.Limit;

/**
 * 封装用户信息列表参数的DTO
 * 
 * @author xiefei
 * 
 */
public class UserTagParamsDto implements Serializable {

	private static final long serialVersionUID = -71658634550796740L;
	// 用户ID
	private Long id;
	// 粉丝数
	private Integer minFans;
	private Integer maxFans;
	// 机器人粉丝数
	private Integer minRobotFans;
	private Integer maxRobotFans;
	// 分类
	private String tag;
	// 昵称
	private String nickName;
	// 是否机器人
	private Boolean isRobot;
	// 地区
	private String province;

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public Limit getLimit() {
		return limit;
	}

	public void setLimit(Limit limit) {
		this.limit = limit;
	}

	private Limit limit;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getMinFans() {
		return minFans;
	}

	public void setMinFans(Integer minFans) {
		this.minFans = minFans;
	}

	public Integer getMaxFans() {
		return maxFans;
	}

	public void setMaxFans(Integer maxFans) {
		this.maxFans = maxFans;
	}

	public Integer getMinRobotFans() {
		return minRobotFans;
	}

	public void setMinRobotFans(Integer minRobotFans) {
		this.minRobotFans = minRobotFans;
	}

	public Integer getMaxRobotFans() {
		return maxRobotFans;
	}

	public void setMaxRobotFans(Integer maxRobotFans) {
		this.maxRobotFans = maxRobotFans;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Boolean getIsRobot() {
		return isRobot;
	}

	public void setIsRobot(Boolean isRobot) {
		this.isRobot = isRobot;
	}
}
