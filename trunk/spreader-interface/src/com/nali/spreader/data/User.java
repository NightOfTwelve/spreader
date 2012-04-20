package com.nali.spreader.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.nali.common.model.BaseModel;

public class User extends BaseModel implements Serializable {
	
    public static final Long UID_ANYONE = 0L;
    
    public static final Long UID_NOT_LOGIN = -1L;

    public static final Integer GENDER_MALE = 1;

    public static final Integer GENDER_FEMALE = 2;

    private static final long serialVersionUID = -6112071711622749869L;

    private List<Career> careers;

    private List<Education> educations;

    private List<UserTag> tags;

    private String tag;

    private String avatarUrl;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public List<Career> getCareers() {
        return careers;
    }

    public void setCareers(List<Career> careers) {
        this.careers = careers;
    }

    public List<Education> getEducations() {
        return educations;
    }

    public void setEducations(List<Education> educations) {
        this.educations = educations;
    }

    public List<UserTag> getTags() {
        return tags;
    }

    public void setTags(List<UserTag> tags) {
        this.tags = tags;
    }

    public Integer getVType() {
        return vType;
    }

    public void setVType(Integer type) {
        vType = type;
    }

    public String getVInfo() {
        return vInfo;
    }

    public void setVInfo(String info) {
        vInfo = info;
    }
    
