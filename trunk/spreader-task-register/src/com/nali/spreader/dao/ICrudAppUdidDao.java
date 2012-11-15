package com.nali.spreader.dao;

import java.util.List;

import com.nali.spreader.data.AppUdid;
import com.nali.spreader.data.AppUdidExample;

public interface ICrudAppUdidDao {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_app_udid
     *
     * @ibatorgenerated Mon Jul 02 15:18:29 CST 2012
     */
    int countByExample(AppUdidExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_app_udid
     *
     * @ibatorgenerated Mon Jul 02 15:18:29 CST 2012
     */
    int deleteByExample(AppUdidExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_app_udid
     *
     * @ibatorgenerated Mon Jul 02 15:18:29 CST 2012
     */
    int deleteByPrimaryKey(Long registerId);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_app_udid
     *
     * @ibatorgenerated Mon Jul 02 15:18:29 CST 2012
     */
    void insert(AppUdid record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_app_udid
     *
     * @ibatorgenerated Mon Jul 02 15:18:29 CST 2012
     */
    void insertSelective(AppUdid record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_app_udid
     *
     * @ibatorgenerated Mon Jul 02 15:18:29 CST 2012
     */
    List<AppUdid> selectByExample(AppUdidExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_app_udid
     *
     * @ibatorgenerated Mon Jul 02 15:18:29 CST 2012
     */
    AppUdid selectByPrimaryKey(Long registerId);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_app_udid
     *
     * @ibatorgenerated Mon Jul 02 15:18:29 CST 2012
     */
    int updateByExampleSelective(AppUdid record, AppUdidExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_app_udid
     *
     * @ibatorgenerated Mon Jul 02 15:18:29 CST 2012
     */
    int updateByExample(AppUdid record, AppUdidExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_app_udid
     *
     * @ibatorgenerated Mon Jul 02 15:18:29 CST 2012
     */
    int updateByPrimaryKeySelective(AppUdid record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_app_udid
     *
     * @ibatorgenerated Mon Jul 02 15:18:29 CST 2012
     */
    int updateByPrimaryKey(AppUdid record);
}
