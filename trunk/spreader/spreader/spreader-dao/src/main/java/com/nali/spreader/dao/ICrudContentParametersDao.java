package com.nali.spreader.dao;

import com.nali.spreader.model.ContentParameters;
import com.nali.spreader.model.ContentParametersExample;
import java.util.List;

public interface ICrudContentParametersDao {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_content_parameters
     *
     * @ibatorgenerated Fri Mar 25 20:53:45 CST 2011
     */
    int countByExample(ContentParametersExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_content_parameters
     *
     * @ibatorgenerated Fri Mar 25 20:53:45 CST 2011
     */
    int deleteByExample(ContentParametersExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_content_parameters
     *
     * @ibatorgenerated Fri Mar 25 20:53:45 CST 2011
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_content_parameters
     *
     * @ibatorgenerated Fri Mar 25 20:53:45 CST 2011
     */
    void insert(ContentParameters record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_content_parameters
     *
     * @ibatorgenerated Fri Mar 25 20:53:45 CST 2011
     */
    void insertSelective(ContentParameters record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_content_parameters
     *
     * @ibatorgenerated Fri Mar 25 20:53:45 CST 2011
     */
    List selectByExample(ContentParametersExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_content_parameters
     *
     * @ibatorgenerated Fri Mar 25 20:53:45 CST 2011
     */
    ContentParameters selectByPrimaryKey(Long id);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_content_parameters
     *
     * @ibatorgenerated Fri Mar 25 20:53:45 CST 2011
     */
    int updateByExampleSelective(ContentParameters record, ContentParametersExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_content_parameters
     *
     * @ibatorgenerated Fri Mar 25 20:53:45 CST 2011
     */
    int updateByExample(ContentParameters record, ContentParametersExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_content_parameters
     *
     * @ibatorgenerated Fri Mar 25 20:53:45 CST 2011
     */
    int updateByPrimaryKeySelective(ContentParameters record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_content_parameters
     *
     * @ibatorgenerated Fri Mar 25 20:53:45 CST 2011
     */
    int updateByPrimaryKey(ContentParameters record);
}
