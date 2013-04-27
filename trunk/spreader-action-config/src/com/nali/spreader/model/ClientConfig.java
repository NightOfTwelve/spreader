package com.nali.spreader.model;

import com.nali.common.model.BaseModel;
import java.io.Serializable;

public class ClientConfig extends BaseModel implements Serializable {

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_client_config.id
     *
     * @ibatorgenerated Tue Apr 16 17:39:18 CST 2013
     */
    private Long id;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_client_config.client_id
     *
     * @ibatorgenerated Tue Apr 16 17:39:18 CST 2013
     */
    private Long clientId;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_client_config.config_name
     *
     * @ibatorgenerated Tue Apr 16 17:39:18 CST 2013
     */
    private String configName;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_client_config.config_type
     *
     * @ibatorgenerated Tue Apr 16 17:39:18 CST 2013
     */
    private Integer configType;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_client_config.config_md5
     *
     * @ibatorgenerated Tue Apr 16 17:39:18 CST 2013
     */
    private String configMd5;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_client_config.type
     *
     * @ibatorgenerated Tue Apr 16 17:39:18 CST 2013
     */
    private Integer type;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column spreader.tb_client_config.client_config
     *
     * @ibatorgenerated Tue Apr 16 17:39:18 CST 2013
     */
    private String clientConfig;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_client_config.id
     *
     * @return the value of spreader.tb_client_config.id
     *
     * @ibatorgenerated Tue Apr 16 17:39:18 CST 2013
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_client_config.id
     *
     * @param id the value for spreader.tb_client_config.id
     *
     * @ibatorgenerated Tue Apr 16 17:39:18 CST 2013
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_client_config.client_id
     *
     * @return the value of spreader.tb_client_config.client_id
     *
     * @ibatorgenerated Tue Apr 16 17:39:18 CST 2013
     */
    public Long getClientId() {
        return clientId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_client_config.client_id
     *
     * @param clientId the value for spreader.tb_client_config.client_id
     *
     * @ibatorgenerated Tue Apr 16 17:39:18 CST 2013
     */
    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_client_config.config_name
     *
     * @return the value of spreader.tb_client_config.config_name
     *
     * @ibatorgenerated Tue Apr 16 17:39:18 CST 2013
     */
    public String getConfigName() {
        return configName;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_client_config.config_name
     *
     * @param configName the value for spreader.tb_client_config.config_name
     *
     * @ibatorgenerated Tue Apr 16 17:39:18 CST 2013
     */
    public void setConfigName(String configName) {
        this.configName = configName;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_client_config.config_type
     *
     * @return the value of spreader.tb_client_config.config_type
     *
     * @ibatorgenerated Tue Apr 16 17:39:18 CST 2013
     */
    public Integer getConfigType() {
        return configType;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_client_config.config_type
     *
     * @param configType the value for spreader.tb_client_config.config_type
     *
     * @ibatorgenerated Tue Apr 16 17:39:18 CST 2013
     */
    public void setConfigType(Integer configType) {
        this.configType = configType;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_client_config.config_md5
     *
     * @return the value of spreader.tb_client_config.config_md5
     *
     * @ibatorgenerated Tue Apr 16 17:39:18 CST 2013
     */
    public String getConfigMd5() {
        return configMd5;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_client_config.config_md5
     *
     * @param configMd5 the value for spreader.tb_client_config.config_md5
     *
     * @ibatorgenerated Tue Apr 16 17:39:18 CST 2013
     */
    public void setConfigMd5(String configMd5) {
        this.configMd5 = configMd5;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_client_config.type
     *
     * @return the value of spreader.tb_client_config.type
     *
     * @ibatorgenerated Tue Apr 16 17:39:18 CST 2013
     */
    public Integer getType() {
        return type;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_client_config.type
     *
     * @param type the value for spreader.tb_client_config.type
     *
     * @ibatorgenerated Tue Apr 16 17:39:18 CST 2013
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column spreader.tb_client_config.client_config
     *
     * @return the value of spreader.tb_client_config.client_config
     *
     * @ibatorgenerated Tue Apr 16 17:39:18 CST 2013
     */
    public String getClientConfig() {
        return clientConfig;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column spreader.tb_client_config.client_config
     *
     * @param clientConfig the value for spreader.tb_client_config.client_config
     *
     * @ibatorgenerated Tue Apr 16 17:39:18 CST 2013
     */
    public void setClientConfig(String clientConfig) {
        this.clientConfig = clientConfig;
    }
}