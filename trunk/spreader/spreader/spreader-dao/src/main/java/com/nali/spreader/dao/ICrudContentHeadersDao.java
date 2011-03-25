package com.nali.spreader.dao;

import com.nali.spreader.model.ContentHeadersExample;
import com.nali.spreader.model.TbContentHeadersKey;
import java.util.List;

public interface ICrudContentHeadersDao {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_content_headers
     *
     * @ibatorgenerated Fri Mar 25 20:53:45 CST 2011
     */
    int countByExample(ContentHeadersExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_content_headers
     *
     * @ibatorgenerated Fri Mar 25 20:53:45 CST 2011
     */
    int deleteByExample(ContentHeadersExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_content_headers
     *
     * @ibatorgenerated Fri Mar 25 20:53:45 CST 2011
     */
    int deleteByPrimaryKey(TbContentHeadersKey key);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_content_headers
     *
     * @ibatorgenerated Fri Mar 25 20:53:45 CST 2011
     */
    void insert(TbContentHeadersKey record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_content_headers
     *
     * @ibatorgenerated Fri Mar 25 20:53:45 CST 2011
     */
    void insertSelective(TbContentHeadersKey record);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_content_headers
     *
     * @ibatorgenerated Fri Mar 25 20:53:45 CST 2011
     */
    List selectByExample(ContentHeadersExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_content_headers
     *
     * @ibatorgenerated Fri Mar 25 20:53:45 CST 2011
     */
    int updateByExampleSelective(TbContentHeadersKey record, ContentHeadersExample example);

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_content_headers
     *
     * @ibatorgenerated Fri Mar 25 20:53:45 CST 2011
     */
    int updateByExample(TbContentHeadersKey record, ContentHeadersExample example);
}
