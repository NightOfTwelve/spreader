package com.nali.spreader.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.nali.spreader.dao.ICrudTaskDao;
import com.nali.spreader.model.Task;
import com.nali.spreader.model.TaskExample;

@Repository
public class CrudTaskDao implements ICrudTaskDao {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_task
     *
     * @ibatorgenerated Thu Dec 22 16:20:14 CST 2011
     */
    public CrudTaskDao() {
        super();
    }

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database table spreader.tb_task
     *
     * @ibatorgenerated Wed Mar 21 15:07:47 CST 2012
     */
    @Autowired
    private SqlMapClientTemplate sqlMap;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_task
     *
     * @ibatorgenerated Wed Mar 21 15:07:47 CST 2012
     */
    public int countByExample(TaskExample example) {
        Integer count = (Integer) getSqlMapClientTemplate().queryForObject("spreader_tb_task.ibatorgenerated_countByExample", example);
        return count;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_task
     *
     * @ibatorgenerated Wed Mar 21 15:07:47 CST 2012
     */
    public int deleteByExample(TaskExample example) {
        int rows = getSqlMapClientTemplate().delete("spreader_tb_task.ibatorgenerated_deleteByExample", example);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_task
     *
     * @ibatorgenerated Wed Mar 21 15:07:47 CST 2012
     */
    public int deleteByPrimaryKey(Long id) {
        Task key = new Task();
        key.setId(id);
        int rows = getSqlMapClientTemplate().delete("spreader_tb_task.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_task
     *
     * @ibatorgenerated Wed Mar 21 15:07:47 CST 2012
     */
    public void insert(Task record) {
        getSqlMapClientTemplate().insert("spreader_tb_task.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_task
     *
     * @ibatorgenerated Wed Mar 21 15:07:47 CST 2012
     */
    public void insertSelective(Task record) {
        getSqlMapClientTemplate().insert("spreader_tb_task.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_task
     *
     * @ibatorgenerated Wed Mar 21 15:07:47 CST 2012
     */
    @SuppressWarnings("unchecked")
    public List<Task> selectByExample(TaskExample example) {
        List<Task> list = getSqlMapClientTemplate().queryForList("spreader_tb_task.ibatorgenerated_selectByExample", example);
        return list;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_task
     *
     * @ibatorgenerated Wed Mar 21 15:07:47 CST 2012
     */
    public Task selectByPrimaryKey(Long id) {
        Task key = new Task();
        key.setId(id);
        Task record = (Task) getSqlMapClientTemplate().queryForObject("spreader_tb_task.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_task
     *
     * @ibatorgenerated Wed Mar 21 15:07:47 CST 2012
     */
    public int updateByExampleSelective(Task record, TaskExample example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("spreader_tb_task.ibatorgenerated_updateByExampleSelective", parms);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_task
     *
     * @ibatorgenerated Wed Mar 21 15:07:47 CST 2012
     */
    public int updateByExample(Task record, TaskExample example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("spreader_tb_task.ibatorgenerated_updateByExample", parms);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_task
     *
     * @ibatorgenerated Wed Mar 21 15:07:47 CST 2012
     */
    public int updateByPrimaryKeySelective(Task record) {
        int rows = getSqlMapClientTemplate().update("spreader_tb_task.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_task
     *
     * @ibatorgenerated Wed Mar 21 15:07:47 CST 2012
     */
    public int updateByPrimaryKey(Task record) {
        int rows = getSqlMapClientTemplate().update("spreader_tb_task.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_task
     *
     * @ibatorgenerated Wed Mar 21 15:07:47 CST 2012
     */
    public SqlMapClientTemplate getSqlMapClientTemplate() {
        return sqlMap;
    }

    /**
     * This class was generated by Apache iBATIS ibator.
     * This class corresponds to the database table spreader.tb_task
     *
     * @ibatorgenerated Wed Mar 21 15:07:47 CST 2012
     */
    private static class UpdateByExampleParms extends TaskExample {

        private Object record;

        public UpdateByExampleParms(Object record, TaskExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}
