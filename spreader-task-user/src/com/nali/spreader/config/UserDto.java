package com.nali.spreader.config;

import java.util.List;

import com.nali.spreader.factory.config.desc.PropertyDescription;

public class UserDto extends WebsiteUserDto {
	private static final long serialVersionUID = -3118856576195136494L;
	public static final int DEFAULT_RANDOM_GTE = 1;
	public static final int DEFAULT_RANDOM_LTE = 10;
	@PropertyDescription("标签")
	private List<String> categories;
	@PropertyDescription("网站id")
	private Integer websiteId;
	@PropertyDescription("是否机器人")
	private Boolean isRobot;
	@PropertyDescription("数量上限")
	private Integer limit;
	@PropertyDescription("关键字列表")
	private List<String> keywords;
	@PropertyDescription("随机关键字列表")
	private List<String> randomKeywords;
	@PropertyDescription("随机上下限")
	private Range<Integer> randomRange;
	
	public List<String> getRandomKeywords() {
		return randomKeywords;
	}

	public void setRandomKeywords(List<String> randomKeywords) {
		this.randomKeywords = randomKeywords;
	}

	public Range<Integer> getRandomRange() {
		return randomRange;
	}

	public void setRandomRange(Range<Integer> randomRange) {
		this.randomRange = randomRange;
	}

	public static UserDto genUserDtoFrom(WebsiteUserDto baseUserDto) {
		UserDto userDto = new UserDto();
		userDto.setArticles(baseUserDto.getArticles());
		userDto.setAttentions(baseUserDto.getAttentions());
		userDto.setBirthdayYear(baseUserDto.getBirthdayYear());
		userDto.setCity(baseUserDto.getCity());
		userDto.setFans(baseUserDto.getFans());
		userDto.setGender(baseUserDto.getGender());
		userDto.setProvince(baseUserDto.getProvince());
		userDto.setvType(baseUserDto.getvType());
		//设置用户的网站UID
		userDto.setWebsiteUid(baseUserDto.getWebsiteUid());
		return userDto;
	}

	public List<String> getKeywords() {
		return keywords;
	}

	public void setKeywords(List<String> keywords) {
		this.keywords = keywords;
	}

	public List<String> getCategories() {
		return categories;
	}

	public void setCategories(List<String> categories) {
		this.categories = categories;
	}

	public Integer getWebsiteId() {
		return websiteId;
	}

	public void setWebsiteId(Integer websiteId) {
		this.websiteId = websiteId;
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
