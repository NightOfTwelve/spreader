package com.nali.spreader.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.nali.spreader.dao.ICrudClientTaskDao;
import com.nali.spreader.model.ClientTask;
import com.nali.spreader.model.ClientTaskExample;

@Repository
public class CrudClientTaskDao implements ICrudClientTaskDao {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_client_task
     *
     * @ibatorgenerated Wed Jul 20 16:44:35 CST 2011
     */
    public CrudClientTaskDao() {
        super();
    }

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database table spreader.tb_client_task
     *
     * @ibatorgenerated Thu Aug 25 17:53:14 CST 2011
     */
    @Autowired
    private SqlMapClientTemplate sqlMap;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_client_task
     *
     * @ibatorgenerated Thu Aug 25 17:53:14 CST 2011
     */
    public int countByExample(ClientTaskExample example) {
        Integer count = (Integer) getSqlMapClientTemplate().queryForObject("spreader_tb_client_task.ibatorgenerated_countByExample", example);
        return count;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_client_task
     *
     * @ibatorgenerated Thu Aug 25 17:53:14 CST 2011
     */
    public int deleteByExample(ClientTaskExample example) {
        int rows = getSqlMapClientTemplate().delete("spreader_tb_client_task.ibatorgenerated_deleteByExample", example);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_client_task
     *
     * @ibatorgenerated Thu Aug 25 17:53:14 CST 2011
     */
    public int deleteByPrimaryKey(Long id) {
        ClientTask key = new ClientTask();
        key.setId(id);
        int rows = getSqlMapClientTemplate().delete("spreader_tb_client_task.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_client_task
     *
     * @ibatorgenerated Thu Aug 25 17:53:14 CST 2011
     */
    public void insert(ClientTask record) {
        getSqlMapClientTemplate().insert("spreader_tb_client_task.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_client_task
     *
     * @ibatorgenerated Thu Aug 25 17:53:14 CST 2011
     */
    public void insertSelective(ClientTask record) {
        getSqlMapClientTemplate().insert("spreader_tb_client_task.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_client_task
     *
     * @ibatorgenerated Thu Aug 25 17:53:14 CST 2011
     */
    @SuppressWarnings("unchecked")
    public List<ClientTask> selectByExampleWithBLOBs(ClientTaskExample example) {
        List<ClientTask> list = getSqlMapClientTemplate().queryForList("spreader_tb_client_task.ibatorgenerated_selectByExampleWithBLOBs", example);
        return list;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_client_task
     *
     * @ibatorgenerated Thu Aug 25 17:53:14 CST 2011
     */
    @SuppressWarnings("unchecked")
    public List<ClientTask> selectByExampleWithoutBLOBs(ClientTaskExample example) {
        List<ClientTask> list = getSqlMapClientTemplate().queryForList("spreader_tb_client_task.ibatorgenerated_selectByExample", example);
        return list;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_client_task
     *
     * @ibatorgenerated Thu Aug 25 17:53:14 CST 2011
     */
    public ClientTask selectByPrimaryKey(Long id) {
        ClientTask key = new ClientTask();
        key.setId(id);
        ClientTask record = (ClientTask) getSqlMapClientTemplate().queryForObject("spreader_tb_client_task.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_client_task
     *
     * @ibatorgenerated Thu Aug 25 17:53:14 CST 2011
     */
    public int updateByExampleSelective(ClientTask record, ClientTaskExample example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("spreader_tb_client_task.ibatorgenerated_updateByExampleSelective", parms);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_client_task
     *
     * @ibatorgenerated Thu Aug 25 17:53:14 CST 2011
     */
    public int updateByExampleWithBLOBs(ClientTask record, ClientTaskExample example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("spreader_tb_client_task.ibatorgenerated_updateByExampleWithBLOBs", parms);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_client_task
     *
     * @ibatorgenerated Thu Aug 25 17:53:14 CST 2011
     */
    public int updateByExampleWithoutBLOBs(ClientTask record, ClientTaskExample example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("spreader_tb_client_task.ibatorgenerated_updateByExample", parms);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_client_task
     *
     * @ibatorgenerated Thu Aug 25 17:53:14 CST 2011
     */
    public int updateByPrimaryKeySelective(ClientTask record) {
        int rows = getSqlMapClientTemplate().update("spreader_tb_client_task.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_client_task
     *
     * @ibatorgenerated Thu Aug 25 17:53:14 CST 2011
     */
    public int updateByPrimaryKeyWithBLOBs(ClientTask record) {
        int rows = getSqlMapClientTemplate().update("spreader_tb_client_task.ibatorgenerated_updateByPrimaryKeyWithBLOBs", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_client_task
     *
     * @ibatorgenerated Thu Aug 25 17:53:14 CST 2011
     */
    public int updateByPrimaryKeyWithoutBLOBs(ClientTask record) {
        int rows = getSqlMapClientTemplate().update("spreader_tb_client_task.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_client_task
     *
     * @ibatorgenerated Thu Aug 25 17:53:14 CST 2011
     */
    public SqlMapClientTemplate getSqlMapClientTemplate() {
        return sqlMap;
    }

    /**
     * This class was generated by Apache iBATIS ibator.
     * This class corresponds to the database table spreader.tb_client_task
     *
     * @ibatorgenerated Thu Aug 25 17:53:14 CST 2011
     */
    private static class UpdateByExampleParms extends ClientTaskExample {

        private Object record;

        public UpdateByExampleParms(Object record, ClientTaskExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}
