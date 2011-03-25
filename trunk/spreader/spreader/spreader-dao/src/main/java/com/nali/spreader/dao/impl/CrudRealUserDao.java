package com.nali.spreader.dao.impl;

import com.nali.spreader.dao.ICrudRealUserDao;
import com.nali.spreader.model.RealUser;
import com.nali.spreader.model.RealUserExample;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

@Repository
public class CrudRealUserDao implements ICrudRealUserDao {
    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database table ebuy.tb_real_user
     *
     * @ibatorgenerated Fri Mar 25 19:27:43 CST 2011
     */
    @Autowired
    private SqlMapClientTemplate sqlMap;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table ebuy.tb_real_user
     *
     * @ibatorgenerated Fri Mar 25 19:27:43 CST 2011
     */
    public CrudRealUserDao() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table ebuy.tb_real_user
     *
     * @ibatorgenerated Fri Mar 25 19:27:43 CST 2011
     */
    public int countByExample(RealUserExample example) {
        Integer count = (Integer)  getSqlMapClientTemplate().queryForObject("ebuy_tb_real_user.ibatorgenerated_countByExample", example);
        return count.intValue();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table ebuy.tb_real_user
     *
     * @ibatorgenerated Fri Mar 25 19:27:43 CST 2011
     */
    public int deleteByExample(RealUserExample example) {
        int rows = getSqlMapClientTemplate().delete("ebuy_tb_real_user.ibatorgenerated_deleteByExample", example);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table ebuy.tb_real_user
     *
     * @ibatorgenerated Fri Mar 25 19:27:43 CST 2011
     */
    public int deleteByPrimaryKey(Long uid) {
        RealUser key = new RealUser();
        key.setUid(uid);
        int rows = getSqlMapClientTemplate().delete("ebuy_tb_real_user.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table ebuy.tb_real_user
     *
     * @ibatorgenerated Fri Mar 25 19:27:43 CST 2011
     */
    public void insert(RealUser record) {
        getSqlMapClientTemplate().insert("ebuy_tb_real_user.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table ebuy.tb_real_user
     *
     * @ibatorgenerated Fri Mar 25 19:27:43 CST 2011
     */
    public void insertSelective(RealUser record) {
        getSqlMapClientTemplate().insert("ebuy_tb_real_user.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table ebuy.tb_real_user
     *
     * @ibatorgenerated Fri Mar 25 19:27:43 CST 2011
     */
    public List selectByExample(RealUserExample example) {
        List list = getSqlMapClientTemplate().queryForList("ebuy_tb_real_user.ibatorgenerated_selectByExample", example);
        return list;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table ebuy.tb_real_user
     *
     * @ibatorgenerated Fri Mar 25 19:27:43 CST 2011
     */
    public RealUser selectByPrimaryKey(Long uid) {
        RealUser key = new RealUser();
        key.setUid(uid);
        RealUser record = (RealUser) getSqlMapClientTemplate().queryForObject("ebuy_tb_real_user.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table ebuy.tb_real_user
     *
     * @ibatorgenerated Fri Mar 25 19:27:43 CST 2011
     */
    public int updateByExampleSelective(RealUser record, RealUserExample example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("ebuy_tb_real_user.ibatorgenerated_updateByExampleSelective", parms);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table ebuy.tb_real_user
     *
     * @ibatorgenerated Fri Mar 25 19:27:43 CST 2011
     */
    public int updateByExample(RealUser record, RealUserExample example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("ebuy_tb_real_user.ibatorgenerated_updateByExample", parms);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table ebuy.tb_real_user
     *
     * @ibatorgenerated Fri Mar 25 19:27:43 CST 2011
     */
    public int updateByPrimaryKeySelective(RealUser record) {
        int rows = getSqlMapClientTemplate().update("ebuy_tb_real_user.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table ebuy.tb_real_user
     *
     * @ibatorgenerated Fri Mar 25 19:27:43 CST 2011
     */
    public int updateByPrimaryKey(RealUser record) {
        int rows = getSqlMapClientTemplate().update("ebuy_tb_real_user.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table ebuy.tb_real_user
     *
     * @ibatorgenerated Fri Mar 25 19:27:43 CST 2011
     */
    public SqlMapClientTemplate getSqlMapClientTemplate() {
        return sqlMap;
    }

    /**
     * This class was generated by Apache iBATIS ibator.
     * This class corresponds to the database table ebuy.tb_real_user
     *
     * @ibatorgenerated Fri Mar 25 19:27:43 CST 2011
     */
    private static class UpdateByExampleParms extends RealUserExample {
        private Object record;

        public UpdateByExampleParms(Object record, RealUserExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}