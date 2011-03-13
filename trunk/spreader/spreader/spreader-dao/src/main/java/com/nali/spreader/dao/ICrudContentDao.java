package com.nali.spreader.dao;

import com.nali.spreader.model.Content;
import com.nali.spreader.model.ContentExample;
import java.util.List;

public interface ICrudContentDao {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_content
     *
     * @ibatorgenerated Sun Mar 13 16:01:15 CST 2011
     */
    int countByExample(ContentExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_content
     *
     * @ibatorgenerated Sun Mar 13 16:01:15 CST 2011
     */
    int deleteByExample(ContentExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_content
     *
     * @ibatorgenerated Sun Mar 13 16:01:15 CST 2011
     */
    int deleteByPrimaryKey(Long contentId);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_content
     *
     * @ibatorgenerated Sun Mar 13 16:01:15 CST 2011
     */
    void insert(Content record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_content
     *
     * @ibatorgenerated Sun Mar 13 16:01:15 CST 2011
     */
    void insertSelective(Content record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_content
     *
     * @ibatorgenerated Sun Mar 13 16:01:15 CST 2011
     */
    List selectByExample(ContentExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_content
     *
     * @ibatorgenerated Sun Mar 13 16:01:15 CST 2011
     */
    Content selectByPrimaryKey(Long contentId);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_content
     *
     * @ibatorgenerated Sun Mar 13 16:01:15 CST 2011
     */
    int updateByExampleSelective(Content record, ContentExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_content
     *
     * @ibatorgenerated Sun Mar 13 16:01:15 CST 2011
     */
    int updateByExample(Content record, ContentExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_content
     *
     * @ibatorgenerated Sun Mar 13 16:01:15 CST 2011
     */
    int updateByPrimaryKeySelective(Content record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_content
     *
     * @ibatorgenerated Sun Mar 13 16:01:15 CST 2011
     */
    int updateByPrimaryKey(Content record);
}
