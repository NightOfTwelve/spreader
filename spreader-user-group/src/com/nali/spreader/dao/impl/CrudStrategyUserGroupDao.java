package com.nali.spreader.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.nali.spreader.dao.ICrudStrategyUserGroupDao;
import com.nali.spreader.model.StrategyUserGroup;
import com.nali.spreader.model.StrategyUserGroupExample;

@Repository
public class CrudStrategyUserGroupDao implements ICrudStrategyUserGroupDao {
    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database table spreader.tb_strategy_user_group
     *
     * @ibatorgenerated Fri Jan 13 17:58:00 CST 2012
     */
    @Autowired
    private SqlMapClientTemplate sqlMap;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_strategy_user_group
     *
     * @ibatorgenerated Fri Jan 13 17:58:00 CST 2012
     */
    public CrudStrategyUserGroupDao() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_strategy_user_group
     *
     * @ibatorgenerated Fri Jan 13 17:58:00 CST 2012
     */
    public int countByExample(StrategyUserGroupExample example) {
        Integer count = (Integer)  getSqlMapClientTemplate().queryForObject("spreader_tb_strategy_user_group.ibatorgenerated_countByExample", example);
        return count;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_strategy_user_group
     *
     * @ibatorgenerated Fri Jan 13 17:58:00 CST 2012
     */
    public int deleteByExample(StrategyUserGroupExample example) {
        int rows = getSqlMapClientTemplate().delete("spreader_tb_strategy_user_group.ibatorgenerated_deleteByExample", example);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_strategy_user_group
     *
     * @ibatorgenerated Fri Jan 13 17:58:00 CST 2012
     */
    public int deleteByPrimaryKey(Long sid) {
        StrategyUserGroup key = new StrategyUserGroup();
        key.setSid(sid);
        int rows = getSqlMapClientTemplate().delete("spreader_tb_strategy_user_group.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_strategy_user_group
     *
     * @ibatorgenerated Fri Jan 13 17:58:00 CST 2012
     */
    public void insert(StrategyUserGroup record) {
        getSqlMapClientTemplate().insert("spreader_tb_strategy_user_group.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_strategy_user_group
     *
     * @ibatorgenerated Fri Jan 13 17:58:00 CST 2012
     */
    public void insertSelective(StrategyUserGroup record) {
        getSqlMapClientTemplate().insert("spreader_tb_strategy_user_group.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_strategy_user_group
     *
     * @ibatorgenerated Fri Jan 13 17:58:00 CST 2012
     */
    @SuppressWarnings("unchecked")
    public List<StrategyUserGroup> selectByExample(StrategyUserGroupExample example) {
        List<StrategyUserGroup> list = getSqlMapClientTemplate().queryForList("spreader_tb_strategy_user_group.ibatorgenerated_selectByExample", example);
        return list;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_strategy_user_group
     *
     * @ibatorgenerated Fri Jan 13 17:58:00 CST 2012
     */
    public StrategyUserGroup selectByPrimaryKey(Long sid) {
        StrategyUserGroup key = new StrategyUserGroup();
        key.setSid(sid);
        StrategyUserGroup record = (StrategyUserGroup) getSqlMapClientTemplate().queryForObject("spreader_tb_strategy_user_group.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_strategy_user_group
     *
     * @ibatorgenerated Fri Jan 13 17:58:00 CST 2012
     */
    public int updateByExampleSelective(StrategyUserGroup record, StrategyUserGroupExample example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("spreader_tb_strategy_user_group.ibatorgenerated_updateByExampleSelective", parms);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_strategy_user_group
     *
     * @ibatorgenerated Fri Jan 13 17:58:00 CST 2012
     */
    public int updateByExample(StrategyUserGroup record, StrategyUserGroupExample example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("spreader_tb_strategy_user_group.ibatorgenerated_updateByExample", parms);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_strategy_user_group
     *
     * @ibatorgenerated Fri Jan 13 17:58:00 CST 2012
     */
    public int updateByPrimaryKeySelective(StrategyUserGroup record) {
        int rows = getSqlMapClientTemplate().update("spreader_tb_strategy_user_group.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_strategy_user_group
     *
     * @ibatorgenerated Fri Jan 13 17:58:00 CST 2012
     */
    public int updateByPrimaryKey(StrategyUserGroup record) {
        int rows = getSqlMapClientTemplate().update("spreader_tb_strategy_user_group.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_strategy_user_group
     *
     * @ibatorgenerated Fri Jan 13 17:58:00 CST 2012
     */
    public SqlMapClientTemplate getSqlMapClientTemplate() {
        return sqlMap;
    }

    /**
     * This class was generated by Apache iBATIS ibator.
     * This class corresponds to the database table spreader.tb_strategy_user_group
     *
     * @ibatorgenerated Fri Jan 13 17:58:00 CST 2012
     */
    private static class UpdateByExampleParms extends StrategyUserGroupExample {
        private Object record;

        public UpdateByExampleParms(Object record, StrategyUserGroupExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}