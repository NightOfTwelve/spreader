package com.nali.spreader.dao;

import com.nali.spreader.model.WebCookies;
import com.nali.spreader.model.WebCookiesExample;
import java.util.List;

public interface ICrudWebCookiesDao {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_web_cookies
     *
     * @ibatorgenerated Sun Mar 13 16:01:17 CST 2011
     */
    int countByExample(WebCookiesExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_web_cookies
     *
     * @ibatorgenerated Sun Mar 13 16:01:17 CST 2011
     */
    int deleteByExample(WebCookiesExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_web_cookies
     *
     * @ibatorgenerated Sun Mar 13 16:01:17 CST 2011
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_web_cookies
     *
     * @ibatorgenerated Sun Mar 13 16:01:17 CST 2011
     */
    void insert(WebCookies record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_web_cookies
     *
     * @ibatorgenerated Sun Mar 13 16:01:17 CST 2011
     */
    void insertSelective(WebCookies record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_web_cookies
     *
     * @ibatorgenerated Sun Mar 13 16:01:17 CST 2011
     */
    List selectByExample(WebCookiesExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_web_cookies
     *
     * @ibatorgenerated Sun Mar 13 16:01:17 CST 2011
     */
    WebCookies selectByPrimaryKey(Long id);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_web_cookies
     *
     * @ibatorgenerated Sun Mar 13 16:01:17 CST 2011
     */
    int updateByExampleSelective(WebCookies record, WebCookiesExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_web_cookies
     *
     * @ibatorgenerated Sun Mar 13 16:01:17 CST 2011
     */
    int updateByExample(WebCookies record, WebCookiesExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_web_cookies
     *
     * @ibatorgenerated Sun Mar 13 16:01:17 CST 2011
     */
    int updateByPrimaryKeySelective(WebCookies record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_web_cookies
     *
     * @ibatorgenerated Sun Mar 13 16:01:17 CST 2011
     */
    int updateByPrimaryKey(WebCookies record);
}
