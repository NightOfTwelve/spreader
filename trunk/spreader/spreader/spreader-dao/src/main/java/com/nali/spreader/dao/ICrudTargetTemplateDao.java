package com.nali.spreader.dao;

import com.nali.spreader.model.TargetTemplate;
import com.nali.spreader.model.TargetTemplateExample;
import java.util.List;

public interface ICrudTargetTemplateDao {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_target_template
     *
     * @ibatorgenerated Thu Mar 31 15:39:48 CST 2011
     */
    int countByExample(TargetTemplateExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_target_template
     *
     * @ibatorgenerated Thu Mar 31 15:39:48 CST 2011
     */
    int deleteByExample(TargetTemplateExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_target_template
     *
     * @ibatorgenerated Thu Mar 31 15:39:48 CST 2011
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_target_template
     *
     * @ibatorgenerated Thu Mar 31 15:39:48 CST 2011
     */
    void insert(TargetTemplate record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_target_template
     *
     * @ibatorgenerated Thu Mar 31 15:39:48 CST 2011
     */
    void insertSelective(TargetTemplate record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_target_template
     *
     * @ibatorgenerated Thu Mar 31 15:39:48 CST 2011
     */
    List selectByExample(TargetTemplateExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_target_template
     *
     * @ibatorgenerated Thu Mar 31 15:39:48 CST 2011
     */
    TargetTemplate selectByPrimaryKey(Long id);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_target_template
     *
     * @ibatorgenerated Thu Mar 31 15:39:48 CST 2011
     */
    int updateByExampleSelective(TargetTemplate record, TargetTemplateExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_target_template
     *
     * @ibatorgenerated Thu Mar 31 15:39:48 CST 2011
     */
    int updateByExample(TargetTemplate record, TargetTemplateExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_target_template
     *
     * @ibatorgenerated Thu Mar 31 15:39:48 CST 2011
     */
    int updateByPrimaryKeySelective(TargetTemplate record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_target_template
     *
     * @ibatorgenerated Thu Mar 31 15:39:48 CST 2011
     */
    int updateByPrimaryKey(TargetTemplate record);
}
