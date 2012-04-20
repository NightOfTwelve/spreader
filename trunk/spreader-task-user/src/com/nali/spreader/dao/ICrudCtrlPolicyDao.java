package com.nali.spreader.dao;

import java.util.List;
import com.nali.spreader.data.CtrlPolicy;
import com.nali.spreader.data.CtrlPolicyExample;

public interface ICrudCtrlPolicyDao {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_ctrl_policy
     *
     * @ibatorgenerated Mon Apr 09 16:55:01 CST 2012
     */
    int countByExample(CtrlPolicyExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_ctrl_policy
     *
     * @ibatorgenerated Mon Apr 09 16:55:01 CST 2012
     */
    int deleteByExample(CtrlPolicyExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_ctrl_policy
     *
     * @ibatorgenerated Mon Apr 09 16:55:01 CST 2012
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_ctrl_policy
     *
     * @ibatorgenerated Mon Apr 09 16:55:01 CST 2012
     */
    void insert(CtrlPolicy record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_ctrl_policy
     *
     * @ibatorgenerated Mon Apr 09 16:55:01 CST 2012
     */
    void insertSelective(CtrlPolicy record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_ctrl_policy
     *
     * @ibatorgenerated Mon Apr 09 16:55:01 CST 2012
     */
    List<CtrlPolicy> selectByExample(CtrlPolicyExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_ctrl_policy
     *
     * @ibatorgenerated Mon Apr 09 16:55:01 CST 2012
     */
    CtrlPolicy selectByPrimaryKey(Long id);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_ctrl_policy
     *
     * @ibatorgenerated Mon Apr 09 16:55:01 CST 2012
     */
    int updateByExampleSelective(CtrlPolicy record, CtrlPolicyExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_ctrl_policy
     *
     * @ibatorgenerated Mon Apr 09 16:55:01 CST 2012
     */
    int updateByExample(CtrlPolicy record, CtrlPolicyExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_ctrl_policy
     *
     * @ibatorgenerated Mon Apr 09 16:55:01 CST 2012
     */
    int updateByPrimaryKeySelective(CtrlPolicy record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_ctrl_policy
     *
     * @ibatorgenerated Mon Apr 09 16:55:01 CST 2012
     */
    int updateByPrimaryKey(CtrlPolicy record);
}
