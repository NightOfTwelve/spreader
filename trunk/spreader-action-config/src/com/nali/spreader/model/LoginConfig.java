package com.nali.spreader.model;

import com.nali.common.model.BaseModel;
import java.io.Serializable;

public class LoginConfig extends BaseModel implements Serializable {
    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_login_config.uid
     *
     * @ibatorgenerated Fri Jul 15 17:17:13 CST 2011
     */
    private Long uid;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_login_config.action_id
     *
     * @ibatorgenerated Fri Jul 15 17:17:13 CST 2011
     */
    private Long actionId;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_login_config.contents
     *
     * @ibatorgenerated Fri Jul 15 17:17:13 CST 2011
     */
    private String contents;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_login_config.uid
     *
     * @return the value of spreader.tb_login_config.uid
     *
     * @ibatorgenerated Fri Jul 15 17:17:13 CST 2011
     */
    public Long getUid() {
        return uid;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_login_config.uid
     *
     * @param uid the value for spreader.tb_login_config.uid
     *
     * @ibatorgenerated Fri Jul 15 17:17:13 CST 2011
     */
    public void setUid(Long uid) {
        this.uid = uid;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_login_config.action_id
     *
     * @return the value of spreader.tb_login_config.action_id
     *
     * @ibatorgenerated Fri Jul 15 17:17:13 CST 2011
     */
    public Long getActionId() {
        return actionId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_login_config.action_id
     *
     * @param actionId the value for spreader.tb_login_config.action_id
     *
     * @ibatorgenerated Fri Jul 15 17:17:13 CST 2011
     */
    public void setActionId(Long actionId) {
        this.actionId = actionId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_login_config.contents
     *
     * @return the value of spreader.tb_login_config.contents
     *
     * @ibatorgenerated Fri Jul 15 17:17:13 CST 2011
     */
    public String getContents() {
        return contents;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_login_config.contents
     *
     * @param contents the value for spreader.tb_login_config.contents
     *
     * @ibatorgenerated Fri Jul 15 17:17:13 CST 2011
     */
    public void setContents(String contents) {
        this.contents = contents;
    }
}