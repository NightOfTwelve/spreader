package com.nali.spreader.model;

import java.io.Serializable;
import java.util.Date;
import com.nali.common.model.BaseModel;

public class Template extends BaseModel implements Serializable {

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_template.template_id
     *
     * @ibatorgenerated Fri Mar 25 20:53:46 CST 2011
     */
    private Integer templateId;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_template.name
     *
     * @ibatorgenerated Fri Mar 25 20:53:46 CST 2011
     */
    private String name;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_template.description
     *
     * @ibatorgenerated Fri Mar 25 20:53:46 CST 2011
     */
    private String description;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_template.ceated_time
     *
     * @ibatorgenerated Fri Mar 25 20:53:46 CST 2011
     */
    private Date ceatedTime;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_template.last_modified_time
     *
     * @ibatorgenerated Fri Mar 25 20:53:46 CST 2011
     */
    private Date lastModifiedTime;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_template.content_type
     *
     * @ibatorgenerated Fri Mar 25 20:53:46 CST 2011
     */
    private String contentType;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_template.method_type
     *
     * @ibatorgenerated Fri Mar 25 20:53:46 CST 2011
     */
    private Integer methodType;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_template.template_id
     *
     * @return the value of spreader.tb_template.template_id
     *
     * @ibatorgenerated Fri Mar 25 20:53:46 CST 2011
     */
    public Integer getTemplateId() {
        return templateId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_template.template_id
     *
     * @param templateId the value for spreader.tb_template.template_id
     *
     * @ibatorgenerated Fri Mar 25 20:53:46 CST 2011
     */
    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_template.name
     *
     * @return the value of spreader.tb_template.name
     *
     * @ibatorgenerated Fri Mar 25 20:53:46 CST 2011
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_template.name
     *
     * @param name the value for spreader.tb_template.name
     *
     * @ibatorgenerated Fri Mar 25 20:53:46 CST 2011
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_template.description
     *
     * @return the value of spreader.tb_template.description
     *
     * @ibatorgenerated Fri Mar 25 20:53:46 CST 2011
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_template.description
     *
     * @param description the value for spreader.tb_template.description
     *
     * @ibatorgenerated Fri Mar 25 20:53:46 CST 2011
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_template.ceated_time
     *
     * @return the value of spreader.tb_template.ceated_time
     *
     * @ibatorgenerated Fri Mar 25 20:53:46 CST 2011
     */
    public Date getCeatedTime() {
        return ceatedTime;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_template.ceated_time
     *
     * @param ceatedTime the value for spreader.tb_template.ceated_time
     *
     * @ibatorgenerated Fri Mar 25 20:53:46 CST 2011
     */
    public void setCeatedTime(Date ceatedTime) {
        this.ceatedTime = ceatedTime;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_template.last_modified_time
     *
     * @return the value of spreader.tb_template.last_modified_time
     *
     * @ibatorgenerated Fri Mar 25 20:53:46 CST 2011
     */
    public Date getLastModifiedTime() {
        return lastModifiedTime;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_template.last_modified_time
     *
     * @param lastModifiedTime the value for spreader.tb_template.last_modified_time
     *
     * @ibatorgenerated Fri Mar 25 20:53:46 CST 2011
     */
    public void setLastModifiedTime(Date lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_template.content_type
     *
     * @return the value of spreader.tb_template.content_type
     *
     * @ibatorgenerated Fri Mar 25 20:53:46 CST 2011
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_template.content_type
     *
     * @param contentType the value for spreader.tb_template.content_type
     *
     * @ibatorgenerated Fri Mar 25 20:53:46 CST 2011
     */
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_template.method_type
     *
     * @return the value of spreader.tb_template.method_type
     *
     * @ibatorgenerated Fri Mar 25 20:53:46 CST 2011
     */
    public Integer getMethodType() {
        return methodType;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_template.method_type
     *
     * @param methodType the value for spreader.tb_template.method_type
     *
     * @ibatorgenerated Fri Mar 25 20:53:46 CST 2011
     */
    public void setMethodType(Integer methodType) {
        this.methodType = methodType;
    }
}
