package com.nali.spreader.dao;

import com.nali.spreader.model.TargetParamValues;
import com.nali.spreader.model.TargetParamValuesExample;
import java.util.List;

public interface ICrudTargetParamValuesDao {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_target_param_values
     *
     * @ibatorgenerated Sat Mar 12 17:19:22 CST 2011
     */
    int countByExample(TargetParamValuesExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_target_param_values
     *
     * @ibatorgenerated Sat Mar 12 17:19:22 CST 2011
     */
    int deleteByExample(TargetParamValuesExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_target_param_values
     *
     * @ibatorgenerated Sat Mar 12 17:19:22 CST 2011
     */
    int deleteByPrimaryKey(Long paramId);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_target_param_values
     *
     * @ibatorgenerated Sat Mar 12 17:19:22 CST 2011
     */
    void insert(TargetParamValues record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_target_param_values
     *
     * @ibatorgenerated Sat Mar 12 17:19:22 CST 2011
     */
    void insertSelective(TargetParamValues record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_target_param_values
     *
     * @ibatorgenerated Sat Mar 12 17:19:22 CST 2011
     */
    List selectByExample(TargetParamValuesExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_target_param_values
     *
     * @ibatorgenerated Sat Mar 12 17:19:22 CST 2011
     */
    TargetParamValues selectByPrimaryKey(Long paramId);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_target_param_values
     *
     * @ibatorgenerated Sat Mar 12 17:19:22 CST 2011
     */
    int updateByExampleSelective(TargetParamValues record, TargetParamValuesExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_target_param_values
     *
     * @ibatorgenerated Sat Mar 12 17:19:22 CST 2011
     */
    int updateByExample(TargetParamValues record, TargetParamValuesExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_target_param_values
     *
     * @ibatorgenerated Sat Mar 12 17:19:22 CST 2011
     */
    int updateByPrimaryKeySelective(TargetParamValues record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_target_param_values
     *
     * @ibatorgenerated Sat Mar 12 17:19:22 CST 2011
     */
    int updateByPrimaryKey(TargetParamValues record);
}