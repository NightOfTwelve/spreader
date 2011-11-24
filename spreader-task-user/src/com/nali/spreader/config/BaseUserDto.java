package com.nali.spreader.config;

import java.io.Serializable;

import com.nali.spreader.factory.config.desc.PropertyDescription;

public class BaseUserDto implements Serializable {
	private static final long serialVersionUID = -1944768767479868498L;
	@PropertyDescription("性别")
	private Integer gender;
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
	public Integer getGender() {
		return gender;
	}
	public void setGender(Integer gender) {
		this.gender = gender;
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
}
