package com.nali.spreader.dao.impl;

import com.nali.spreader.dao.ICrudHelpEnumInfoDao;
import com.nali.spreader.data.HelpEnumInfo;
import com.nali.spreader.data.HelpEnumInfoExample;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

@Repository
public class CrudHelpEnumInfoDao implements ICrudHelpEnumInfoDao {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_help_enum_info
     *
     * @ibatorgenerated Thu Nov 15 16:04:32 CST 2012
     */
    public CrudHelpEnumInfoDao() {
        super();
    }

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database table spreader.tb_help_enum_info
     *
     * @ibatorgenerated Thu Nov 15 17:57:35 CST 2012
     */
    @Autowired
    private SqlMapClientTemplate sqlMap;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_help_enum_info
     *
     * @ibatorgenerated Thu Nov 15 17:57:35 CST 2012
     */
    public int countByExample(HelpEnumInfoExample example) {
        Integer count = (Integer) getSqlMapClientTemplate().queryForObject("spreader_tb_help_enum_info.ibatorgenerated_countByExample", example);
        return count;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_help_enum_info
     *
     * @ibatorgenerated Thu Nov 15 17:57:35 CST 2012
     */
    public int deleteByExample(HelpEnumInfoExample example) {
        int rows = getSqlMapClientTemplate().delete("spreader_tb_help_enum_info.ibatorgenerated_deleteByExample", example);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_help_enum_info
     *
     * @ibatorgenerated Thu Nov 15 17:57:35 CST 2012
     */
    public int deleteByPrimaryKey(Long id) {
        HelpEnumInfo key = new HelpEnumInfo();
        key.setId(id);
        int rows = getSqlMapClientTemplate().delete("spreader_tb_help_enum_info.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_help_enum_info
     *
     * @ibatorgenerated Thu Nov 15 17:57:35 CST 2012
     */
    public Long insert(HelpEnumInfo record) {
        Object newKey = getSqlMapClientTemplate().insert("spreader_tb_help_enum_info.ibatorgenerated_insert", record);
        return (Long) newKey;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_help_enum_info
     *
     * @ibatorgenerated Thu Nov 15 17:57:35 CST 2012
     */
    public Long insertSelective(HelpEnumInfo record) {
        Object newKey = getSqlMapClientTemplate().insert("spreader_tb_help_enum_info.ibatorgenerated_insertSelective", record);
        return (Long) newKey;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_help_enum_info
     *
     * @ibatorgenerated Thu Nov 15 17:57:35 CST 2012
     */
    @SuppressWarnings("unchecked")
    public List<HelpEnumInfo> selectByExample(HelpEnumInfoExample example) {
        List<HelpEnumInfo> list = getSqlMapClientTemplate().queryForList("spreader_tb_help_enum_info.ibatorgenerated_selectByExample", example);
        return list;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_help_enum_info
     *
     * @ibatorgenerated Thu Nov 15 17:57:35 CST 2012
     */
    public HelpEnumInfo selectByPrimaryKey(Long id) {
        HelpEnumInfo key = new HelpEnumInfo();
        key.setId(id);
        HelpEnumInfo record = (HelpEnumInfo) getSqlMapClientTemplate().queryForObject("spreader_tb_help_enum_info.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_help_enum_info
     *
     * @ibatorgenerated Thu Nov 15 17:57:35 CST 2012
     */
    public int updateByExampleSelective(HelpEnumInfo record, HelpEnumInfoExample example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("spreader_tb_help_enum_info.ibatorgenerated_updateByExampleSelective", parms);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_help_enum_info
     *
     * @ibatorgenerated Thu Nov 15 17:57:35 CST 2012
     */
    public int updateByExample(HelpEnumInfo record, HelpEnumInfoExample example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("spreader_tb_help_enum_info.ibatorgenerated_updateByExample", parms);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_help_enum_info
     *
     * @ibatorgenerated Thu Nov 15 17:57:35 CST 2012
     */
    public int updateByPrimaryKeySelective(HelpEnumInfo record) {
        int rows = getSqlMapClientTemplate().update("spreader_tb_help_enum_info.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_help_enum_info
     *
     * @ibatorgenerated Thu Nov 15 17:57:35 CST 2012
     */
    public int updateByPrimaryKey(HelpEnumInfo record) {
        int rows = getSqlMapClientTemplate().update("spreader_tb_help_enum_info.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_help_enum_info
     *
     * @ibatorgenerated Thu Nov 15 17:57:35 CST 2012
     */
    public SqlMapClientTemplate getSqlMapClientTemplate() {
        return sqlMap;
    }

    /**
     * This class was generated by Apache iBATIS ibator.
     * This class corresponds to the database table spreader.tb_help_enum_info
     *
     * @ibatorgenerated Thu Nov 15 17:57:35 CST 2012
     */
    private static class UpdateByExampleParms extends HelpEnumInfoExample {

        private Object record;

        public UpdateByExampleParms(Object record, HelpEnumInfoExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}
