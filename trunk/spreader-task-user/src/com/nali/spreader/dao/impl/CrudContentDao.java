package com.nali.spreader.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.nali.spreader.dao.ICrudContentDao;
import com.nali.spreader.data.Content;
import com.nali.spreader.data.ContentExample;

@Repository
public class CrudContentDao implements ICrudContentDao {
    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database table spreader.tb_content
     *
     * @ibatorgenerated Fri Aug 12 15:49:59 CST 2011
     */
    @Autowired
    private SqlMapClientTemplate sqlMap;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_content
     *
     * @ibatorgenerated Fri Aug 12 15:49:59 CST 2011
     */
    public CrudContentDao() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_content
     *
     * @ibatorgenerated Fri Aug 12 15:49:59 CST 2011
     */
    public int countByExample(ContentExample example) {
        Integer count = (Integer)  getSqlMapClientTemplate().queryForObject("spreader_tb_content.ibatorgenerated_countByExample", example);
        return count;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_content
     *
     * @ibatorgenerated Fri Aug 12 15:49:59 CST 2011
     */
    public int deleteByExample(ContentExample example) {
        int rows = getSqlMapClientTemplate().delete("spreader_tb_content.ibatorgenerated_deleteByExample", example);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_content
     *
     * @ibatorgenerated Fri Aug 12 15:49:59 CST 2011
     */
    public int deleteByPrimaryKey(Long id) {
        Content key = new Content();
        key.setId(id);
        int rows = getSqlMapClientTemplate().delete("spreader_tb_content.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_content
     *
     * @ibatorgenerated Fri Aug 12 15:49:59 CST 2011
     */
    public void insert(Content record) {
        getSqlMapClientTemplate().insert("spreader_tb_content.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_content
     *
     * @ibatorgenerated Fri Aug 12 15:49:59 CST 2011
     */
    public void insertSelective(Content record) {
        getSqlMapClientTemplate().insert("spreader_tb_content.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_content
     *
     * @ibatorgenerated Fri Aug 12 15:49:59 CST 2011
     */
    @SuppressWarnings("unchecked")
    public List<Content> selectByExampleWithBLOBs(ContentExample example) {
        List<Content> list = getSqlMapClientTemplate().queryForList("spreader_tb_content.ibatorgenerated_selectByExampleWithBLOBs", example);
        return list;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_content
     *
     * @ibatorgenerated Fri Aug 12 15:49:59 CST 2011
     */
    @SuppressWarnings("unchecked")
    public List<Content> selectByExampleWithoutBLOBs(ContentExample example) {
        List<Content> list = getSqlMapClientTemplate().queryForList("spreader_tb_content.ibatorgenerated_selectByExample", example);
        return list;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_content
     *
     * @ibatorgenerated Fri Aug 12 15:49:59 CST 2011
     */
    public Content selectByPrimaryKey(Long id) {
        Content key = new Content();
        key.setId(id);
        Content record = (Content) getSqlMapClientTemplate().queryForObject("spreader_tb_content.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_content
     *
     * @ibatorgenerated Fri Aug 12 15:49:59 CST 2011
     */
    public int updateByExampleSelective(Content record, ContentExample example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("spreader_tb_content.ibatorgenerated_updateByExampleSelective", parms);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_content
     *
     * @ibatorgenerated Fri Aug 12 15:49:59 CST 2011
     */
    public int updateByExampleWithBLOBs(Content record, ContentExample example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("spreader_tb_content.ibatorgenerated_updateByExampleWithBLOBs", parms);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_content
     *
     * @ibatorgenerated Fri Aug 12 15:49:59 CST 2011
     */
    public int updateByExampleWithoutBLOBs(Content record, ContentExample example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("spreader_tb_content.ibatorgenerated_updateByExample", parms);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_content
     *
     * @ibatorgenerated Fri Aug 12 15:49:59 CST 2011
     */
    public int updateByPrimaryKeySelective(Content record) {
        int rows = getSqlMapClientTemplate().update("spreader_tb_content.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_content
     *
     * @ibatorgenerated Fri Aug 12 15:49:59 CST 2011
     */
    public int updateByPrimaryKeyWithBLOBs(Content record) {
        int rows = getSqlMapClientTemplate().update("spreader_tb_content.ibatorgenerated_updateByPrimaryKeyWithBLOBs", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_content
     *
     * @ibatorgenerated Fri Aug 12 15:49:59 CST 2011
     */
    public int updateByPrimaryKeyWithoutBLOBs(Content record) {
        int rows = getSqlMapClientTemplate().update("spreader_tb_content.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_content
     *
     * @ibatorgenerated Fri Aug 12 15:49:59 CST 2011
     */
    public SqlMapClientTemplate getSqlMapClientTemplate() {
        return sqlMap;
    }

    /**
     * This class was generated by Apache iBATIS ibator.
     * This class corresponds to the database table spreader.tb_content
     *
     * @ibatorgenerated Fri Aug 12 15:49:59 CST 2011
     */
    private static class UpdateByExampleParms extends ContentExample {
        private Object record;

        public UpdateByExampleParms(Object record, ContentExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}