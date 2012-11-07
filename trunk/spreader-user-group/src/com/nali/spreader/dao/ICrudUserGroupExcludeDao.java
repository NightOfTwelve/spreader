package com.nali.spreader.dao;

import com.nali.spreader.model.UserGroupExclude;
import com.nali.spreader.model.UserGroupExcludeExample;
import java.util.List;

public interface ICrudUserGroupExcludeDao {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_user_group_exclude
     *
     * @ibatorgenerated Mon Nov 05 14:37:30 CST 2012
     */
    int countByExample(UserGroupExcludeExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_user_group_exclude
     *
     * @ibatorgenerated Mon Nov 05 14:37:30 CST 2012
     */
    int deleteByExample(UserGroupExcludeExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_user_group_exclude
     *
     * @ibatorgenerated Mon Nov 05 14:37:30 CST 2012
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_user_group_exclude
     *
     * @ibatorgenerated Mon Nov 05 14:37:30 CST 2012
     */
    Long insert(UserGroupExclude record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_user_group_exclude
     *
     * @ibatorgenerated Mon Nov 05 14:37:30 CST 2012
     */
    Long insertSelective(UserGroupExclude record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_user_group_exclude
     *
     * @ibatorgenerated Mon Nov 05 14:37:30 CST 2012
     */
    List<UserGroupExclude> selectByExample(UserGroupExcludeExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_user_group_exclude
     *
     * @ibatorgenerated Mon Nov 05 14:37:30 CST 2012
     */
    UserGroupExclude selectByPrimaryKey(Long id);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_user_group_exclude
     *
     * @ibatorgenerated Mon Nov 05 14:37:30 CST 2012
     */
    int updateByExampleSelective(UserGroupExclude record, UserGroupExcludeExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_user_group_exclude
     *
     * @ibatorgenerated Mon Nov 05 14:37:30 CST 2012
     */
    int updateByExample(UserGroupExclude record, UserGroupExcludeExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_user_group_exclude
     *
     * @ibatorgenerated Mon Nov 05 14:37:30 CST 2012
     */
    int updateByPrimaryKeySelective(UserGroupExclude record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_user_group_exclude
     *
     * @ibatorgenerated Mon Nov 05 14:37:30 CST 2012
     */
    int updateByPrimaryKey(UserGroupExclude record);
}