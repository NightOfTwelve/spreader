package com.nali.spreader.config;

import java.io.Serializable;
import java.util.List;

import com.nali.spreader.factory.config.desc.PropertyDescription;

public class UserRelatedDto implements Serializable {
	private static final long serialVersionUID = 5069704846543292631L;
	@PropertyDescription("基本信息")
	private BaseUserDto baseInfo;
	@PropertyDescription("是否机器人")
	private Boolean isRobot;
	
	@PropertyDescription("分类")
	private List<String> categories;
	
	@PropertyDescription("关注了用户（uid）")
	private Long attentionToUid;
	@PropertyDescription("包含粉丝（uid）")
	private Long fansUid;
	public List<String> getCategories() {
		return categories;
	}
	public void setCategories(List<String> categories) {
		this.categories = categories;
	}
	public Boolean getIsRobot() {
		return isRobot;
	}
	public void setIsRobot(Boolean isRobot) {
		this.isRobot = isRobot;
	}
	public Long getAttentionToUid() {
		return attentionToUid;
	}
	public void setAttentionToUid(Long attentionToUid) {
		this.attentionToUid = attentionToUid;
	}
	public Long getFansUid() {
		return fansUid;
	}
	public void setFansUid(Long fansUid) {
		this.fansUid = fansUid;
	}
	public BaseUserDto getBaseInfo() {
		return baseInfo;
	}
	public void setBaseInfo(BaseUserDto baseInfo) {
		this.baseInfo = baseInfo;
	}
}
