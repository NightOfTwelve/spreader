package com.nali.spreader.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.nali.spreader.dao.ICrudCaptchaDao;
import com.nali.spreader.model.Captcha;
import com.nali.spreader.model.CaptchaExample;

@Repository
public class CrudCaptchaDao implements ICrudCaptchaDao {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_captcha
     *
     * @ibatorgenerated Thu Sep 08 17:07:29 CST 2011
     */
    public CrudCaptchaDao() {
        super();
    }

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database table spreader.tb_captcha
     *
     * @ibatorgenerated Thu Jan 05 11:29:34 CST 2012
     */
    @Autowired
    private SqlMapClientTemplate sqlMap;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_captcha
     *
     * @ibatorgenerated Thu Jan 05 11:29:34 CST 2012
     */
    public int countByExample(CaptchaExample example) {
        Integer count = (Integer) getSqlMapClientTemplate().queryForObject("spreader_tb_captcha.ibatorgenerated_countByExample", example);
        return count;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_captcha
     *
     * @ibatorgenerated Thu Jan 05 11:29:34 CST 2012
     */
    public int deleteByExample(CaptchaExample example) {
        int rows = getSqlMapClientTemplate().delete("spreader_tb_captcha.ibatorgenerated_deleteByExample", example);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_captcha
     *
     * @ibatorgenerated Thu Jan 05 11:29:34 CST 2012
     */
    public int deleteByPrimaryKey(Long id) {
        Captcha key = new Captcha();
        key.setId(id);
        int rows = getSqlMapClientTemplate().delete("spreader_tb_captcha.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_captcha
     *
     * @ibatorgenerated Thu Jan 05 11:29:34 CST 2012
     */
    public void insert(Captcha record) {
        getSqlMapClientTemplate().insert("spreader_tb_captcha.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_captcha
     *
     * @ibatorgenerated Thu Jan 05 11:29:34 CST 2012
     */
    public void insertSelective(Captcha record) {
        getSqlMapClientTemplate().insert("spreader_tb_captcha.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_captcha
     *
     * @ibatorgenerated Thu Jan 05 11:29:34 CST 2012
     */
    @SuppressWarnings("unchecked")
    public List<Captcha> selectByExampleWithBLOBs(CaptchaExample example) {
        List<Captcha> list = getSqlMapClientTemplate().queryForList("spreader_tb_captcha.ibatorgenerated_selectByExampleWithBLOBs", example);
        return list;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_captcha
     *
     * @ibatorgenerated Thu Jan 05 11:29:34 CST 2012
     */
    @SuppressWarnings("unchecked")
    public List<Captcha> selectByExampleWithoutBLOBs(CaptchaExample example) {
        List<Captcha> list = getSqlMapClientTemplate().queryForList("spreader_tb_captcha.ibatorgenerated_selectByExample", example);
        return list;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_captcha
     *
     * @ibatorgenerated Thu Jan 05 11:29:34 CST 2012
     */
    public Captcha selectByPrimaryKey(Long id) {
        Captcha key = new Captcha();
        key.setId(id);
        Captcha record = (Captcha) getSqlMapClientTemplate().queryForObject("spreader_tb_captcha.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_captcha
     *
     * @ibatorgenerated Thu Jan 05 11:29:34 CST 2012
     */
    public int updateByExampleSelective(Captcha record, CaptchaExample example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("spreader_tb_captcha.ibatorgenerated_updateByExampleSelective", parms);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_captcha
     *
     * @ibatorgenerated Thu Jan 05 11:29:34 CST 2012
     */
    public int updateByExampleWithBLOBs(Captcha record, CaptchaExample example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("spreader_tb_captcha.ibatorgenerated_updateByExampleWithBLOBs", parms);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_captcha
     *
     * @ibatorgenerated Thu Jan 05 11:29:34 CST 2012
     */
    public int updateByExampleWithoutBLOBs(Captcha record, CaptchaExample example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("spreader_tb_captcha.ibatorgenerated_updateByExample", parms);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_captcha
     *
     * @ibatorgenerated Thu Jan 05 11:29:34 CST 2012
     */
    public int updateByPrimaryKeySelective(Captcha record) {
        int rows = getSqlMapClientTemplate().update("spreader_tb_captcha.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_captcha
     *
     * @ibatorgenerated Thu Jan 05 11:29:34 CST 2012
     */
    public int updateByPrimaryKeyWithBLOBs(Captcha record) {
        int rows = getSqlMapClientTemplate().update("spreader_tb_captcha.ibatorgenerated_updateByPrimaryKeyWithBLOBs", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_captcha
     *
     * @ibatorgenerated Thu Jan 05 11:29:34 CST 2012
     */
    public int updateByPrimaryKeyWithoutBLOBs(Captcha record) {
        int rows = getSqlMapClientTemplate().update("spreader_tb_captcha.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_captcha
     *
     * @ibatorgenerated Thu Jan 05 11:29:34 CST 2012
     */
    public SqlMapClientTemplate getSqlMapClientTemplate() {
        return sqlMap;
    }

    /**
     * This class was generated by Apache iBATIS ibator.
     * This class corresponds to the database table spreader.tb_captcha
     *
     * @ibatorgenerated Thu Jan 05 11:29:34 CST 2012
     */
    private static class UpdateByExampleParms extends CaptchaExample {

        private Object record;

        public UpdateByExampleParms(Object record, CaptchaExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}
