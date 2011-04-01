package com.nali.spreader.dao;

import com.nali.spreader.model.TargetWebFlow;
import com.nali.spreader.model.TargetWebFlowExample;
import java.util.List;

public interface ICrudTargetWebFlowDao {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_target_web_flow
     *
     * @ibatorgenerated Thu Mar 31 15:39:48 CST 2011
     */
    int countByExample(TargetWebFlowExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_target_web_flow
     *
     * @ibatorgenerated Thu Mar 31 15:39:48 CST 2011
     */
    int deleteByExample(TargetWebFlowExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_target_web_flow
     *
     * @ibatorgenerated Thu Mar 31 15:39:48 CST 2011
     */
    int deleteByPrimaryKey(Long flowId);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_target_web_flow
     *
     * @ibatorgenerated Thu Mar 31 15:39:48 CST 2011
     */
    void insert(TargetWebFlow record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_target_web_flow
     *
     * @ibatorgenerated Thu Mar 31 15:39:48 CST 2011
     */
    void insertSelective(TargetWebFlow record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_target_web_flow
     *
     * @ibatorgenerated Thu Mar 31 15:39:48 CST 2011
     */
    List selectByExample(TargetWebFlowExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_target_web_flow
     *
     * @ibatorgenerated Thu Mar 31 15:39:48 CST 2011
     */
    TargetWebFlow selectByPrimaryKey(Long flowId);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_target_web_flow
     *
     * @ibatorgenerated Thu Mar 31 15:39:48 CST 2011
     */
    int updateByExampleSelective(TargetWebFlow record, TargetWebFlowExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_target_web_flow
     *
     * @ibatorgenerated Thu Mar 31 15:39:48 CST 2011
     */
    int updateByExample(TargetWebFlow record, TargetWebFlowExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_target_web_flow
     *
     * @ibatorgenerated Thu Mar 31 15:39:48 CST 2011
     */
    int updateByPrimaryKeySelective(TargetWebFlow record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_target_web_flow
     *
     * @ibatorgenerated Thu Mar 31 15:39:48 CST 2011
     */
    int updateByPrimaryKey(TargetWebFlow record);
}
