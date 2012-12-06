package com.nali.spreader.model;

import com.nali.common.model.BaseModel;
import java.io.Serializable;

public class RobotUser extends BaseModel implements Serializable {
    private static final long serialVersionUID = -1233874741469235880L;
    public static final Integer ACCOUNT_STATE_NORMAL=1;
    public static final Integer ACCOUNT_STATE_DISABLE=2;
    public static final Integer ACCOUNT_STATE_EXPORT=3;
    public static final Integer ACCOUNT_STATE_LIMITED=4;
	public static final Integer ACCOUNT_STATE_PWD_ERROR=5;
    
    private Integer gender;
    
    public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	/**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_robot_user.uid
     *
     * @ibatorgenerated Thu Aug 25 17:45:15 CST 2011
     */
    private Long uid;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_robot_user.website_id
     *
     * @ibatorgenerated Thu Aug 25 17:45:15 CST 2011
     */
    private Integer websiteId;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_robot_user.website_uid
     *
     * @ibatorgenerated Thu Aug 25 17:45:15 CST 2011
     */
    private Long websiteUid;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_robot_user.login_name
     *
     * @ibatorgenerated Thu Aug 25 17:45:15 CST 2011
     */
    private String loginName;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_robot_user.login_pwd
     *
     * @ibatorgenerated Thu Aug 25 17:45:15 CST 2011
     */
    private String loginPwd;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_robot_user.robot_register_id
     *
     * @ibatorgenerated Thu Aug 25 17:45:15 CST 2011
     */
    private Long robotRegisterId;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_robot_user.account_state
     *
     * @ibatorgenerated Thu Aug 25 17:45:15 CST 2011
     */
    private Integer accountState;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_robot_user.uid
     *
     * @return the value of spreader.tb_robot_user.uid
     *
     * @ibatorgenerated Thu Aug 25 17:45:15 CST 2011
     */
    public Long getUid() {
        return uid;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_robot_user.uid
     *
     * @param uid the value for spreader.tb_robot_user.uid
     *
     * @ibatorgenerated Thu Aug 25 17:45:15 CST 2011
     */
    public void setUid(Long uid) {
        this.uid = uid;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_robot_user.website_id
     *
     * @return the value of spreader.tb_robot_user.website_id
     *
     * @ibatorgenerated Thu Aug 25 17:45:15 CST 2011
     */
    public Integer getWebsiteId() {
        return websiteId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_robot_user.website_id
     *
     * @param websiteId the value for spreader.tb_robot_user.website_id
     *
     * @ibatorgenerated Thu Aug 25 17:45:15 CST 2011
     */
    public void setWebsiteId(Integer websiteId) {
        this.websiteId = websiteId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_robot_user.website_uid
     *
     * @return the value of spreader.tb_robot_user.website_uid
     *
     * @ibatorgenerated Thu Aug 25 17:45:15 CST 2011
     */
    public Long getWebsiteUid() {
        return websiteUid;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_robot_user.website_uid
     *
     * @param websiteUid the value for spreader.tb_robot_user.website_uid
     *
     * @ibatorgenerated Thu Aug 25 17:45:15 CST 2011
     */
    public void setWebsiteUid(Long websiteUid) {
        this.websiteUid = websiteUid;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_robot_user.login_name
     *
     * @return the value of spreader.tb_robot_user.login_name
     *
     * @ibatorgenerated Thu Aug 25 17:45:15 CST 2011
     */
    public String getLoginName() {
        return loginName;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_robot_user.login_name
     *
     * @param loginName the value for spreader.tb_robot_user.login_name
     *
     * @ibatorgenerated Thu Aug 25 17:45:15 CST 2011
     */
    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_robot_user.login_pwd
     *
     * @return the value of spreader.tb_robot_user.login_pwd
     *
     * @ibatorgenerated Thu Aug 25 17:45:15 CST 2011
     */
    public String getLoginPwd() {
        return loginPwd;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_robot_user.login_pwd
     *
     * @param loginPwd the value for spreader.tb_robot_user.login_pwd
     *
     * @ibatorgenerated Thu Aug 25 17:45:15 CST 2011
     */
    public void setLoginPwd(String loginPwd) {
        this.loginPwd = loginPwd;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_robot_user.robot_register_id
     *
     * @return the value of spreader.tb_robot_user.robot_register_id
     *
     * @ibatorgenerated Thu Aug 25 17:45:15 CST 2011
     */
    public Long getRobotRegisterId() {
        return robotRegisterId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_robot_user.robot_register_id
     *
     * @param robotRegisterId the value for spreader.tb_robot_user.robot_register_id
     *
     * @ibatorgenerated Thu Aug 25 17:45:15 CST 2011
     */
    public void setRobotRegisterId(Long robotRegisterId) {
        this.robotRegisterId = robotRegisterId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_robot_user.account_state
     *
     * @return the value of spreader.tb_robot_user.account_state
     *
     * @ibatorgenerated Thu Aug 25 17:45:15 CST 2011
     */
    public Integer getAccountState() {
        return accountState;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_robot_user.account_state
     *
     * @param accountState the value for spreader.tb_robot_user.account_state
     *
     * @ibatorgenerated Thu Aug 25 17:45:15 CST 2011
     */
    public void setAccountState(Integer accountState) {
        this.accountState = accountState;
    }
}