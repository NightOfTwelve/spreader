package com.nali.spreader.model;

import java.io.Serializable;

public class ContentParameters implements Serializable {

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_content_parameters.id
     *
     * @ibatorgenerated Sat Mar 12 17:19:21 CST 2011
     */
    private Long id;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_content_parameters.content_id
     *
     * @ibatorgenerated Sat Mar 12 17:19:21 CST 2011
     */
    private Long contentId;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_content_parameters.param_id
     *
     * @ibatorgenerated Sat Mar 12 17:19:21 CST 2011
     */
    private Long paramId;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_content_parameters.value
     *
     * @ibatorgenerated Sat Mar 12 17:19:21 CST 2011
     */
    private String value;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_content_parameters.id
     *
     * @return the value of spreader.tb_content_parameters.id
     *
     * @ibatorgenerated Sat Mar 12 17:19:21 CST 2011
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_content_parameters.id
     *
     * @param id the value for spreader.tb_content_parameters.id
     *
     * @ibatorgenerated Sat Mar 12 17:19:21 CST 2011
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_content_parameters.content_id
     *
     * @return the value of spreader.tb_content_parameters.content_id
     *
     * @ibatorgenerated Sat Mar 12 17:19:21 CST 2011
     */
    public Long getContentId() {
        return contentId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_content_parameters.content_id
     *
     * @param contentId the value for spreader.tb_content_parameters.content_id
     *
     * @ibatorgenerated Sat Mar 12 17:19:21 CST 2011
     */
    public void setContentId(Long contentId) {
        this.contentId = contentId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_content_parameters.param_id
     *
     * @return the value of spreader.tb_content_parameters.param_id
     *
     * @ibatorgenerated Sat Mar 12 17:19:21 CST 2011
     */
    public Long getParamId() {
        return paramId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_content_parameters.param_id
     *
     * @param paramId the value for spreader.tb_content_parameters.param_id
     *
     * @ibatorgenerated Sat Mar 12 17:19:21 CST 2011
     */
    public void setParamId(Long paramId) {
        this.paramId = paramId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_content_parameters.value
     *
     * @return the value of spreader.tb_content_parameters.value
     *
     * @ibatorgenerated Sat Mar 12 17:19:21 CST 2011
     */
    public String getValue() {
        return value;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_content_parameters.value
     *
     * @param value the value for spreader.tb_content_parameters.value
     *
     * @ibatorgenerated Sat Mar 12 17:19:21 CST 2011
     */
    public void setValue(String value) {
        this.value = value;
    }
}
