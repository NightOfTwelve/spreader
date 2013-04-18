package com.nali.spreader.dao;

import com.nali.spreader.model.ClientConfig;
import com.nali.spreader.model.ClientConfigExample;
import java.util.List;

public interface ICrudClientConfigDao {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_client_config
     *
     * @ibatorgenerated Tue Apr 16 17:39:18 CST 2013
     */
    int countByExample(ClientConfigExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_client_config
     *
     * @ibatorgenerated Tue Apr 16 17:39:18 CST 2013
     */
    int deleteByExample(ClientConfigExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_client_config
     *
     * @ibatorgenerated Tue Apr 16 17:39:18 CST 2013
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_client_config
     *
     * @ibatorgenerated Tue Apr 16 17:39:18 CST 2013
     */
    Long insert(ClientConfig record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_client_config
     *
     * @ibatorgenerated Tue Apr 16 17:39:18 CST 2013
     */
    Long insertSelective(ClientConfig record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_client_config
     *
     * @ibatorgenerated Tue Apr 16 17:39:18 CST 2013
     */
    List<ClientConfig> selectByExampleWithBLOBs(ClientConfigExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_client_config
     *
     * @ibatorgenerated Tue Apr 16 17:39:18 CST 2013
     */
    List<ClientConfig> selectByExampleWithoutBLOBs(ClientConfigExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_client_config
     *
     * @ibatorgenerated Tue Apr 16 17:39:18 CST 2013
     */
    ClientConfig selectByPrimaryKey(Long id);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_client_config
     *
     * @ibatorgenerated Tue Apr 16 17:39:18 CST 2013
     */
    int updateByExampleSelective(ClientConfig record, ClientConfigExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_client_config
     *
     * @ibatorgenerated Tue Apr 16 17:39:18 CST 2013
     */
    int updateByExampleWithBLOBs(ClientConfig record, ClientConfigExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_client_config
     *
     * @ibatorgenerated Tue Apr 16 17:39:18 CST 2013
     */
    int updateByExampleWithoutBLOBs(ClientConfig record, ClientConfigExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_client_config
     *
     * @ibatorgenerated Tue Apr 16 17:39:18 CST 2013
     */
    int updateByPrimaryKeySelective(ClientConfig record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_client_config
     *
     * @ibatorgenerated Tue Apr 16 17:39:18 CST 2013
     */
    int updateByPrimaryKeyWithBLOBs(ClientConfig record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_client_config
     *
     * @ibatorgenerated Tue Apr 16 17:39:18 CST 2013
     */
    int updateByPrimaryKeyWithoutBLOBs(ClientConfig record);
}
