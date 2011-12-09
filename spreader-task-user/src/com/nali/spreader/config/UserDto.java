package com.nali.spreader.config;

import java.util.List;

import com.nali.spreader.factory.config.desc.PropertyDescription;

public class UserDto extends BaseUserDto {
	private static final long serialVersionUID = -3118856576195136494L;
	@PropertyDescription("分类")
	private List<String> categories;
	@PropertyDescription("网站id")
    private Integer websiteId;
	@PropertyDescription("是否机器人")
	private Boolean isRobot;
	@PropertyDescription("数量上限")
    private Integer limit;
	
	public static UserDto genUserDtoFrom(BaseUserDto baseUserDto) {
		UserDto userDto = new UserDto();
		userDto.setArticles(baseUserDto.getArticles());
		userDto.setAttentions(baseUserDto.getAttentions());
		userDto.setBirthdayYear(baseUserDto.getBirthdayYear());
		userDto.setCity(baseUserDto.getCity());
		userDto.setFans(baseUserDto.getFans());
		userDto.setGender(baseUserDto.getGender());
		userDto.setProvince(baseUserDto.getProvince());
		userDto.setvType(baseUserDto.getvType());
		return userDto;
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
