package com.nali.spreader.model;

import java.io.Serializable;
import java.util.Date;
import com.nali.common.model.BaseModel;

public class Content extends BaseModel implements Serializable {

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_content.content_id
     *
     * @ibatorgenerated Fri Mar 25 20:53:45 CST 2011
     */
    private Long contentId;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_content.template_id
     *
     * @ibatorgenerated Fri Mar 25 20:53:45 CST 2011
     */
    private Long templateId;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_content.quality
     *
     * @ibatorgenerated Fri Mar 25 20:53:45 CST 2011
     */
    private Byte quality;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_content.creater_id
     *
     * @ibatorgenerated Fri Mar 25 20:53:45 CST 2011
     */
    private Long createrId;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_content.creater_time
     *
     * @ibatorgenerated Fri Mar 25 20:53:45 CST 2011
     */
    private Date createrTime;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_content.last_modified_time
     *
     * @ibatorgenerated Fri Mar 25 20:53:45 CST 2011
     */
    private Date lastModifiedTime;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_content.use_count
     *
     * @ibatorgenerated Fri Mar 25 20:53:45 CST 2011
     */
    private Integer useCount;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_content.content_id
     *
     * @return the value of spreader.tb_content.content_id
     *
     * @ibatorgenerated Fri Mar 25 20:53:45 CST 2011
     */
    public Long getContentId() {
        return contentId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_content.content_id
     *
     * @param contentId the value for spreader.tb_content.content_id
     *
     * @ibatorgenerated Fri Mar 25 20:53:45 CST 2011
     */
    public void setContentId(Long contentId) {
        this.contentId = contentId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_content.template_id
     *
     * @return the value of spreader.tb_content.template_id
     *
     * @ibatorgenerated Fri Mar 25 20:53:45 CST 2011
     */
    public Long getTemplateId() {
        return templateId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_content.template_id
     *
     * @param templateId the value for spreader.tb_content.template_id
     *
     * @ibatorgenerated Fri Mar 25 20:53:45 CST 2011
     */
    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_content.quality
     *
     * @return the value of spreader.tb_content.quality
     *
     * @ibatorgenerated Fri Mar 25 20:53:45 CST 2011
     */
    public Byte getQuality() {
        return quality;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_content.quality
     *
     * @param quality the value for spreader.tb_content.quality
     *
     * @ibatorgenerated Fri Mar 25 20:53:45 CST 2011
     */
    public void setQuality(Byte quality) {
        this.quality = quality;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_content.creater_id
     *
     * @return the value of spreader.tb_content.creater_id
     *
     * @ibatorgenerated Fri Mar 25 20:53:45 CST 2011
     */
    public Long getCreaterId() {
        return createrId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_content.creater_id
     *
     * @param createrId the value for spreader.tb_content.creater_id
     *
     * @ibatorgenerated Fri Mar 25 20:53:45 CST 2011
     */
    public void setCreaterId(Long createrId) {
        this.createrId = createrId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_content.creater_time
     *
     * @return the value of spreader.tb_content.creater_time
     *
     * @ibatorgenerated Fri Mar 25 20:53:45 CST 2011
     */
    public Date getCreaterTime() {
        return createrTime;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_content.creater_time
     *
     * @param createrTime the value for spreader.tb_content.creater_time
     *
     * @ibatorgenerated Fri Mar 25 20:53:45 CST 2011
     */
    public void setCreaterTime(Date createrTime) {
        this.createrTime = createrTime;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_content.last_modified_time
     *
     * @return the value of spreader.tb_content.last_modified_time
     *
     * @ibatorgenerated Fri Mar 25 20:53:45 CST 2011
     */
    public Date getLastModifiedTime() {
        return lastModifiedTime;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_content.last_modified_time
     *
     * @param lastModifiedTime the value for spreader.tb_content.last_modified_time
     *
     * @ibatorgenerated Fri Mar 25 20:53:45 CST 2011
     */
    public void setLastModifiedTime(Date lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_content.use_count
     *
     * @return the value of spreader.tb_content.use_count
     *
     * @ibatorgenerated Fri Mar 25 20:53:45 CST 2011
     */
    public Integer getUseCount() {
        return useCount;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_content.use_count
     *
     * @param useCount the value for spreader.tb_content.use_count
     *
     * @ibatorgenerated Fri Mar 25 20:53:45 CST 2011
     */
    public void setUseCount(Integer useCount) {
        this.useCount = useCount;
    }
}
