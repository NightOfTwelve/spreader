package com.nali.spreader.dao;

import com.nali.spreader.model.WebContentParameters;
import com.nali.spreader.model.WebContentParametersExample;
import java.util.List;

public interface ICrudWebContentParametersDao {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_web_content_parameters
     *
     * @ibatorgenerated Sat Mar 12 17:19:22 CST 2011
     */
    int countByExample(WebContentParametersExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_web_content_parameters
     *
     * @ibatorgenerated Sat Mar 12 17:19:22 CST 2011
     */
    int deleteByExample(WebContentParametersExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_web_content_parameters
     *
     * @ibatorgenerated Sat Mar 12 17:19:22 CST 2011
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_web_content_parameters
     *
     * @ibatorgenerated Sat Mar 12 17:19:22 CST 2011
     */
    void insert(WebContentParameters record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_web_content_parameters
     *
     * @ibatorgenerated Sat Mar 12 17:19:22 CST 2011
     */
    void insertSelective(WebContentParameters record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_web_content_parameters
     *
     * @ibatorgenerated Sat Mar 12 17:19:22 CST 2011
     */
    List selectByExample(WebContentParametersExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_web_content_parameters
     *
     * @ibatorgenerated Sat Mar 12 17:19:22 CST 2011
     */
    WebContentParameters selectByPrimaryKey(Long id);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_web_content_parameters
     *
     * @ibatorgenerated Sat Mar 12 17:19:22 CST 2011
     */
    int updateByExampleSelective(WebContentParameters record, WebContentParametersExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_web_content_parameters
     *
     * @ibatorgenerated Sat Mar 12 17:19:22 CST 2011
     */
    int updateByExample(WebContentParameters record, WebContentParametersExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_web_content_parameters
     *
     * @ibatorgenerated Sat Mar 12 17:19:22 CST 2011
     */
    int updateByPrimaryKeySelective(WebContentParameters record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_web_content_parameters
     *
     * @ibatorgenerated Sat Mar 12 17:19:22 CST 2011
     */
    int updateByPrimaryKey(WebContentParameters record);
}