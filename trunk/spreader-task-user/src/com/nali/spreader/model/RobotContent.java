package com.nali.spreader.model;

import com.nali.common.model.BaseModel;
import java.io.Serializable;
import java.util.Date;

public class RobotContent extends BaseModel implements Serializable {

    private static final long serialVersionUID = 4691012381466371507L;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_robot_content.id
     *
     * @ibatorgenerated Wed Mar 14 14:49:31 CST 2012
     */
    private Long id;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_robot_content.uid
     *
     * @ibatorgenerated Wed Mar 14 14:49:31 CST 2012
     */
    private Long uid;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_robot_content.content_id
     *
     * @ibatorgenerated Wed Mar 14 14:49:31 CST 2012
     */
    private Long contentId;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_robot_content.author_id
     *
     * @ibatorgenerated Wed Mar 14 14:49:31 CST 2012
     */
    private Long authorId;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_robot_content.type
     *
     * @ibatorgenerated Wed Mar 14 14:49:31 CST 2012
     */
    private Integer type;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_robot_content.status
     *
     * @ibatorgenerated Wed Mar 14 14:49:31 CST 2012
     */
    private Integer status;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_robot_content.update_time
     *
     * @ibatorgenerated Wed Mar 14 14:49:31 CST 2012
     */
    private Date updateTime;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_robot_content.id
     *
     * @return the value of spreader.tb_robot_content.id
     *
     * @ibatorgenerated Wed Mar 14 14:49:31 CST 2012
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_robot_content.id
     *
     * @param id the value for spreader.tb_robot_content.id
     *
     * @ibatorgenerated Wed Mar 14 14:49:31 CST 2012
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_robot_content.uid
     *
     * @return the value of spreader.tb_robot_content.uid
     *
     * @ibatorgenerated Wed Mar 14 14:49:31 CST 2012
     */
    public Long getUid() {
        return uid;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_robot_content.uid
     *
     * @param uid the value for spreader.tb_robot_content.uid
     *
     * @ibatorgenerated Wed Mar 14 14:49:31 CST 2012
     */
    public void setUid(Long uid) {
        this.uid = uid;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_robot_content.content_id
     *
     * @return the value of spreader.tb_robot_content.content_id
     *
     * @ibatorgenerated Wed Mar 14 14:49:31 CST 2012
     */
    public Long getContentId() {
        return contentId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_robot_content.content_id
     *
     * @param contentId the value for spreader.tb_robot_content.content_id
     *
     * @ibatorgenerated Wed Mar 14 14:49:31 CST 2012
     */
    public void setContentId(Long contentId) {
        this.contentId = contentId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_robot_content.author_id
     *
     * @return the value of spreader.tb_robot_content.author_id
     *
     * @ibatorgenerated Wed Mar 14 14:49:31 CST 2012
     */
    public Long getAuthorId() {
        return authorId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_robot_content.author_id
     *
     * @param authorId the value for spreader.tb_robot_content.author_id
     *
     * @ibatorgenerated Wed Mar 14 14:49:31 CST 2012
     */
    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_robot_content.type
     *
     * @return the value of spreader.tb_robot_content.type
     *
     * @ibatorgenerated Wed Mar 14 14:49:31 CST 2012
     */
    public Integer getType() {
        return type;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_robot_content.type
     *
     * @param type the value for spreader.tb_robot_content.type
     *
     * @ibatorgenerated Wed Mar 14 14:49:31 CST 2012
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_robot_content.status
     *
     * @return the value of spreader.tb_robot_content.status
     *
     * @ibatorgenerated Wed Mar 14 14:49:31 CST 2012
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_robot_content.status
     *
     * @param status the value for spreader.tb_robot_content.status
     *
     * @ibatorgenerated Wed Mar 14 14:49:31 CST 2012
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_robot_content.update_time
     *
     * @return the value of spreader.tb_robot_content.update_time
     *
     * @ibatorgenerated Wed Mar 14 14:49:31 CST 2012
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_robot_content.update_time
     *
     * @param updateTime the value for spreader.tb_robot_content.update_time
     *
     * @ibatorgenerated Wed Mar 14 14:49:31 CST 2012
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
