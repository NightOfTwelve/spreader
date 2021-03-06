package com.nali.spreader.data;

import com.nali.common.model.BaseModel;
import java.io.Serializable;

public class Education extends BaseModel implements Serializable {

    private static final long serialVersionUID = -2872490270125351732L;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_education.id
     *
     * @ibatorgenerated Fri Aug 12 15:49:59 CST 2011
     */
    private Long id;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_education.uid
     *
     * @ibatorgenerated Fri Aug 12 15:49:59 CST 2011
     */
    private Long uid;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_education.school
     *
     * @ibatorgenerated Fri Aug 12 15:49:59 CST 2011
     */
    private String school;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_education.type
     *
     * @ibatorgenerated Fri Aug 12 15:49:59 CST 2011
     */
    private String type;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_education.college
     *
     * @ibatorgenerated Fri Aug 12 15:49:59 CST 2011
     */
    private String college;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_education.year
     *
     * @ibatorgenerated Fri Aug 12 15:49:59 CST 2011
     */
    private Integer year;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_education.id
     *
     * @return the value of spreader.tb_education.id
     *
     * @ibatorgenerated Fri Aug 12 15:49:59 CST 2011
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_education.id
     *
     * @param id the value for spreader.tb_education.id
     *
     * @ibatorgenerated Fri Aug 12 15:49:59 CST 2011
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_education.uid
     *
     * @return the value of spreader.tb_education.uid
     *
     * @ibatorgenerated Fri Aug 12 15:49:59 CST 2011
     */
    public Long getUid() {
        return uid;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_education.uid
     *
     * @param uid the value for spreader.tb_education.uid
     *
     * @ibatorgenerated Fri Aug 12 15:49:59 CST 2011
     */
    public void setUid(Long uid) {
        this.uid = uid;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_education.school
     *
     * @return the value of spreader.tb_education.school
     *
     * @ibatorgenerated Fri Aug 12 15:49:59 CST 2011
     */
    public String getSchool() {
        return school;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_education.school
     *
     * @param school the value for spreader.tb_education.school
     *
     * @ibatorgenerated Fri Aug 12 15:49:59 CST 2011
     */
    public void setSchool(String school) {
        this.school = school;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_education.type
     *
     * @return the value of spreader.tb_education.type
     *
     * @ibatorgenerated Fri Aug 12 15:49:59 CST 2011
     */
    public String getType() {
        return type;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_education.type
     *
     * @param type the value for spreader.tb_education.type
     *
     * @ibatorgenerated Fri Aug 12 15:49:59 CST 2011
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_education.college
     *
     * @return the value of spreader.tb_education.college
     *
     * @ibatorgenerated Fri Aug 12 15:49:59 CST 2011
     */
    public String getCollege() {
        return college;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_education.college
     *
     * @param college the value for spreader.tb_education.college
     *
     * @ibatorgenerated Fri Aug 12 15:49:59 CST 2011
     */
    public void setCollege(String college) {
        this.college = college;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_education.year
     *
     * @return the value of spreader.tb_education.year
     *
     * @ibatorgenerated Fri Aug 12 15:49:59 CST 2011
     */
    public Integer getYear() {
        return year;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_education.year
     *
     * @param year the value for spreader.tb_education.year
     *
     * @ibatorgenerated Fri Aug 12 15:49:59 CST 2011
     */
    public void setYear(Integer year) {
        this.year = year;
    }
}
