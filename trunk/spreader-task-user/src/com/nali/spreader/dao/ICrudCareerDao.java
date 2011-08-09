package com.nali.spreader.dao;

import com.nali.spreader.data.Career;
import com.nali.spreader.data.CareerExample;
import java.util.List;

public interface ICrudCareerDao {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_career
     *
     * @ibatorgenerated Thu Aug 04 10:00:33 CST 2011
     */
    int countByExample(CareerExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_career
     *
     * @ibatorgenerated Thu Aug 04 10:00:33 CST 2011
     */
    int deleteByExample(CareerExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_career
     *
     * @ibatorgenerated Thu Aug 04 10:00:33 CST 2011
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_career
     *
     * @ibatorgenerated Thu Aug 04 10:00:33 CST 2011
     */
    void insert(Career record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_career
     *
     * @ibatorgenerated Thu Aug 04 10:00:33 CST 2011
     */
    void insertSelective(Career record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_career
     *
     * @ibatorgenerated Thu Aug 04 10:00:33 CST 2011
     */
    List<Career> selectByExample(CareerExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_career
     *
     * @ibatorgenerated Thu Aug 04 10:00:33 CST 2011
     */
    Career selectByPrimaryKey(Long id);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_career
     *
     * @ibatorgenerated Thu Aug 04 10:00:33 CST 2011
     */
    int updateByExampleSelective(Career record, CareerExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_career
     *
     * @ibatorgenerated Thu Aug 04 10:00:33 CST 2011
     */
    int updateByExample(Career record, CareerExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_career
     *
     * @ibatorgenerated Thu Aug 04 10:00:33 CST 2011
     */
    int updateByPrimaryKeySelective(Career record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_career
     *
     * @ibatorgenerated Thu Aug 04 10:00:33 CST 2011
     */
    int updateByPrimaryKey(Career record);
}
