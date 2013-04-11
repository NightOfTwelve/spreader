package com.nali.spreader.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.nali.spreader.dao.ICrudEmailUsingDao;
import com.nali.spreader.data.EmailUsing;
import com.nali.spreader.data.EmailUsingExample;

@Repository
public class CrudEmailUsingDao implements ICrudEmailUsingDao {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_email_using
     *
     * @ibatorgenerated Thu Nov 15 12:05:26 CST 2012
     */
    public CrudEmailUsingDao() {
        super();
    }

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database table spreader.tb_email_using
     *
     * @ibatorgenerated Thu Nov 15 19:35:27 CST 2012
     */
    @Autowired
    private SqlMapClientTemplate sqlMap;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_email_using
     *
     * @ibatorgenerated Thu Nov 15 19:35:27 CST 2012
     */
    public int countByExample(EmailUsingExample example) {
        Integer count = (Integer) getSqlMapClientTemplate().queryForObject("spreader_tb_email_using.ibatorgenerated_countByExample", example);
        return count;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_email_using
     *
     * @ibatorgenerated Thu Nov 15 19:35:27 CST 2012
     */
    public int deleteByExample(EmailUsingExample example) {
        int rows = getSqlMapClientTemplate().delete("spreader_tb_email_using.ibatorgenerated_deleteByExample", example);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_email_using
     *
     * @ibatorgenerated Thu Nov 15 19:35:27 CST 2012
     */
    public int deleteByPrimaryKey(String email) {
        EmailUsing key = new EmailUsing();
        key.setEmail(email);
        int rows = getSqlMapClientTemplate().delete("spreader_tb_email_using.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_email_using
     *
     * @ibatorgenerated Thu Nov 15 19:35:27 CST 2012
     */
    public void insert(EmailUsing record) {
        getSqlMapClientTemplate().insert("spreader_tb_email_using.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_email_using
     *
     * @ibatorgenerated Thu Nov 15 19:35:27 CST 2012
     */
    public void insertSelective(EmailUsing record) {
        getSqlMapClientTemplate().insert("spreader_tb_email_using.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_email_using
     *
     * @ibatorgenerated Thu Nov 15 19:35:27 CST 2012
     */
    @SuppressWarnings("unchecked")
    public List<EmailUsing> selectByExample(EmailUsingExample example) {
        List<EmailUsing> list = getSqlMapClientTemplate().queryForList("spreader_tb_email_using.ibatorgenerated_selectByExample", example);
        return list;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_email_using
     *
     * @ibatorgenerated Thu Nov 15 19:35:27 CST 2012
     */
    public int updateByExampleSelective(EmailUsing record, EmailUsingExample example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("spreader_tb_email_using.ibatorgenerated_updateByExampleSelective", parms);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_email_using
     *
     * @ibatorgenerated Thu Nov 15 19:35:27 CST 2012
     */
    public int updateByExample(EmailUsing record, EmailUsingExample example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("spreader_tb_email_using.ibatorgenerated_updateByExample", parms);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_email_using
     *
     * @ibatorgenerated Thu Nov 15 19:35:27 CST 2012
     */
    public SqlMapClientTemplate getSqlMapClientTemplate() {
        return sqlMap;
    }

    /**
     * This class was generated by Apache iBATIS ibator.
     * This class corresponds to the database table spreader.tb_email_using
     *
     * @ibatorgenerated Thu Nov 15 19:35:27 CST 2012
     */
    private static class UpdateByExampleParms extends EmailUsingExample {

        private Object record;

        public UpdateByExampleParms(Object record, EmailUsingExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}