    public static List<Long> getUids(List<User> users) {
    	List<Long> uids = new ArrayList<Long>(users.size());
    	for(User user : users) {
    		uids.add(user.getId());
    	}
    	return uids;
    }
    
    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_user.id
     *
     * @ibatorgenerated Wed Jan 18 15:09:15 CST 2012
     */
    private Long id;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_user.website_id
     *
     * @ibatorgenerated Wed Jan 18 15:09:15 CST 2012
     */
    private Integer websiteId;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_user.website_uid
     *
     * @ibatorgenerated Wed Jan 18 15:09:15 CST 2012
     */
    private Long websiteUid;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_user.is_robot
     *
     * @ibatorgenerated Wed Jan 18 15:09:15 CST 2012
     */
    private Boolean isRobot;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_user.create_time
     *
     * @ibatorgenerated Wed Jan 18 15:09:15 CST 2012
     */
    private Date createTime;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_user.update_time
     *
     * @ibatorgenerated Wed Jan 18 15:09:15 CST 2012
     */
    private Date updateTime;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_user.nick_name
     *
     * @ibatorgenerated Wed Jan 18 15:09:15 CST 2012
     */
    private String nickName;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_user.gender
     *
     * @ibatorgenerated Wed Jan 18 15:09:15 CST 2012
     */
    private Integer gender;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_user.space_entry
     *
     * @ibatorgenerated Wed Jan 18 15:09:15 CST 2012
     */
    private String spaceEntry;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_user.real_name
     *
     * @ibatorgenerated Wed Jan 18 15:09:15 CST 2012
     */
    private String realName;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_user.email
     *
     * @ibatorgenerated Wed Jan 18 15:09:15 CST 2012
     */
    private String email;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_user.nationality
     *
     * @ibatorgenerated Wed Jan 18 15:09:15 CST 2012
     */
    private String nationality;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_user.province
     *
     * @ibatorgenerated Wed Jan 18 15:09:15 CST 2012
     */
    private String province;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_user.city
     *
     * @ibatorgenerated Wed Jan 18 15:09:15 CST 2012
     */
    private String city;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_user.introduction
     *
     * @ibatorgenerated Wed Jan 18 15:09:15 CST 2012
     */
    private String introduction;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_user.birthday_year
     *
     * @ibatorgenerated Wed Jan 18 15:09:15 CST 2012
     */
    private Integer birthdayYear;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_user.birthday_month
     *
     * @ibatorgenerated Wed Jan 18 15:09:15 CST 2012
     */
    private Integer birthdayMonth;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_user.birthday_day
     *
     * @ibatorgenerated Wed Jan 18 15:09:15 CST 2012
     */
    private Integer birthdayDay;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_user.constellation
     *
     * @ibatorgenerated Wed Jan 18 15:09:15 CST 2012
     */
    private Integer constellation;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_user.qq
     *
     * @ibatorgenerated Wed Jan 18 15:09:15 CST 2012
     */
    private String qq;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_user.msn
     *
     * @ibatorgenerated Wed Jan 18 15:09:15 CST 2012
     */
    private String msn;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_user.blog
     *
     * @ibatorgenerated Wed Jan 18 15:09:15 CST 2012
     */
    private String blog;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_user.v_type
     *
     * @ibatorgenerated Wed Jan 18 15:09:15 CST 2012
     */
    private Integer vType;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_user.v_info
     *
     * @ibatorgenerated Wed Jan 18 15:09:15 CST 2012
     */
    private String vInfo;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_user.attentions
     *
     * @ibatorgenerated Wed Jan 18 15:09:15 CST 2012
     */
    private Long attentions;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_user.fans
     *
     * @ibatorgenerated Wed Jan 18 15:09:15 CST 2012
     */
    private Long fans;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_user.articles
     *
     * @ibatorgenerated Wed Jan 18 15:09:15 CST 2012
     */
    private Long articles;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_user.attentions_relation_update_time
     *
     * @ibatorgenerated Wed Jan 18 15:09:15 CST 2012
     */
    private Date attentionsRelationUpdateTime;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_user.robot_fans
     *
     * @ibatorgenerated Wed Jan 18 15:09:15 CST 2012
     */
    private Long robotFans;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_user.pid
     *
     * @ibatorgenerated Wed Jan 18 15:09:15 CST 2012
     */
    private Long pid;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_user.score
     *
     * @ibatorgenerated Wed Jan 18 15:09:15 CST 2012
     */
    private BigDecimal score;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_user.ctrl_gid
     *
     * @ibatorgenerated Wed Jan 18 15:09:15 CST 2012
     */
    private Long ctrlGid;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_user.id
     *
     * @return the value of spreader.tb_user.id
     *
     * @ibatorgenerated Wed Jan 18 15:09:15 CST 2012
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_user.id
     *
     * @param id the value for spreader.tb_user.id
     *
     * @ibatorgenerated Wed Jan 18 15:09:15 CST 2012
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_user.website_id
     *
     * @return the value of spreader.tb_user.website_id
     *
     * @ibatorgenerated Wed Jan 18 15:09:15 CST 2012
     */
    public Integer getWebsiteId() {
        return websiteId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_user.website_id
     *
     * @param websiteId the value for spreader.tb_user.website_id
     *
     * @ibatorgenerated Wed Jan 18 15:09:15 CST 2012
     */
    public void setWebsiteId(Integer websiteId) {
        this.websiteId = websiteId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_user.website_uid
     *
     * @return the value of spreader.tb_user.website_uid
     *
     * @ibatorgenerated Wed Jan 18 15:09:15 CST 2012
     */
    public Long getWebsiteUid() {
        return websiteUid;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_user.website_uid
     *
     * @param websiteUid the value for spreader.tb_user.website_uid
     *
     * @ibatorgenerated Wed Jan 18 15:09:15 CST 2012
     */
    public void setWebsiteUid(Long websiteUid) {
        this.websiteUid = websiteUid;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_user.is_robot
     *
     * @return the value of spreader.tb_user.is_robot
     *
     * @ibatorgenerated Wed Jan 18 15:09:15 CST 2012
     */
    public Boolean getIsRobot() {
        return isRobot;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_user.is_robot
     *
     * @param isRobot the value for spreader.tb_user.is_robot
     *
     * @ibatorgenerated Wed Jan 18 15:09:15 CST 2012
     */
    public void setIsRobot(Boolean isRobot) {
        this.isRobot = isRobot;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_user.create_time
     *
     * @return the value of spreader.tb_user.create_time
     *
     * @ibatorgenerated Wed Jan 18 15:09:15 CST 2012
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_user.create_time
     *
     * @param createTime the value for spreader.tb_user.create_time
     *
     * @ibatorgenerated Wed Jan 18 15:09:15 CST 2012
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_user.update_time
     *
     * @return the value of spreader.tb_user.update_time
     *
     * @ibatorgenerated Wed Jan 18 15:09:15 CST 2012
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_user.update_time
     *
     * @param updateTime the value for spreader.tb_user.update_time
     *
     * @ibatorgenerated Wed Jan 18 15:09:15 CST 2012
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_user.nick_name
     *
     * @return the value of spreader.tb_user.nick_name
     *
     * @ibatorgenerated Wed Jan 18 15:09:15 CST 2012
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_user.nick_name
     *
     * @param nickName the value for spreader.tb_user.nick_name
     *
     * @ibatorgenerated Wed Jan 18 15:09:15 CST 2012
     */
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_user.gender
     *
     * @return the value of spreader.tb_user.gender
     *
     * @ibatorgenerated Wed Jan 18 15:09:15 CST 2012
     */
    public Integer getGender() {
        return gender;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_user.gender
     *
     * @param gender the value for spreader.tb_user.gender
     *
     * @ibatorgenerated Wed Jan 18 15:09:15 CST 2012
     */
    public void setGender(Integer gender) {
        this.gender = gender;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_user.space_entry
     *
     * @return the value of spreader.tb_user.space_entry
     *
     * @ibatorgenerated Wed Jan 18 15:09:15 CST 2012
     */
    public String getSpaceEntry() {
        return spaceEntry;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_user.space_entry
     *
     * @param spaceEntry the value for spreader.tb_user.space_entry
     *
     * @ibatorgenerated Wed Jan 18 15:09:15 CST 2012
     */
    public void setSpaceEntry(String spaceEntry) {
        this.spaceEntry = spaceEntry;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_user.real_name
     *
     * @return the value of spreader.tb_user.real_name
     *
     * @ibatorgenerated Wed Jan 18 15:09:15 CST 2012
     */
    public String getRealName() {
        return realName;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_user.real_name
     *
     * @param realName the value for spreader.tb_user.real_name
     *
     * @ibatorgenerated Wed Jan 18 15:09:15 CST 2012
     */
    public void setRealName(String realName) {
        this.realName = realName;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_user.email
     *
     * @return the value of spreader.tb_user.email
     *
     * @ibatorgenerated Wed Jan 18 15:09:15 CST 2012
     */
    public String getEmail() {
        return email;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_user.email
     *
     * @param email the value for spreader.tb_user.email
     *
     * @ibatorgenerated Wed Jan 18 15:09:15 CST 2012
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_user.nationality
     *
     * @return the value of spreader.tb_user.nationality
     *
     * @ibatorgenerated Wed Jan 18 15:09:15 CST 2012
     */
    public String getNationality() {
        return nationality;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_user.nationality
     *
     * @param nationality the value for spreader.tb_user.nationality
     *
     * @ibatorgenerated Wed Jan 18 15:09:15 CST 2012
     */
    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_user.province
     *
     * @return the value of spreader.tb_user.province
     *
     * @ibatorgenerated Wed Jan 18 15:09:15 CST 2012
     */
    public String getProvince() {
        return province;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_user.province
     *
     * @param province the value for spreader.tb_user.province
     *
     * @ibatorgenerated Wed Jan 18 15:09:15 CST 2012
     */
    public void setProvince(String province) {
        this.province = province;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_user.city
     *
     * @return the value of spreader.tb_user.city
     *
     * @ibatorgenerated Wed Jan 18 15:09:15 CST 2012
     */
    public String getCity() {
        return city;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_user.city
     *
     * @param city the value for spreader.tb_user.city
     *
     * @ibatorgenerated Wed Jan 18 15:09:15 CST 2012
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_user.introduction
     *
     * @return the value of spreader.tb_user.introduction
     *
     * @ibatorgenerated Wed Jan 18 15:09:15 CST 2012
     */
    public String getIntroduction() {
        return introduction;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_user.introduction
     *
     * @param introduction the value for spreader.tb_user.introduction
     *
     * @ibatorgenerated Wed Jan 18 15:09:15 CST 2012
     */
    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_user.birthday_year
     *
     * @return the value of spreader.tb_user.birthday_year
     *
     * @ibatorgenerated Wed Jan 18 15:09:15 CST 2012
     */
    public Integer getBirthdayYear() {
        return birthdayYear;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_user.birthday_year
     *
     * @param birthdayYear the value for spreader.tb_user.birthday_year
     *
     * @ibatorgenerated Wed Jan 18 15:09:15 CST 2012
     */
    public void setBirthdayYear(Integer birthdayYear) {
        this.birthdayYear = birthdayYear;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_user.birthday_month
     *
     * @return the value of spreader.tb_user.birthday_month
     *
     * @ibatorgenerated Wed Jan 18 15:09:15 CST 2012
     */
    public Integer getBirthdayMonth() {
        return birthdayMonth;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_user.birthday_month
     *
     * @param birthdayMonth the value for spreader.tb_user.birthday_month
     *
     * @ibatorgenerated Wed Jan 18 15:09:15 CST 2012
     */
    public void setBirthdayMonth(Integer birthdayMonth) {
        this.birthdayMonth = birthdayMonth;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_user.birthday_day
     *
     * @return the value of spreader.tb_user.birthday_day
     *
     * @ibatorgenerated Wed Jan 18 15:09:15 CST 2012
     */
    public Integer getBirthdayDay() {
        return birthdayDay;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_user.birthday_day
     *
     * @param birthdayDay the value for spreader.tb_user.birthday_day
     *
     * @ibatorgenerated Wed Jan 18 15:09:15 CST 2012
     */
    public void setBirthdayDay(Integer birthdayDay) {
        this.birthdayDay = birthdayDay;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_user.constellation
     *
     * @return the value of spreader.tb_user.constellation
     *
     * @ibatorgenerated Wed Jan 18 15:09:15 CST 2012
     */
    public Integer getConstellation() {
        return constellation;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_user.constellation
     *
     * @param constellation the value for spreader.tb_user.constellation
     *
     * @ibatorgenerated Wed Jan 18 15:09:15 CST 2012
     */
    public void setConstellation(Integer constellation) {
        this.constellation = constellation;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_user.qq
     *
     * @return the value of spreader.tb_user.qq
     *
     * @ibatorgenerated Wed Jan 18 15:09:15 CST 2012
     */
    public String getQq() {
        return qq;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_user.qq
     *
     * @param qq the value for spreader.tb_user.qq
     *
     * @ibatorgenerated Wed Jan 18 15:09:15 CST 2012
     */
    public void setQq(String qq) {
        this.qq = qq;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_user.msn
     *
     * @return the value of spreader.tb_user.msn
     *
     * @ibatorgenerated Wed Jan 18 15:09:15 CST 2012
     */
    public String getMsn() {
        return msn;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_user.msn
     *
     * @param msn the value for spreader.tb_user.msn
     *
     * @ibatorgenerated Wed Jan 18 15:09:15 CST 2012
     */
    public void setMsn(String msn) {
        this.msn = msn;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_user.blog
     *
     * @return the value of spreader.tb_user.blog
     *
     * @ibatorgenerated Wed Jan 18 15:09:15 CST 2012
     */
    public String getBlog() {
        return blog;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_user.blog
     *
     * @param blog the value for spreader.tb_user.blog
     *
     * @ibatorgenerated Wed Jan 18 15:09:15 CST 2012
     */
    public void setBlog(String blog) {
        this.blog = blog;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_user.v_type
     *
     * @return the value of spreader.tb_user.v_type
     *
     * @ibatorgenerated Wed Jan 18 15:09:15 CST 2012
     */
    public Integer getvType() {
        return vType;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_user.v_type
     *
     * @param vType the value for spreader.tb_user.v_type
     *
     * @ibatorgenerated Wed Jan 18 15:09:15 CST 2012
     */
    public void setvType(Integer vType) {
        this.vType = vType;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_user.v_info
     *
     * @return the value of spreader.tb_user.v_info
     *
     * @ibatorgenerated Wed Jan 18 15:09:15 CST 2012
     */
    public String getvInfo() {
        return vInfo;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_user.v_info
     *
     * @param vInfo the value for spreader.tb_user.v_info
     *
     * @ibatorgenerated Wed Jan 18 15:09:15 CST 2012
     */
    public void setvInfo(String vInfo) {
        this.vInfo = vInfo;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_user.attentions
     *
     * @return the value of spreader.tb_user.attentions
     *
     * @ibatorgenerated Wed Jan 18 15:09:15 CST 2012
     */
    public Long getAttentions() {
        return attentions;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_user.attentions
     *
     * @param attentions the value for spreader.tb_user.attentions
     *
     * @ibatorgenerated Wed Jan 18 15:09:15 CST 2012
     */
    public void setAttentions(Long attentions) {
        this.attentions = attentions;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_user.fans
     *
     * @return the value of spreader.tb_user.fans
     *
     * @ibatorgenerated Wed Jan 18 15:09:15 CST 2012
     */
    public Long getFans() {
        return fans;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_user.fans
     *
     * @param fans the value for spreader.tb_user.fans
     *
     * @ibatorgenerated Wed Jan 18 15:09:15 CST 2012
     */
    public void setFans(Long fans) {
        this.fans = fans;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_user.articles
     *
     * @return the value of spreader.tb_user.articles
     *
     * @ibatorgenerated Wed Jan 18 15:09:15 CST 2012
     */
    public Long getArticles() {
        return articles;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_user.articles
     *
     * @param articles the value for spreader.tb_user.articles
     *
     * @ibatorgenerated Wed Jan 18 15:09:15 CST 2012
     */
    public void setArticles(Long articles) {
        this.articles = articles;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_user.attentions_relation_update_time
     *
     * @return the value of spreader.tb_user.attentions_relation_update_time
     *
     * @ibatorgenerated Wed Jan 18 15:09:15 CST 2012
     */
    public Date getAttentionsRelationUpdateTime() {
        return attentionsRelationUpdateTime;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_user.attentions_relation_update_time
     *
     * @param attentionsRelationUpdateTime the value for spreader.tb_user.attentions_relation_update_time
     *
     * @ibatorgenerated Wed Jan 18 15:09:15 CST 2012
     */
    public void setAttentionsRelationUpdateTime(Date attentionsRelationUpdateTime) {
        this.attentionsRelationUpdateTime = attentionsRelationUpdateTime;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_user.robot_fans
     *
     * @return the value of spreader.tb_user.robot_fans
     *
     * @ibatorgenerated Wed Jan 18 15:09:15 CST 2012
     */
    public Long getRobotFans() {
        return robotFans;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_user.robot_fans
     *
     * @param robotFans the value for spreader.tb_user.robot_fans
     *
     * @ibatorgenerated Wed Jan 18 15:09:15 CST 2012
     */
    public void setRobotFans(Long robotFans) {
        this.robotFans = robotFans;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_user.pid
     *
     * @return the value of spreader.tb_user.pid
     *
     * @ibatorgenerated Wed Jan 18 15:09:15 CST 2012
     */
    public Long getPid() {
        return pid;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_user.pid
     *
     * @param pid the value for spreader.tb_user.pid
     *
     * @ibatorgenerated Wed Jan 18 15:09:15 CST 2012
     */
    public void setPid(Long pid) {
        this.pid = pid;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_user.score
     *
     * @return the value of spreader.tb_user.score
     *
     * @ibatorgenerated Wed Jan 18 15:09:15 CST 2012
     */
    public BigDecimal getScore() {
        return score;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_user.score
     *
     * @param score the value for spreader.tb_user.score
     *
     * @ibatorgenerated Wed Jan 18 15:09:15 CST 2012
     */
    public void setScore(BigDecimal score) {
        this.score = score;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_user.ctrl_gid
     *
     * @return the value of spreader.tb_user.ctrl_gid
     *
     * @ibatorgenerated Wed Jan 18 15:09:15 CST 2012
     */
    public Long getCtrlGid() {
        return ctrlGid;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_user.ctrl_gid
     *
     * @param ctrlGid the value for spreader.tb_user.ctrl_gid
     *
     * @ibatorgenerated Wed Jan 18 15:09:15 CST 2012
     */
    public void setCtrlGid(Long ctrlGid) {
        this.ctrlGid = ctrlGid;
    }
}
