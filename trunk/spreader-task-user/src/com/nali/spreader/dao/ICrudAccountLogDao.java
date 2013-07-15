package com.nali.spreader.dao;

import com.nali.spreader.data.AccountLog;
import com.nali.spreader.data.AccountLogExample;
import java.util.List;

public interface ICrudAccountLogDao {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_account_log
     *
     * @ibatorgenerated Thu Feb 21 13:45:38 CST 2013
     */
    int countByExample(AccountLogExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_account_log
     *
     * @ibatorgenerated Thu Feb 21 13:45:38 CST 2013
     */
    int deleteByExample(AccountLogExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_account_log
     *
     * @ibatorgenerated Thu Feb 21 13:45:38 CST 2013
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_account_log
     *
     * @ibatorgenerated Thu Feb 21 13:45:38 CST 2013
     */
    Long insert(AccountLog record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_account_log
     *
     * @ibatorgenerated Thu Feb 21 13:45:38 CST 2013
     */
    Long insertSelective(AccountLog record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_account_log
     *
     * @ibatorgenerated Thu Feb 21 13:45:38 CST 2013
     */
    List<AccountLog> selectByExample(AccountLogExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_account_log
     *
     * @ibatorgenerated Thu Feb 21 13:45:38 CST 2013
     */
    AccountLog selectByPrimaryKey(Long id);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_account_log
     *
     * @ibatorgenerated Thu Feb 21 13:45:38 CST 2013
     */
    int updateByExampleSelective(AccountLog record, AccountLogExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_account_log
     *
     * @ibatorgenerated Thu Feb 21 13:45:38 CST 2013
     */
    int updateByExample(AccountLog record, AccountLogExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_account_log
     *
     * @ibatorgenerated Thu Feb 21 13:45:38 CST 2013
     */
    int updateByPrimaryKeySelective(AccountLog record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_account_log
     *
     * @ibatorgenerated Thu Feb 21 13:45:38 CST 2013
     */
    int updateByPrimaryKey(AccountLog record);
}