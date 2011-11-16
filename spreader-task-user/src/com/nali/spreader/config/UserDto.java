package com.nali.spreader.config;

import java.io.Serializable;
import java.util.List;

import com.nali.spreader.factory.config.desc.PropertyDescription;

public class UserDto implements Serializable {
	private static final long serialVersionUID = -3118856576195136494L;
	@PropertyDescription("分类")
	private List<String> categories;
	@PropertyDescription("性别")
    private Integer gender;
	@PropertyDescription("网站id")
    private Integer websiteId;
	@PropertyDescription("是否机器人")
    private Boolean isRobot;
	@PropertyDescription("加v类型")
    private Integer vType;
	@PropertyDescription("省份")
    private String province;
	@PropertyDescription("城市")
    private String city;
	@PropertyDescription("出生年份")
    private Range<Integer> birthdayYear;
	@PropertyDescription("关注人数")
    private Range<Long> attentions;
	@PropertyDescription("粉丝数")
    private Range<Long> fans;
	@PropertyDescription("文章数")
    private Range<Long> articles;
	@PropertyDescription("数量上限")
    private Integer limit;
    
	public List<String> getCategories() {
		return categories;
	}
	public void setCategories(List<String> categories) {
		this.categories = categories;
	}
	public Integer getGender() {
		return gender;
	}
	public void setGender(Integer gender) {
		this.gender = gender;
	}
	public Integer getWebsiteId() {
		return websiteId;
	}
	public void setWebsiteId(Integer websiteId) {
		this.websiteId = websiteId;
	}
	public Integer getvType() {
		return vType;
	}
	public void setvType(Integer vType) {
		this.vType = vType;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public Range<Integer> getBirthdayYear() {
		return birthdayYear;
	}
	public void setBirthdayYear(Range<Integer> birthdayYear) {
		this.birthdayYear = birthdayYear;
	}
	public Range<Long> getAttentions() {
		return attentions;
	}
	public void setAttentions(Range<Long> attentions) {
		this.attentions = attentions;
	}
	public Range<Long> getFans() {
		return fans;
	}
	public void setFans(Range<Long> fans) {
		this.fans = fans;
	}
	public Range<Long> getArticles() {
		return articles;
	}
	public void setArticles(Range<Long> articles) {
		this.articles = articles;
	}
	public Integer getLimit() {
		return limit;
	}
	public void setLimit(Integer limit) {
		this.limit = limit;
	}
	public Boolean getIsRobot() {
		return isRobot;
	}
	public void setIsRobot(Boolean isRobot) {
		this.isRobot = isRobot;
	}
}
