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
	private Long minFans;
	private Long maxFans;
	// 机器人粉丝数
	private Long minRobotFans;
	private Long maxRobotFans;
	// 分类
	private String tag;
	// 昵称
	private String nickName;
	// 是否机器人
	private Boolean isRobot;
	// 地区
	private String province;
	// 网站分类
	private Integer websiteId;
	private Integer start;
	private Integer limit;
	private Long websiteUid;
	private Limit lit;

	public Long getWebsiteUid() {
		return websiteUid;
	}

	public void setWebsiteUid(Long websiteUid) {
		this.websiteUid = websiteUid;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public Limit getLit() {
		return lit;
	}

	public void setLit(Limit lit) {
		this.lit = lit;
	}

	public Integer getWebsiteId() {
		return websiteId;
	}

	public void setWebsiteId(Integer websiteId) {
		this.websiteId = websiteId;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMinFans() {
		return minFans;
	}

	public void setMinFans(Long minFans) {
		this.minFans = minFans;
	}

	public Long getMaxFans() {
		return maxFans;
	}

	public void setMaxFans(Long maxFans) {
		this.maxFans = maxFans;
	}

	public Long getMinRobotFans() {
		return minRobotFans;
	}

	public void setMinRobotFans(Long minRobotFans) {
		this.minRobotFans = minRobotFans;
	}

	public Long getMaxRobotFans() {
		return maxRobotFans;
	}

	public void setMaxRobotFans(Long maxRobotFans) {
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