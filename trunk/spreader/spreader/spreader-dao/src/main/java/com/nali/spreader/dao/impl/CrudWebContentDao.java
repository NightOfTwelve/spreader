package com.nali.spreader.dao.impl;

import com.nali.spreader.dao.ICrudWebContentDao;
import com.nali.spreader.model.WebContent;
import com.nali.spreader.model.WebContentExample;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

@Repository
public class CrudWebContentDao implements ICrudWebContentDao {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_web_content
     *
     * @ibatorgenerated Sat Mar 12 17:19:22 CST 2011
     */
    public CrudWebContentDao() {
        super();
    }

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database table spreader.tb_web_content
     *
     * @ibatorgenerated Sun Mar 13 16:01:16 CST 2011
     */
    @Autowired
    private SqlMapClientTemplate sqlMap;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_web_content
     *
     * @ibatorgenerated Sun Mar 13 16:01:16 CST 2011
     */
    public int countByExample(WebContentExample example) {
        Integer count = (Integer) getSqlMapClientTemplate().queryForObject("spreader_tb_web_content.ibatorgenerated_countByExample", example);
        return count.intValue();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_web_content
     *
     * @ibatorgenerated Sun Mar 13 16:01:16 CST 2011
     */
    public int deleteByExample(WebContentExample example) {
        int rows = getSqlMapClientTemplate().delete("spreader_tb_web_content.ibatorgenerated_deleteByExample", example);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_web_content
     *
     * @ibatorgenerated Sun Mar 13 16:01:16 CST 2011
     */
    public int deleteByPrimaryKey(Long id) {
        WebContent key = new WebContent();
        key.setId(id);
        int rows = getSqlMapClientTemplate().delete("spreader_tb_web_content.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_web_content
     *
     * @ibatorgenerated Sun Mar 13 16:01:16 CST 2011
     */
    public void insert(WebContent record) {
        getSqlMapClientTemplate().insert("spreader_tb_web_content.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_web_content
     *
     * @ibatorgenerated Sun Mar 13 16:01:16 CST 2011
     */
    public void insertSelective(WebContent record) {
        getSqlMapClientTemplate().insert("spreader_tb_web_content.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_web_content
     *
     * @ibatorgenerated Sun Mar 13 16:01:16 CST 2011
     */
    public List selectByExample(WebContentExample example) {
        List list = getSqlMapClientTemplate().queryForList("spreader_tb_web_content.ibatorgenerated_selectByExample", example);
        return list;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_web_content
     *
     * @ibatorgenerated Sun Mar 13 16:01:16 CST 2011
     */
    public WebContent selectByPrimaryKey(Long id) {
        WebContent key = new WebContent();
        key.setId(id);
        WebContent record = (WebContent) getSqlMapClientTemplate().queryForObject("spreader_tb_web_content.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_web_content
     *
     * @ibatorgenerated Sun Mar 13 16:01:16 CST 2011
     */
    public int updateByExampleSelective(WebContent record, WebContentExample example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("spreader_tb_web_content.ibatorgenerated_updateByExampleSelective", parms);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_web_content
     *
     * @ibatorgenerated Sun Mar 13 16:01:16 CST 2011
     */
    public int updateByExample(WebContent record, WebContentExample example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("spreader_tb_web_content.ibatorgenerated_updateByExample", parms);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_web_content
     *
     * @ibatorgenerated Sun Mar 13 16:01:16 CST 2011
     */
    public int updateByPrimaryKeySelective(WebContent record) {
        int rows = getSqlMapClientTemplate().update("spreader_tb_web_content.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_web_content
     *
     * @ibatorgenerated Sun Mar 13 16:01:16 CST 2011
     */
    public int updateByPrimaryKey(WebContent record) {
        int rows = getSqlMapClientTemplate().update("spreader_tb_web_content.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_web_content
     *
     * @ibatorgenerated Sun Mar 13 16:01:16 CST 2011
     */
    public SqlMapClientTemplate getSqlMapClientTemplate() {
        return sqlMap;
    }

    /**
     * This class was generated by Apache iBATIS ibator.
     * This class corresponds to the database table spreader.tb_web_content
     *
     * @ibatorgenerated Sun Mar 13 16:01:16 CST 2011
     */
    private static class UpdateByExampleParms extends WebContentExample {

        private Object record;

        public UpdateByExampleParms(Object record, WebContentExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}
