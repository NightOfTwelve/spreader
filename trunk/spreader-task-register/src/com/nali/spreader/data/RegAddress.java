package com.nali.spreader.data;

import com.nali.common.model.BaseModel;
import java.io.Serializable;

public class RegAddress extends BaseModel implements Serializable {
	private static final long serialVersionUID = -2585324402047875980L;
	public static final String NATIONALITY_USA="USA";
	public static final String NATIONALITY_CN="CN";

	/**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_reg_address.register_id
     *
     * @ibatorgenerated Wed Jun 06 17:41:16 CST 2012
     */
    private Long registerId;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_reg_address.nationality
     *
     * @ibatorgenerated Wed Jun 06 17:41:16 CST 2012
     */
    private String nationality;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_reg_address.province
     *
     * @ibatorgenerated Wed Jun 06 17:41:16 CST 2012
     */
    private String province;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_reg_address.city
     *
     * @ibatorgenerated Wed Jun 06 17:41:16 CST 2012
     */
    private String city;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_reg_address.street
     *
     * @ibatorgenerated Wed Jun 06 17:41:16 CST 2012
     */
    private String street;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_reg_address.suite
     *
     * @ibatorgenerated Wed Jun 06 17:41:16 CST 2012
     */
    private String suite;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_reg_address.post_code
     *
     * @ibatorgenerated Wed Jun 06 17:41:16 CST 2012
     */
    private String postCode;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_reg_address.phone_area_code
     *
     * @ibatorgenerated Wed Jun 06 17:41:16 CST 2012
     */
    private String phoneAreaCode;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_reg_address.phone_code
     *
     * @ibatorgenerated Wed Jun 06 17:41:16 CST 2012
     */
    private String phoneCode;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_reg_address.register_id
     *
     * @return the value of spreader.tb_reg_address.register_id
     *
     * @ibatorgenerated Wed Jun 06 17:41:16 CST 2012
     */
    public Long getRegisterId() {
        return registerId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_reg_address.register_id
     *
     * @param registerId the value for spreader.tb_reg_address.register_id
     *
     * @ibatorgenerated Wed Jun 06 17:41:16 CST 2012
     */
    public void setRegisterId(Long registerId) {
        this.registerId = registerId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_reg_address.nationality
     *
     * @return the value of spreader.tb_reg_address.nationality
     *
     * @ibatorgenerated Wed Jun 06 17:41:16 CST 2012
     */
    public String getNationality() {
        return nationality;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_reg_address.nationality
     *
     * @param nationality the value for spreader.tb_reg_address.nationality
     *
     * @ibatorgenerated Wed Jun 06 17:41:16 CST 2012
     */
    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_reg_address.province
     *
     * @return the value of spreader.tb_reg_address.province
     *
     * @ibatorgenerated Wed Jun 06 17:41:16 CST 2012
     */
    public String getProvince() {
        return province;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_reg_address.province
     *
     * @param province the value for spreader.tb_reg_address.province
     *
     * @ibatorgenerated Wed Jun 06 17:41:16 CST 2012
     */
    public void setProvince(String province) {
        this.province = province;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_reg_address.city
     *
     * @return the value of spreader.tb_reg_address.city
     *
     * @ibatorgenerated Wed Jun 06 17:41:16 CST 2012
     */
    public String getCity() {
        return city;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_reg_address.city
     *
     * @param city the value for spreader.tb_reg_address.city
     *
     * @ibatorgenerated Wed Jun 06 17:41:16 CST 2012
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_reg_address.street
     *
     * @return the value of spreader.tb_reg_address.street
     *
     * @ibatorgenerated Wed Jun 06 17:41:16 CST 2012
     */
    public String getStreet() {
        return street;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_reg_address.street
     *
     * @param street the value for spreader.tb_reg_address.street
     *
     * @ibatorgenerated Wed Jun 06 17:41:16 CST 2012
     */
    public void setStreet(String street) {
        this.street = street;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_reg_address.suite
     *
     * @return the value of spreader.tb_reg_address.suite
     *
     * @ibatorgenerated Wed Jun 06 17:41:16 CST 2012
     */
    public String getSuite() {
        return suite;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_reg_address.suite
     *
     * @param suite the value for spreader.tb_reg_address.suite
     *
     * @ibatorgenerated Wed Jun 06 17:41:16 CST 2012
     */
    public void setSuite(String suite) {
        this.suite = suite;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_reg_address.post_code
     *
     * @return the value of spreader.tb_reg_address.post_code
     *
     * @ibatorgenerated Wed Jun 06 17:41:16 CST 2012
     */
    public String getPostCode() {
        return postCode;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_reg_address.post_code
     *
     * @param postCode the value for spreader.tb_reg_address.post_code
     *
     * @ibatorgenerated Wed Jun 06 17:41:16 CST 2012
     */
    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_reg_address.phone_area_code
     *
     * @return the value of spreader.tb_reg_address.phone_area_code
     *
     * @ibatorgenerated Wed Jun 06 17:41:16 CST 2012
     */
    public String getPhoneAreaCode() {
        return phoneAreaCode;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_reg_address.phone_area_code
     *
     * @param phoneAreaCode the value for spreader.tb_reg_address.phone_area_code
     *
     * @ibatorgenerated Wed Jun 06 17:41:16 CST 2012
     */
    public void setPhoneAreaCode(String phoneAreaCode) {
        this.phoneAreaCode = phoneAreaCode;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_reg_address.phone_code
     *
     * @return the value of spreader.tb_reg_address.phone_code
     *
     * @ibatorgenerated Wed Jun 06 17:41:16 CST 2012
     */
    public String getPhoneCode() {
        return phoneCode;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_reg_address.phone_code
     *
     * @param phoneCode the value for spreader.tb_reg_address.phone_code
     *
     * @ibatorgenerated Wed Jun 06 17:41:16 CST 2012
     */
    public void setPhoneCode(String phoneCode) {
        this.phoneCode = phoneCode;
    }
}