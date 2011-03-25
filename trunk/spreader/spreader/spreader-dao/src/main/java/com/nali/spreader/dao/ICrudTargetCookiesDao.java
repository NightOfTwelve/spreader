package com.nali.spreader.dao;

import com.nali.spreader.model.TargetCookies;
import com.nali.spreader.model.TargetCookiesExample;
import java.util.List;

public interface ICrudTargetCookiesDao {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_target_cookies
     *
     * @ibatorgenerated Fri Mar 25 20:53:45 CST 2011
     */
    int countByExample(TargetCookiesExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_target_cookies
     *
     * @ibatorgenerated Fri Mar 25 20:53:45 CST 2011
     */
    int deleteByExample(TargetCookiesExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_target_cookies
     *
     * @ibatorgenerated Fri Mar 25 20:53:45 CST 2011
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_target_cookies
     *
     * @ibatorgenerated Fri Mar 25 20:53:45 CST 2011
     */
    void insert(TargetCookies record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_target_cookies
     *
     * @ibatorgenerated Fri Mar 25 20:53:45 CST 2011
     */
    void insertSelective(TargetCookies record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_target_cookies
     *
     * @ibatorgenerated Fri Mar 25 20:53:45 CST 2011
     */
    List selectByExample(TargetCookiesExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_target_cookies
     *
     * @ibatorgenerated Fri Mar 25 20:53:45 CST 2011
     */
    TargetCookies selectByPrimaryKey(Long id);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_target_cookies
     *
     * @ibatorgenerated Fri Mar 25 20:53:45 CST 2011
     */
    int updateByExampleSelective(TargetCookies record, TargetCookiesExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_target_cookies
     *
     * @ibatorgenerated Fri Mar 25 20:53:45 CST 2011
     */
    int updateByExample(TargetCookies record, TargetCookiesExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_target_cookies
     *
     * @ibatorgenerated Fri Mar 25 20:53:45 CST 2011
     */
    int updateByPrimaryKeySelective(TargetCookies record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_target_cookies
     *
     * @ibatorgenerated Fri Mar 25 20:53:45 CST 2011
     */
    int updateByPrimaryKey(TargetCookies record);
}
