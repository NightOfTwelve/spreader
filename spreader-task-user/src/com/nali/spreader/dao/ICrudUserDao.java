package com.nali.spreader.dao;

import com.nali.spreader.data.User;
import com.nali.spreader.data.UserExample;
import java.util.List;

public interface ICrudUserDao {
    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_user
     *
     * @ibatorgenerated Fri Dec 14 17:29:40 CST 2012
     */
    int countByExample(UserExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_user
     *
     * @ibatorgenerated Fri Dec 14 17:29:40 CST 2012
     */
    int deleteByExample(UserExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_user
     *
     * @ibatorgenerated Fri Dec 14 17:29:40 CST 2012
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_user
     *
     * @ibatorgenerated Fri Dec 14 17:29:40 CST 2012
     */
    Long insert(User record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_user
     *
     * @ibatorgenerated Fri Dec 14 17:29:40 CST 2012
     */
    Long insertSelective(User record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_user
     *
     * @ibatorgenerated Fri Dec 14 17:29:40 CST 2012
     */
    List<User> selectByExample(UserExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_user
     *
     * @ibatorgenerated Fri Dec 14 17:29:40 CST 2012
     */
    User selectByPrimaryKey(Long id);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_user
     *
     * @ibatorgenerated Fri Dec 14 17:29:40 CST 2012
     */
    int updateByExampleSelective(User record, UserExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_user
     *
     * @ibatorgenerated Fri Dec 14 17:29:40 CST 2012
     */
    int updateByExample(User record, UserExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_user
     *
     * @ibatorgenerated Fri Dec 14 17:29:40 CST 2012
     */
    int updateByPrimaryKeySelective(User record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_user
     *
     * @ibatorgenerated Fri Dec 14 17:29:41 CST 2012
     */
    int updateByPrimaryKey(User record);
}