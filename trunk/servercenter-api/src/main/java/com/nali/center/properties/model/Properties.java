package com.nali.center.properties.model;

import com.nali.center.properties.PropertyType;
import com.nali.common.model.BaseModel;
import java.io.Serializable;

public class Properties extends BaseModel implements Serializable {
    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_properties.id
     *
     * @ibatorgenerated Wed Mar 28 13:36:44 CST 2012
     */
    private Long id;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_properties.mod_name
     *
     * @ibatorgenerated Wed Mar 28 13:36:44 CST 2012
     */
    private String modName;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_properties.property_name
     *
     * @ibatorgenerated Wed Mar 28 13:36:44 CST 2012
     */
    private String propertyName;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_properties.sub_property_name
     *
     * @ibatorgenerated Wed Mar 28 13:36:44 CST 2012
     */
    private String subPropertyName;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_properties.property_value
     *
     * @ibatorgenerated Wed Mar 28 13:36:44 CST 2012
     */
    private String propertyValue;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_properties.property_value_type
     *
     * @ibatorgenerated Wed Mar 28 13:36:44 CST 2012
     */
    private String propertyValueType;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_properties.property_type
     *
     * @ibatorgenerated Wed Mar 28 13:36:44 CST 2012
     */
    private Integer propertyType;
    
    private PropertyType propertyTypeEnum;

    public PropertyType getPropertyTypeEnum() {
		return propertyTypeEnum;
	}

	/**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_properties.id
     *
     * @return the value of spreader.tb_properties.id
     *
     * @ibatorgenerated Wed Mar 28 13:36:44 CST 2012
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_properties.id
     *
     * @param id the value for spreader.tb_properties.id
     *
     * @ibatorgenerated Wed Mar 28 13:36:44 CST 2012
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_properties.mod_name
     *
     * @return the value of spreader.tb_properties.mod_name
     *
     * @ibatorgenerated Wed Mar 28 13:36:44 CST 2012
     */
    public String getModName() {
        return modName;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_properties.mod_name
     *
     * @param modName the value for spreader.tb_properties.mod_name
     *
     * @ibatorgenerated Wed Mar 28 13:36:44 CST 2012
     */
    public void setModName(String modName) {
        this.modName = modName;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_properties.property_name
     *
     * @return the value of spreader.tb_properties.property_name
     *
     * @ibatorgenerated Wed Mar 28 13:36:44 CST 2012
     */
    public String getPropertyName() {
        return propertyName;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_properties.property_name
     *
     * @param propertyName the value for spreader.tb_properties.property_name
     *
     * @ibatorgenerated Wed Mar 28 13:36:44 CST 2012
     */
    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_properties.sub_property_name
     *
     * @return the value of spreader.tb_properties.sub_property_name
     *
     * @ibatorgenerated Wed Mar 28 13:36:44 CST 2012
     */
    public String getSubPropertyName() {
        return subPropertyName;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_properties.sub_property_name
     *
     * @param subPropertyName the value for spreader.tb_properties.sub_property_name
     *
     * @ibatorgenerated Wed Mar 28 13:36:44 CST 2012
     */
    public void setSubPropertyName(String subPropertyName) {
        this.subPropertyName = subPropertyName;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_properties.property_value
     *
     * @return the value of spreader.tb_properties.property_value
     *
     * @ibatorgenerated Wed Mar 28 13:36:44 CST 2012
     */
    public String getPropertyValue() {
        return propertyValue;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_properties.property_value
     *
     * @param propertyValue the value for spreader.tb_properties.property_value
     *
     * @ibatorgenerated Wed Mar 28 13:36:44 CST 2012
     */
    public void setPropertyValue(String propertyValue) {
        this.propertyValue = propertyValue;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_properties.property_value_type
     *
     * @return the value of spreader.tb_properties.property_value_type
     *
     * @ibatorgenerated Wed Mar 28 13:36:44 CST 2012
     */
    public String getPropertyValueType() {
        return propertyValueType;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_properties.property_value_type
     *
     * @param propertyValueType the value for spreader.tb_properties.property_value_type
     *
     * @ibatorgenerated Wed Mar 28 13:36:44 CST 2012
     */
    public void setPropertyValueType(String propertyValueType) {
        this.propertyValueType = propertyValueType;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_properties.property_type
     *
     * @return the value of spreader.tb_properties.property_type
     *
     * @ibatorgenerated Wed Mar 28 13:36:44 CST 2012
     */
    public Integer getPropertyType() {
        return propertyType;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_properties.property_type
     *
     * @param propertyType the value for spreader.tb_properties.property_type
     *
     * @ibatorgenerated Wed Mar 28 13:36:44 CST 2012
     */
    public void setPropertyType(Integer propertyType) {
        this.propertyType = propertyType;
        this.propertyTypeEnum = PropertyType.valueOf(propertyType);
    }
}