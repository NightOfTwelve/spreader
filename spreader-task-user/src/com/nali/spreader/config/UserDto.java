package com.nali.spreader.config;

import java.io.Serializable;
import java.util.List;

public class UserDto implements Serializable {
	private static final long serialVersionUID = -3118856576195136494L;
	private List<String> categories;
    private Integer gender;
    private Integer userCount;
    private Integer websiteId;
    private Boolean isRobot;
    private Integer vType;
    private String province;
    private String city;
    private Range<Integer> birthdayYear;
    private Range<Long> attentions;
    private Range<Long> fans;
    private Range<Long> articles;
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
	public Integer getUserCount() {
		return userCount;
	}
	public void setUserCount(Integer userCount) {
		this.userCount = userCount;
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
