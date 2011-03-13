package com.nali.spreader.dao;

import com.nali.spreader.model.Websites;
import com.nali.spreader.model.WebsitesExample;
import java.util.List;

public interface ICrudWebsitesDao {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_websites
     *
     * @ibatorgenerated Sun Mar 13 16:01:17 CST 2011
     */
    int countByExample(WebsitesExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_websites
     *
     * @ibatorgenerated Sun Mar 13 16:01:17 CST 2011
     */
    int deleteByExample(WebsitesExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_websites
     *
     * @ibatorgenerated Sun Mar 13 16:01:17 CST 2011
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_websites
     *
     * @ibatorgenerated Sun Mar 13 16:01:17 CST 2011
     */
    void insert(Websites record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_websites
     *
     * @ibatorgenerated Sun Mar 13 16:01:17 CST 2011
     */
    void insertSelective(Websites record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_websites
     *
     * @ibatorgenerated Sun Mar 13 16:01:17 CST 2011
     */
    List selectByExample(WebsitesExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_websites
     *
     * @ibatorgenerated Sun Mar 13 16:01:17 CST 2011
     */
    Websites selectByPrimaryKey(Integer id);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_websites
     *
     * @ibatorgenerated Sun Mar 13 16:01:17 CST 2011
     */
    int updateByExampleSelective(Websites record, WebsitesExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_websites
     *
     * @ibatorgenerated Sun Mar 13 16:01:17 CST 2011
     */
    int updateByExample(Websites record, WebsitesExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_websites
     *
     * @ibatorgenerated Sun Mar 13 16:01:17 CST 2011
     */
    int updateByPrimaryKeySelective(Websites record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_websites
     *
     * @ibatorgenerated Sun Mar 13 16:01:17 CST 2011
     */
    int updateByPrimaryKey(Websites record);
}
