package com.nali.spreader.dao;

import com.nali.spreader.data.UserTag;
import com.nali.spreader.data.UserTagExample;
import java.util.List;

public interface ICrudUserTagDao {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_user_tag
     *
     * @ibatorgenerated Fri Oct 21 16:02:22 CST 2011
     */
    int countByExample(UserTagExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_user_tag
     *
     * @ibatorgenerated Fri Oct 21 16:02:22 CST 2011
     */
    int deleteByExample(UserTagExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_user_tag
     *
     * @ibatorgenerated Fri Oct 21 16:02:22 CST 2011
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_user_tag
     *
     * @ibatorgenerated Fri Oct 21 16:02:22 CST 2011
     */
    void insert(UserTag record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_user_tag
     *
     * @ibatorgenerated Fri Oct 21 16:02:22 CST 2011
     */
    void insertSelective(UserTag record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_user_tag
     *
     * @ibatorgenerated Fri Oct 21 16:02:22 CST 2011
     */
    List<UserTag> selectByExample(UserTagExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_user_tag
     *
     * @ibatorgenerated Fri Oct 21 16:02:22 CST 2011
     */
    UserTag selectByPrimaryKey(Long id);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_user_tag
     *
     * @ibatorgenerated Fri Oct 21 16:02:22 CST 2011
     */
    int updateByExampleSelective(UserTag record, UserTagExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_user_tag
     *
     * @ibatorgenerated Fri Oct 21 16:02:22 CST 2011
     */
    int updateByExample(UserTag record, UserTagExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_user_tag
     *
     * @ibatorgenerated Fri Oct 21 16:02:22 CST 2011
     */
    int updateByPrimaryKeySelective(UserTag record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_user_tag
     *
     * @ibatorgenerated Fri Oct 21 16:02:22 CST 2011
     */
    int updateByPrimaryKey(UserTag record);
}