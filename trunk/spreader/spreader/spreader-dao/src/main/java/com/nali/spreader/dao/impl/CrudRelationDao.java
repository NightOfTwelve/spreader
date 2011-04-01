package com.nali.spreader.dao.impl;

import com.nali.spreader.dao.ICrudRelationDao;
import com.nali.spreader.model.Relation;
import com.nali.spreader.model.RelationExample;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

@Repository
public class CrudRelationDao implements ICrudRelationDao {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_relation
     *
     * @ibatorgenerated Sat Mar 12 17:19:22 CST 2011
     */
    public CrudRelationDao() {
        super();
    }

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database table spreader.tb_relation
     *
     * @ibatorgenerated Thu Mar 31 15:39:48 CST 2011
     */
    @Autowired
    private SqlMapClientTemplate sqlMap;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_relation
     *
     * @ibatorgenerated Thu Mar 31 15:39:48 CST 2011
     */
    public int countByExample(RelationExample example) {
        Integer count = (Integer) getSqlMapClientTemplate().queryForObject("spreader_tb_relation.ibatorgenerated_countByExample", example);
        return count.intValue();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_relation
     *
     * @ibatorgenerated Thu Mar 31 15:39:48 CST 2011
     */
    public int deleteByExample(RelationExample example) {
        int rows = getSqlMapClientTemplate().delete("spreader_tb_relation.ibatorgenerated_deleteByExample", example);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_relation
     *
     * @ibatorgenerated Thu Mar 31 15:39:48 CST 2011
     */
    public void insert(Relation record) {
        getSqlMapClientTemplate().insert("spreader_tb_relation.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_relation
     *
     * @ibatorgenerated Thu Mar 31 15:39:48 CST 2011
     */
    public void insertSelective(Relation record) {
        getSqlMapClientTemplate().insert("spreader_tb_relation.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_relation
     *
     * @ibatorgenerated Thu Mar 31 15:39:48 CST 2011
     */
    public List selectByExample(RelationExample example) {
        List list = getSqlMapClientTemplate().queryForList("spreader_tb_relation.ibatorgenerated_selectByExample", example);
        return list;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_relation
     *
     * @ibatorgenerated Thu Mar 31 15:39:48 CST 2011
     */
    public int updateByExampleSelective(Relation record, RelationExample example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("spreader_tb_relation.ibatorgenerated_updateByExampleSelective", parms);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_relation
     *
     * @ibatorgenerated Thu Mar 31 15:39:48 CST 2011
     */
    public int updateByExample(Relation record, RelationExample example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("spreader_tb_relation.ibatorgenerated_updateByExample", parms);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_relation
     *
     * @ibatorgenerated Thu Mar 31 15:39:48 CST 2011
     */
    public SqlMapClientTemplate getSqlMapClientTemplate() {
        return sqlMap;
    }

    /**
     * This class was generated by Apache iBATIS ibator.
     * This class corresponds to the database table spreader.tb_relation
     *
     * @ibatorgenerated Thu Mar 31 15:39:48 CST 2011
     */
    private static class UpdateByExampleParms extends RelationExample {

        private Object record;

        public UpdateByExampleParms(Object record, RelationExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}
