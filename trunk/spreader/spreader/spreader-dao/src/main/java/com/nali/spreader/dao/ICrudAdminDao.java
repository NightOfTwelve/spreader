package com.nali.spreader.dao;

import com.nali.spreader.model.Admin;
import com.nali.spreader.model.AdminExample;
import java.util.List;

public interface ICrudAdminDao {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_admin
     *
     * @ibatorgenerated Sun Mar 13 16:01:15 CST 2011
     */
    int countByExample(AdminExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_admin
     *
     * @ibatorgenerated Sun Mar 13 16:01:15 CST 2011
     */
    int deleteByExample(AdminExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_admin
     *
     * @ibatorgenerated Sun Mar 13 16:01:15 CST 2011
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_admin
     *
     * @ibatorgenerated Sun Mar 13 16:01:15 CST 2011
     */
    void insert(Admin record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_admin
     *
     * @ibatorgenerated Sun Mar 13 16:01:15 CST 2011
     */
    void insertSelective(Admin record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_admin
     *
     * @ibatorgenerated Sun Mar 13 16:01:15 CST 2011
     */
    List selectByExample(AdminExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_admin
     *
     * @ibatorgenerated Sun Mar 13 16:01:15 CST 2011
     */
    Admin selectByPrimaryKey(Integer id);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_admin
     *
     * @ibatorgenerated Sun Mar 13 16:01:15 CST 2011
     */
    int updateByExampleSelective(Admin record, AdminExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_admin
     *
     * @ibatorgenerated Sun Mar 13 16:01:15 CST 2011
     */
    int updateByExample(Admin record, AdminExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_admin
     *
     * @ibatorgenerated Sun Mar 13 16:01:15 CST 2011
     */
    int updateByPrimaryKeySelective(Admin record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_admin
     *
     * @ibatorgenerated Sun Mar 13 16:01:15 CST 2011
     */
    int updateByPrimaryKey(Admin record);
}
