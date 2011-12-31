package com.nali.spreader.model;

import java.io.Serializable;
import java.util.List;
import com.nali.common.model.BaseModel;

public class ClientAction extends BaseModel implements Serializable {

    private static final long serialVersionUID = -1616478963855814084L;

    private List<ActionStep> stepList;

    public List<ActionStep> getStepList() {
        return stepList;
    }

    public void setStepList(List<ActionStep> stepList) {
        this.stepList = stepList;
    }

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_client_action.id
     *
     * @ibatorgenerated Thu Aug 04 16:40:11 CST 2011
     */
    private Long id;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_client_action.logic_type
     *
     * @ibatorgenerated Thu Aug 04 16:40:11 CST 2011
     */
    private Integer logicType;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_client_action.cool_down_seconds
     *
     * @ibatorgenerated Thu Aug 04 16:40:11 CST 2011
     */
    private Long coolDownSeconds;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_client_action.weight
     *
     * @ibatorgenerated Thu Aug 04 16:40:11 CST 2011
     */
    private Integer weight;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_client_action.procedure_config
     *
     * @ibatorgenerated Thu Aug 04 16:40:11 CST 2011
     */
    private String procedureConfig;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_client_action.description
     *
     * @ibatorgenerated Thu Aug 04 16:40:12 CST 2011
     */
    private String description;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_client_action.return_config
     *
     * @ibatorgenerated Thu Aug 04 16:40:12 CST 2011
     */
    private String returnConfig;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_client_action.id
     *
     * @return the value of spreader.tb_client_action.id
     *
     * @ibatorgenerated Thu Aug 04 16:40:11 CST 2011
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_client_action.id
     *
     * @param id the value for spreader.tb_client_action.id
     *
     * @ibatorgenerated Thu Aug 04 16:40:11 CST 2011
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_client_action.logic_type
     *
     * @return the value of spreader.tb_client_action.logic_type
     *
     * @ibatorgenerated Thu Aug 04 16:40:11 CST 2011
     */
    public Integer getLogicType() {
        return logicType;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_client_action.logic_type
     *
     * @param logicType the value for spreader.tb_client_action.logic_type
     *
     * @ibatorgenerated Thu Aug 04 16:40:11 CST 2011
     */
    public void setLogicType(Integer logicType) {
        this.logicType = logicType;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_client_action.cool_down_seconds
     *
     * @return the value of spreader.tb_client_action.cool_down_seconds
     *
     * @ibatorgenerated Thu Aug 04 16:40:11 CST 2011
     */
    public Long getCoolDownSeconds() {
        return coolDownSeconds;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_client_action.cool_down_seconds
     *
     * @param coolDownSeconds the value for spreader.tb_client_action.cool_down_seconds
     *
     * @ibatorgenerated Thu Aug 04 16:40:11 CST 2011
     */
    public void setCoolDownSeconds(Long coolDownSeconds) {
        this.coolDownSeconds = coolDownSeconds;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_client_action.weight
     *
     * @return the value of spreader.tb_client_action.weight
     *
     * @ibatorgenerated Thu Aug 04 16:40:11 CST 2011
     */
    public Integer getWeight() {
        return weight;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_client_action.weight
     *
     * @param weight the value for spreader.tb_client_action.weight
     *
     * @ibatorgenerated Thu Aug 04 16:40:11 CST 2011
     */
    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_client_action.procedure_config
     *
     * @return the value of spreader.tb_client_action.procedure_config
     *
     * @ibatorgenerated Thu Aug 04 16:40:11 CST 2011
     */
    public String getProcedureConfig() {
        return procedureConfig;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_client_action.procedure_config
     *
     * @param procedureConfig the value for spreader.tb_client_action.procedure_config
     *
     * @ibatorgenerated Thu Aug 04 16:40:12 CST 2011
     */
    public void setProcedureConfig(String procedureConfig) {
        this.procedureConfig = procedureConfig;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_client_action.description
     *
     * @return the value of spreader.tb_client_action.description
     *
     * @ibatorgenerated Thu Aug 04 16:40:12 CST 2011
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_client_action.description
     *
     * @param description the value for spreader.tb_client_action.description
     *
     * @ibatorgenerated Thu Aug 04 16:40:12 CST 2011
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_client_action.return_config
     *
     * @return the value of spreader.tb_client_action.return_config
     *
     * @ibatorgenerated Thu Aug 04 16:40:12 CST 2011
     */
    public String getReturnConfig() {
        return returnConfig;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_client_action.return_config
     *
     * @param returnConfig the value for spreader.tb_client_action.return_config
     *
     * @ibatorgenerated Thu Aug 04 16:40:12 CST 2011
     */
    public void setReturnConfig(String returnConfig) {
        this.returnConfig = returnConfig;
    }
}