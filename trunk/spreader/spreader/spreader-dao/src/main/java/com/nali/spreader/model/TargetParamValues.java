package com.nali.spreader.model;

import java.io.Serializable;
import com.nali.common.model.BaseModel;

public class TargetParamValues extends BaseModel implements Serializable {

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_target_param_values.param_id
     *
     * @ibatorgenerated Sun Mar 13 16:01:16 CST 2011
     */
    private Long paramId;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_target_param_values.value
     *
     * @ibatorgenerated Sun Mar 13 16:01:16 CST 2011
     */
    private String value;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_target_param_values.param_id
     *
     * @return the value of spreader.tb_target_param_values.param_id
     *
     * @ibatorgenerated Sun Mar 13 16:01:16 CST 2011
     */
    public Long getParamId() {
        return paramId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_target_param_values.param_id
     *
     * @param paramId the value for spreader.tb_target_param_values.param_id
     *
     * @ibatorgenerated Sun Mar 13 16:01:16 CST 2011
     */
    public void setParamId(Long paramId) {
        this.paramId = paramId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_target_param_values.value
     *
     * @return the value of spreader.tb_target_param_values.value
     *
     * @ibatorgenerated Sun Mar 13 16:01:16 CST 2011
     */
    public String getValue() {
        return value;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_target_param_values.value
     *
     * @param value the value for spreader.tb_target_param_values.value
     *
     * @ibatorgenerated Sun Mar 13 16:01:16 CST 2011
     */
    public void setValue(String value) {
        this.value = value;
    }
}
