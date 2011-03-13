package com.nali.spreader.dao.impl;

import com.nali.spreader.dao.ICrudWorldRelationDao;
import com.nali.spreader.model.WorldRelation;
import com.nali.spreader.model.WorldRelationExample;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

@Repository
public class CrudWorldRelationDao implements ICrudWorldRelationDao {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_world_relation
     *
     * @ibatorgenerated Sat Mar 12 17:19:23 CST 2011
     */
    public CrudWorldRelationDao() {
        super();
    }

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database table spreader.tb_world_relation
     *
     * @ibatorgenerated Sun Mar 13 16:01:17 CST 2011
     */
    @Autowired
    private SqlMapClientTemplate sqlMap;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_world_relation
     *
     * @ibatorgenerated Sun Mar 13 16:01:17 CST 2011
     */
    public int countByExample(WorldRelationExample example) {
        Integer count = (Integer) getSqlMapClientTemplate().queryForObject("spreader_tb_world_relation.ibatorgenerated_countByExample", example);
        return count.intValue();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_world_relation
     *
     * @ibatorgenerated Sun Mar 13 16:01:17 CST 2011
     */
    public int deleteByExample(WorldRelationExample example) {
        int rows = getSqlMapClientTemplate().delete("spreader_tb_world_relation.ibatorgenerated_deleteByExample", example);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_world_relation
     *
     * @ibatorgenerated Sun Mar 13 16:01:17 CST 2011
     */
    public void insert(WorldRelation record) {
        getSqlMapClientTemplate().insert("spreader_tb_world_relation.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_world_relation
     *
     * @ibatorgenerated Sun Mar 13 16:01:17 CST 2011
     */
    public void insertSelective(WorldRelation record) {
        getSqlMapClientTemplate().insert("spreader_tb_world_relation.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_world_relation
     *
     * @ibatorgenerated Sun Mar 13 16:01:17 CST 2011
     */
    public List selectByExample(WorldRelationExample example) {
        List list = getSqlMapClientTemplate().queryForList("spreader_tb_world_relation.ibatorgenerated_selectByExample", example);
        return list;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_world_relation
     *
     * @ibatorgenerated Sun Mar 13 16:01:17 CST 2011
     */
    public int updateByExampleSelective(WorldRelation record, WorldRelationExample example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("spreader_tb_world_relation.ibatorgenerated_updateByExampleSelective", parms);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_world_relation
     *
     * @ibatorgenerated Sun Mar 13 16:01:17 CST 2011
     */
    public int updateByExample(WorldRelation record, WorldRelationExample example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("spreader_tb_world_relation.ibatorgenerated_updateByExample", parms);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_world_relation
     *
     * @ibatorgenerated Sun Mar 13 16:01:17 CST 2011
     */
    public SqlMapClientTemplate getSqlMapClientTemplate() {
        return sqlMap;
    }

    /**
     * This class was generated by Apache iBATIS ibator.
     * This class corresponds to the database table spreader.tb_world_relation
     *
     * @ibatorgenerated Sun Mar 13 16:01:17 CST 2011
     */
    private static class UpdateByExampleParms extends WorldRelationExample {

        private Object record;

        public UpdateByExampleParms(Object record, WorldRelationExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}
