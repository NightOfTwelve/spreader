package com.nali.spreader.group.exp;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.nali.common.model.Limit;
import com.nali.spreader.config.Range;
import com.nali.spreader.factory.config.desc.PropertyDescription;

public class PropertyExpressionDTO implements Serializable {

	private static final long serialVersionUID = 3721321742370556171L;

	@PropertyDescription("是否机器人")
	private Boolean isRobot;

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

	@PropertyDescription("国家")
	private String nationality;

	@PropertyDescription("省份")
	private String province;

	@PropertyDescription("城市")
	private String city;

	@PropertyDescription("昵称")
	private String nickName;

	@PropertyDescription("加V类型")
	private Integer vType;

	@PropertyDescription("网站ID")
	private Integer websiteId;

	@PropertyDescription("需要排除的用户分组")
	private List<Long> excludeGids;
	
	@PropertyDescription("用户创时间(开始)")
	private Date startCreateTime;
	
	@PropertyDescription("用户创时间(结束)")
	private Date endCreateTime;
	
	private Integer propVal;

	public Date getStartCreateTime() {
		return startCreateTime;
	}

	public void setStartCreateTime(Date startCreateTime) {
		this.startCreateTime = startCreateTime;
	}

	public Date getEndCreateTime() {
		return endCreateTime;
	}

	public void setEndCreateTime(Date endCreateTime) {
		this.endCreateTime = endCreateTime;
	}

	public List<Long> getExcludeGids() {
		return excludeGids;
	}

	public void setExcludeGids(List<Long> excludeGids) {
		this.excludeGids = excludeGids;
	}

	public Integer getPropVal() {
		return propVal;
	}

	public void setPropVal(Integer propVal) {
		this.propVal = propVal;
	}

	public Integer getWebsiteId() {
		return websiteId;
	}

	public void setWebsiteId(Integer websiteId) {
		this.websiteId = websiteId;
	}

	@JsonIgnore
	private Limit lit;

	public Limit getLit() {
		return lit;
	}

	public void setLit(Limit lit) {
		this.lit = lit;
	}

	public Integer getvType() {
		return vType;
	}

	public void setvType(Integer vType) {
		this.vType = vType;
	}

	public PropertyExpressionDTO() {
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

	public void setRobot(Boolean isRobot) {
		this.isRobot = isRobot;
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
}
