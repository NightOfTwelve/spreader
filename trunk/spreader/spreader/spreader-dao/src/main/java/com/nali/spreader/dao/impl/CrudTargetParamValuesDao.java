package com.nali.spreader.dao.impl;

import com.nali.spreader.dao.ICrudTargetParamValuesDao;
import com.nali.spreader.model.TargetParamValues;
import com.nali.spreader.model.TargetParamValuesExample;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

@Repository
public class CrudTargetParamValuesDao implements ICrudTargetParamValuesDao {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_target_param_values
     *
     * @ibatorgenerated Sat Mar 12 17:19:22 CST 2011
     */
    public CrudTargetParamValuesDao() {
        super();
    }

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database table spreader.tb_target_param_values
     *
     * @ibatorgenerated Sun Mar 13 16:01:16 CST 2011
     */
    @Autowired
    private SqlMapClientTemplate sqlMap;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_target_param_values
     *
     * @ibatorgenerated Sun Mar 13 16:01:16 CST 2011
     */
    public int countByExample(TargetParamValuesExample example) {
        Integer count = (Integer) getSqlMapClientTemplate().queryForObject("spreader_tb_target_param_values.ibatorgenerated_countByExample", example);
        return count.intValue();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_target_param_values
     *
     * @ibatorgenerated Sun Mar 13 16:01:16 CST 2011
     */
    public int deleteByExample(TargetParamValuesExample example) {
        int rows = getSqlMapClientTemplate().delete("spreader_tb_target_param_values.ibatorgenerated_deleteByExample", example);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_target_param_values
     *
     * @ibatorgenerated Sun Mar 13 16:01:16 CST 2011
     */
    public int deleteByPrimaryKey(Long paramId) {
        TargetParamValues key = new TargetParamValues();
        key.setParamId(paramId);
        int rows = getSqlMapClientTemplate().delete("spreader_tb_target_param_values.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_target_param_values
     *
     * @ibatorgenerated Sun Mar 13 16:01:16 CST 2011
     */
    public void insert(TargetParamValues record) {
        getSqlMapClientTemplate().insert("spreader_tb_target_param_values.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_target_param_values
     *
     * @ibatorgenerated Sun Mar 13 16:01:16 CST 2011
     */
    public void insertSelective(TargetParamValues record) {
        getSqlMapClientTemplate().insert("spreader_tb_target_param_values.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_target_param_values
     *
     * @ibatorgenerated Sun Mar 13 16:01:16 CST 2011
     */
    public List selectByExample(TargetParamValuesExample example) {
        List list = getSqlMapClientTemplate().queryForList("spreader_tb_target_param_values.ibatorgenerated_selectByExample", example);
        return list;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_target_param_values
     *
     * @ibatorgenerated Sun Mar 13 16:01:16 CST 2011
     */
    public TargetParamValues selectByPrimaryKey(Long paramId) {
        TargetParamValues key = new TargetParamValues();
        key.setParamId(paramId);
        TargetParamValues record = (TargetParamValues) getSqlMapClientTemplate().queryForObject("spreader_tb_target_param_values.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_target_param_values
     *
     * @ibatorgenerated Sun Mar 13 16:01:16 CST 2011
     */
    public int updateByExampleSelective(TargetParamValues record, TargetParamValuesExample example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("spreader_tb_target_param_values.ibatorgenerated_updateByExampleSelective", parms);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_target_param_values
     *
     * @ibatorgenerated Sun Mar 13 16:01:16 CST 2011
     */
    public int updateByExample(TargetParamValues record, TargetParamValuesExample example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("spreader_tb_target_param_values.ibatorgenerated_updateByExample", parms);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_target_param_values
     *
     * @ibatorgenerated Sun Mar 13 16:01:16 CST 2011
     */
    public int updateByPrimaryKeySelective(TargetParamValues record) {
        int rows = getSqlMapClientTemplate().update("spreader_tb_target_param_values.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_target_param_values
     *
     * @ibatorgenerated Sun Mar 13 16:01:16 CST 2011
     */
    public int updateByPrimaryKey(TargetParamValues record) {
        int rows = getSqlMapClientTemplate().update("spreader_tb_target_param_values.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_target_param_values
     *
     * @ibatorgenerated Sun Mar 13 16:01:16 CST 2011
     */
    public SqlMapClientTemplate getSqlMapClientTemplate() {
        return sqlMap;
    }

    /**
     * This class was generated by Apache iBATIS ibator.
     * This class corresponds to the database table spreader.tb_target_param_values
     *
     * @ibatorgenerated Sun Mar 13 16:01:16 CST 2011
     */
    private static class UpdateByExampleParms extends TargetParamValuesExample {

        private Object record;

        public UpdateByExampleParms(Object record, TargetParamValuesExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}
