package com.nali.spreader.model;

import com.nali.common.model.BaseModel;
import java.io.Serializable;

public class PhoneInfo extends BaseModel implements Serializable {
    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_phone_info.id
     *
     * @ibatorgenerated Fri Aug 23 16:52:22 CST 2013
     */
    private Long id;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_phone_info.phone_name
     *
     * @ibatorgenerated Fri Aug 23 16:52:22 CST 2013
     */
    private String phoneName;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_phone_info.resolution_x
     *
     * @ibatorgenerated Fri Aug 23 16:52:22 CST 2013
     */
    private Integer resolutionX;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_phone_info.resolution_y
     *
     * @ibatorgenerated Fri Aug 23 16:52:22 CST 2013
     */
    private Integer resolutionY;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_phone_info.dpi
     *
     * @ibatorgenerated Fri Aug 23 16:52:22 CST 2013
     */
    private Integer dpi;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_phone_info.id
     *
     * @return the value of spreader.tb_phone_info.id
     *
     * @ibatorgenerated Fri Aug 23 16:52:22 CST 2013
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_phone_info.id
     *
     * @param id the value for spreader.tb_phone_info.id
     *
     * @ibatorgenerated Fri Aug 23 16:52:22 CST 2013
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_phone_info.phone_name
     *
     * @return the value of spreader.tb_phone_info.phone_name
     *
     * @ibatorgenerated Fri Aug 23 16:52:22 CST 2013
     */
    public String getPhoneName() {
        return phoneName;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_phone_info.phone_name
     *
     * @param phoneName the value for spreader.tb_phone_info.phone_name
     *
     * @ibatorgenerated Fri Aug 23 16:52:22 CST 2013
     */
    public void setPhoneName(String phoneName) {
        this.phoneName = phoneName;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_phone_info.resolution_x
     *
     * @return the value of spreader.tb_phone_info.resolution_x
     *
     * @ibatorgenerated Fri Aug 23 16:52:22 CST 2013
     */
    public Integer getResolutionX() {
        return resolutionX;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_phone_info.resolution_x
     *
     * @param resolutionX the value for spreader.tb_phone_info.resolution_x
     *
     * @ibatorgenerated Fri Aug 23 16:52:22 CST 2013
     */
    public void setResolutionX(Integer resolutionX) {
        this.resolutionX = resolutionX;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_phone_info.resolution_y
     *
     * @return the value of spreader.tb_phone_info.resolution_y
     *
     * @ibatorgenerated Fri Aug 23 16:52:22 CST 2013
     */
    public Integer getResolutionY() {
        return resolutionY;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_phone_info.resolution_y
     *
     * @param resolutionY the value for spreader.tb_phone_info.resolution_y
     *
     * @ibatorgenerated Fri Aug 23 16:52:22 CST 2013
     */
    public void setResolutionY(Integer resolutionY) {
        this.resolutionY = resolutionY;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_phone_info.dpi
     *
     * @return the value of spreader.tb_phone_info.dpi
     *
     * @ibatorgenerated Fri Aug 23 16:52:22 CST 2013
     */
    public Integer getDpi() {
        return dpi;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_phone_info.dpi
     *
     * @param dpi the value for spreader.tb_phone_info.dpi
     *
     * @ibatorgenerated Fri Aug 23 16:52:22 CST 2013
     */
    public void setDpi(Integer dpi) {
        this.dpi = dpi;
    }
}