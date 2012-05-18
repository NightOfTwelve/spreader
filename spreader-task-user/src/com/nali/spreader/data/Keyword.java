package com.nali.spreader.data;

import com.nali.common.model.BaseModel;
import java.io.Serializable;
import java.util.Date;

public class Keyword extends BaseModel implements Serializable {

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_keyword.id
     *
     * @ibatorgenerated Wed May 16 11:11:11 CST 2012
     */
    private Long id;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_keyword.name
     *
     * @ibatorgenerated Wed May 16 11:11:11 CST 2012
     */
    private String name;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_keyword.category_id
     *
     * @ibatorgenerated Wed May 16 11:11:11 CST 2012
     */
    private Long categoryId;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_keyword.tag
     *
     * @ibatorgenerated Wed May 16 11:11:11 CST 2012
     */
    private Boolean tag;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_keyword.create_time
     *
     * @ibatorgenerated Wed May 16 11:11:11 CST 2012
     */
    private Date createTime;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_keyword.executable
     *
     * @ibatorgenerated Wed May 16 11:11:11 CST 2012
     */
    private Boolean executable;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_keyword.allowtag
     *
     * @ibatorgenerated Wed May 16 11:11:11 CST 2012
     */
    private Boolean allowtag;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_keyword.id
     *
     * @return the value of spreader.tb_keyword.id
     *
     * @ibatorgenerated Wed May 16 11:11:11 CST 2012
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_keyword.id
     *
     * @param id the value for spreader.tb_keyword.id
     *
     * @ibatorgenerated Wed May 16 11:11:11 CST 2012
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_keyword.name
     *
     * @return the value of spreader.tb_keyword.name
     *
     * @ibatorgenerated Wed May 16 11:11:11 CST 2012
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_keyword.name
     *
     * @param name the value for spreader.tb_keyword.name
     *
     * @ibatorgenerated Wed May 16 11:11:11 CST 2012
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_keyword.category_id
     *
     * @return the value of spreader.tb_keyword.category_id
     *
     * @ibatorgenerated Wed May 16 11:11:11 CST 2012
     */
    public Long getCategoryId() {
        return categoryId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_keyword.category_id
     *
     * @param categoryId the value for spreader.tb_keyword.category_id
     *
     * @ibatorgenerated Wed May 16 11:11:11 CST 2012
     */
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_keyword.tag
     *
     * @return the value of spreader.tb_keyword.tag
     *
     * @ibatorgenerated Wed May 16 11:11:11 CST 2012
     */
    public Boolean getTag() {
        return tag;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_keyword.tag
     *
     * @param tag the value for spreader.tb_keyword.tag
     *
     * @ibatorgenerated Wed May 16 11:11:11 CST 2012
     */
    public void setTag(Boolean tag) {
        this.tag = tag;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_keyword.create_time
     *
     * @return the value of spreader.tb_keyword.create_time
     *
     * @ibatorgenerated Wed May 16 11:11:11 CST 2012
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_keyword.create_time
     *
     * @param createTime the value for spreader.tb_keyword.create_time
     *
     * @ibatorgenerated Wed May 16 11:11:11 CST 2012
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_keyword.executable
     *
     * @return the value of spreader.tb_keyword.executable
     *
     * @ibatorgenerated Wed May 16 11:11:11 CST 2012
     */
    public Boolean getExecutable() {
        return executable;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_keyword.executable
     *
     * @param executable the value for spreader.tb_keyword.executable
     *
     * @ibatorgenerated Wed May 16 11:11:11 CST 2012
     */
    public void setExecutable(Boolean executable) {
        this.executable = executable;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_keyword.allowtag
     *
     * @return the value of spreader.tb_keyword.allowtag
     *
     * @ibatorgenerated Wed May 16 11:11:11 CST 2012
     */
    public Boolean getAllowtag() {
        return allowtag;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_keyword.allowtag
     *
     * @param allowtag the value for spreader.tb_keyword.allowtag
     *
     * @ibatorgenerated Wed May 16 11:11:11 CST 2012
     */
    public void setAllowtag(Boolean allowtag) {
        this.allowtag = allowtag;
    }
}
