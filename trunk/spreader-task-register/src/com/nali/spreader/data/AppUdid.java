package com.nali.spreader.data;

import java.io.Serializable;

import com.nali.common.model.BaseModel;

public class AppUdid extends BaseModel implements Serializable {
	private static final long serialVersionUID = -275191306461556370L;

	/**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_app_udid.register_id
     *
     * @ibatorgenerated Mon Jul 02 15:18:29 CST 2012
     */
    private Long registerId;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_app_udid.udid
     *
     * @ibatorgenerated Mon Jul 02 15:18:29 CST 2012
     */
    private String udid;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_app_udid.pwd
     *
     * @ibatorgenerated Mon Jul 02 15:18:29 CST 2012
     */
    private String pwd;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_app_udid.register_id
     *
     * @return the value of spreader.tb_app_udid.register_id
     *
     * @ibatorgenerated Mon Jul 02 15:18:29 CST 2012
     */
    public Long getRegisterId() {
        return registerId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_app_udid.register_id
     *
     * @param registerId the value for spreader.tb_app_udid.register_id
     *
     * @ibatorgenerated Mon Jul 02 15:18:29 CST 2012
     */
    public void setRegisterId(Long registerId) {
        this.registerId = registerId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_app_udid.udid
     *
     * @return the value of spreader.tb_app_udid.udid
     *
     * @ibatorgenerated Mon Jul 02 15:18:29 CST 2012
     */
    public String getUdid() {
        return udid;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_app_udid.udid
     *
     * @param udid the value for spreader.tb_app_udid.udid
     *
     * @ibatorgenerated Mon Jul 02 15:18:29 CST 2012
     */
    public void setUdid(String udid) {
        this.udid = udid;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_app_udid.pwd
     *
     * @return the value of spreader.tb_app_udid.pwd
     *
     * @ibatorgenerated Mon Jul 02 15:18:29 CST 2012
     */
    public String getPwd() {
        return pwd;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_app_udid.pwd
     *
     * @param pwd the value for spreader.tb_app_udid.pwd
     *
     * @ibatorgenerated Mon Jul 02 15:18:29 CST 2012
     */
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}