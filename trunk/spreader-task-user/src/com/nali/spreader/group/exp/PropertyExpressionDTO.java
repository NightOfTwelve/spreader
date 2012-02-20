package com.nali.spreader.group.exp;

import java.io.Serializable;
import java.util.Date;

import com.nali.spreader.config.Range;
import com.nali.spreader.data.Constellation;
import com.nali.spreader.factory.config.desc.PropertyDescription;
import com.nali.spreader.group.meta.Gender;

public class PropertyExpressionDTO implements Serializable{
	
	@PropertyDescription("是否机器人")
	private Boolean isRobot;
	
	@PropertyDescription("出生日期")
	private Range<Date> birthDay;
	
	@PropertyDescription("星座")
	private String constellation; 
	
	@PropertyDescription("关注数")
	private Range<Long> attentions;
	
	@PropertyDescription("粉丝数")
	private Range<Long> fans;

	@PropertyDescription("文章数")
	private Range<Long> articles;
	
	@PropertyDescription("性别")
	private String gender;
	
	@PropertyDescription("机器人粉丝数")
	private Range<Long> robotFans;
	
	@PropertyDescription("评分")
	private Range<Float> score;
	
	@PropertyDescription("国家")
	private String nationality; 
	
	@PropertyDescription("省份")
	private String province;
	
	@PropertyDescription("城市")
	private String city;
	
	@PropertyDescription("分类")
	private String category;
	
	@PropertyDescription("个人简介")
	private String introduction;
	
	@PropertyDescription("昵称")
	private String nickName;
	
	@PropertyDescription("是否认证")
	private Boolean vType;
	
	public PropertyExpressionDTO() {}
	
	
	public PropertyExpressionDTO(PropertyExpression propertyExpression) {
		
		Long articlesLte = propertyExpression.getArticlesLte();
		Long articlesGte = propertyExpression.getArticlesGte();
		if(articlesLte != null || articlesGte != null) {
			Range<Long> articlesRange = new Range<Long> ();
			articlesRange.setLte(articlesLte);
			articlesRange.setGte(articlesGte);
			this.setArticles(articlesRange);
		}
		
		Long attentionsLte = propertyExpression.getAttentionsLte();
		Long attentionsGte = propertyExpression.getAttentionsGte();
		if(attentionsLte != null || attentionsGte != null) {
			Range<Long> attentionRange = new Range<Long>();
			attentionRange.setLte(attentionsLte);
			attentionRange.setGte(attentionsGte);
			this.setAttentions(attentionRange);
		}
		
		Date birthDayLte = propertyExpression.getBirthDayLte();
		Date birthDayGtte = propertyExpression.getBirthDayGte();
		if(birthDayLte != null || birthDayGtte != null) {
			Range<Date> birthDayRange = new Range<Date>();
			birthDayRange.setLte(birthDayLte);
			birthDayRange.setGte(birthDayGtte);
			this.setBirthDay(birthDayRange);
		}
		
		String category = propertyExpression.getCategory();
		this.setCategory(category);
		
		String city = propertyExpression.getCity();
		this.setCity(city);
		
		Integer constellationInteger = propertyExpression.getConstellation();
	    if(null != constellation) {
	    	Constellation constellation = Constellation.valueOf(constellationInteger);
	    	if(constellation != null) {
	    		this.setConstellation(constellation.getName());
	    	}
	    }
	    
	    Long fansLte = propertyExpression.getFansLte();
	    Long fansGte = propertyExpression.getFansGte();
	    if(fansLte != null || fansGte != null) {
	    	Range<Long> fansRange = new Range<Long> ();
	    	fansRange.setLte(fansLte);
	    	fansRange.setGte(fansGte);
	    	this.setFans(fansRange);
	    }
	    
	    
	    Integer genderInteger = propertyExpression.getGender();
	    if(genderInteger != null) {
	    	Gender gender = Gender.valueOf(genderInteger);
	    	if(gender != null) {
	    		this.setGender(gender.getName());
	    	}
	    }
	    
	    String introduction = propertyExpression.getIntroduction();
	    this.setIntroduction(introduction);
	    
	    Boolean isRobot = propertyExpression.getIsRobot();
	    this.setIsRobot(isRobot);
	    
	    String nationnality = propertyExpression.getNationality();
	    this.setNationality(nationnality);
	    
	    String nickName = propertyExpression.getNickName();
	    this.setNickName(nickName);
	    
	    String province = propertyExpression.getProvince();
	    this.setProvince(province);
	    
	    Long robotFansLte = propertyExpression.getRobotFansLte();
	    Long robotFansGte = propertyExpression.getRobotFansGte();
	    if(robotFansLte != null || robotFansGte != null) {
	    	Range<Long> robotFansRange = new Range<Long>();
	    	robotFansRange.setLte(robotFansLte);
	    	robotFansRange.setGte(robotFansGte);
	    	this.setRobotFans(robotFansRange);
	    }
	    
	    Float scoreGte = propertyExpression.getScoreGte();
	    Float scoreLte = propertyExpression.getScoreLte();
	    if(scoreGte != null || scoreLte != null) {
	    	Range<Float> scoreRange = new Range<Float>();
	    	scoreRange.setGte(scoreGte);
	    	scoreRange.setLte(scoreLte);
	    	this.setScore(scoreRange);
	    }
	    
	    Boolean vType = propertyExpression.getVType();
	    this.setVType(vType);
	    
//	    Integer websiteInteger = propertyExpression.getWebsite();
//	    if(websiteInteger != null) {
//	    	Website website = Website.valueOf(websiteInteger);
//	    	if(website != null) {
//	    		this.setWebsite(website.getName());
//	    	}
//	    }
	}
	
	public Boolean getVType() {
		return vType;
	}

	public void setVType(Boolean type) {
		vType = type;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
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

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
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

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setRobot(Boolean isRobot) {
		this.isRobot = isRobot;
	}

	public Range<Date> getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(Range<Date> birthDay) {
		this.birthDay = birthDay;
	}

	public String getConstellation() {
		return constellation;
	}

	public void setConstellation(String constellation) {
		this.constellation = constellation;
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

	public Boolean getvType() {
		return vType;
	}

	public void setvType(Boolean vType) {
		this.vType = vType;
	}


	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Range<Long> getRobotFans() {
		return robotFans;
	}

	public void setRobotFans(Range<Long> robotFans) {
		this.robotFans = robotFans;
	}

	public Range<Float> getScore() {
		return score;
	}

	public void setScore(Range<Float> score) {
		this.score = score;
	}
}
