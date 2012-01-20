package com.nali.spreader.group.exp;

import java.util.Date;

import com.nali.lang.StringUtils;
import com.nali.spreader.config.Range;
import com.nali.spreader.data.Constellation;
import com.nali.spreader.group.meta.Gender;

public class PropertyExpression {
	private int propVal;
	
	private Boolean isRobot;
	
	private Date birthDayLte;
	
	private Date birthDayGte;
	
	private Integer constellation; 
	
	private Long attentionsLte;
	
	private Long attentionsGte;
	
	private Long fansLte;
	
	private Long fansGte;
	
	private Long articlesLte;
	
	private Long articlesGte;
	
	private Integer gender;
	
	private Long robotFansLte;
	
	private Long robotFansGte;
	
	private Float scoreGte;
	
	private Float scoreLte;
	
	private String nationality; 
	
	private String province;
	
	private String city;
	
	private String category;
	
	private String introduction;
	
	private String nickName;
	
	private Boolean vType;

	public PropertyExpression(PropertyExpressionDTO propertyExpressionDTO) {
		Range<Long> articlesRange = propertyExpressionDTO.getArticles();
		if(articlesRange != null) {
			this.articlesLte = articlesRange.getLte();
			this.articlesGte = articlesRange.getGte();
		}
		
		
		Range<Long> attentionsRange = propertyExpressionDTO.getAttentions();
		if(attentionsRange != null) {
			this.attentionsLte = attentionsRange.getLte();
			this.attentionsGte = attentionsRange.getGte();
		}
		
		
		Range<Date> birthDayRange = propertyExpressionDTO.getBirthDay();
		if(birthDayRange != null) {
			this.birthDayLte = birthDayRange.getLte();
			this.birthDayGte = birthDayRange.getGte();
		}
		
		this.category = propertyExpressionDTO.getCategory();
		
		this.city = propertyExpressionDTO.getCity();
		
		String constellationStr = propertyExpressionDTO.getConstellation();
		if(StringUtils.isNotEmptyNoOffset(constellationStr)) {
			Constellation constellation = Constellation.matched(constellationStr);
			if(constellation != null) {
				this.constellation = constellation.ordinal();
			}else{
				throw new IllegalArgumentException("Ilegal input constellation!");
			}
		}
		
		Range<Long> fansRange = propertyExpressionDTO.getFans();
		if(fansRange != null) {
			this.fansLte = fansRange.getLte();
			this.fansGte = fansRange.getGte();
		}
		
		String genderStr = propertyExpressionDTO.getGender();
		if(StringUtils.isNotEmptyNoOffset(genderStr)) {
			Gender gender = Gender.matched(genderStr);
			if(gender != null) {
				this.gender = gender.ordinal();
			}else{
				throw new IllegalArgumentException("Ilegal input gender!");
			}
		}
		
		this.introduction = propertyExpressionDTO.getIntroduction();
		
		this.isRobot = propertyExpressionDTO.getIsRobot();
		
		this.nationality = propertyExpressionDTO.getNationality();
		
		this.nickName = propertyExpressionDTO.getNickName();
		
		this.province = propertyExpressionDTO.getProvince();
	
		Range<Long> robotFansRange = propertyExpressionDTO.getRobotFans();
		if(null != robotFansRange) {
			this.robotFansLte = robotFansRange.getLte();
			this.robotFansGte = robotFansRange.getGte();
		}
		
		Range<Float> scoreRange = propertyExpressionDTO.getScore();
		if(null != scoreRange) {
			this.scoreGte = scoreRange.getGte();
			this.scoreLte = scoreRange.getLte();
		}
		
		this.vType = propertyExpressionDTO.getVType();
		
//		String websiteStr = propertyExpressionDTO.getWebsite();
//		if(StringUtils.isNotEmptyNoOffset(websiteStr)) {
//			Website website = Website.matched(websiteStr);
//			if(website != null) {
//				this.website = website.getId();
//			}else{
//				throw new IllegalArgumentException("Ilegal input website!");
//			}
//		}
	}
	
	public PropertyExpression() {
	}
	
	public void setPropVal(int propVal) {
		this.propVal = propVal;
	}

	public void setIsRobot(Boolean isRobot) {
		this.isRobot = isRobot;
	}

	public void setBirthDayLte(Date birthDayLte) {
		this.birthDayLte = birthDayLte;
	}

	public void setBirthDayGte(Date birthDayGte) {
		this.birthDayGte = birthDayGte;
	}

	public void setConstellation(Integer constellation) {
		this.constellation = constellation;
	}

	public void setAttentionsLte(Long attentionsLte) {
		this.attentionsLte = attentionsLte;
	}

	public void setAttentionsGte(Long attentionsGte) {
		this.attentionsGte = attentionsGte;
	}

	public void setFansLte(Long fansLte) {
		this.fansLte = fansLte;
	}

	public void setFansGte(Long fansGte) {
		this.fansGte = fansGte;
	}

	public void setArticlesLte(Long articlesLte) {
		this.articlesLte = articlesLte;
	}

	public void setArticlesGte(Long articlesGte) {
		this.articlesGte = articlesGte;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public void setRobotFansLte(Long robotFansLte) {
		this.robotFansLte = robotFansLte;
	}

	public void setRobotFansGte(Long robotFansGte) {
		this.robotFansGte = robotFansGte;
	}

	public void setScoreGte(Float scoreGte) {
		this.scoreGte = scoreGte;
	}

	public void setScoreLte(Float scoreLte) {
		this.scoreLte = scoreLte;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public void setVType(Boolean type) {
		vType = type;
	}

	public int getPropVal() {
		return propVal;
	}

	public Boolean getIsRobot() {
		return isRobot;
	}

	public Date getBirthDayLte() {
		return birthDayLte;
	}

	public Date getBirthDayGte() {
		return birthDayGte;
	}

	public Integer getConstellation() {
		return constellation;
	}

	public Long getAttentionsLte() {
		return attentionsLte;
	}

	public Long getAttentionsGte() {
		return attentionsGte;
	}

	public Long getFansLte() {
		return fansLte;
	}

	public Long getFansGte() {
		return fansGte;
	}

	public Long getArticlesLte() {
		return articlesLte;
	}

	public Long getArticlesGte() {
		return articlesGte;
	}

	public Integer getGender() {
		return gender;
	}

	public Long getRobotFansLte() {
		return robotFansLte;
	}

	public Long getRobotFansGte() {
		return robotFansGte;
	}

	public Float getScoreGte() {
		return scoreGte;
	}

	public Float getScoreLte() {
		return scoreLte;
	}

	public String getNationality() {
		return nationality;
	}

	public String getProvince() {
		return province;
	}

	public String getCity() {
		return city;
	}

	public String getCategory() {
		return category;
	}

	public String getIntroduction() {
		return introduction;
	}

	public String getNickName() {
		return nickName;
	}

	public Boolean getVType() {
		return vType;
	}
}
