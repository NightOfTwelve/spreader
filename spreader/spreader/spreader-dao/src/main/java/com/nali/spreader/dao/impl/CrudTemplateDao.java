package com.nali.spreader.dao.impl;

import com.nali.spreader.dao.ICrudTemplateDao;
import com.nali.spreader.model.Template;
import com.nali.spreader.model.TemplateExample;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

@Repository
public class CrudTemplateDao implements ICrudTemplateDao {
    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database table spreader.tb_template
     *
     * @ibatorgenerated Sat Mar 12 17:19:22 CST 2011
     */
    @Autowired
    private SqlMapClientTemplate sqlMap;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_template
     *
     * @ibatorgenerated Sat Mar 12 17:19:22 CST 2011
     */
    public CrudTemplateDao() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_template
     *
     * @ibatorgenerated Sat Mar 12 17:19:22 CST 2011
     */
    public int countByExample(TemplateExample example) {
        Integer count = (Integer)  getSqlMapClientTemplate().queryForObject("spreader_tb_template.ibatorgenerated_countByExample", example);
        return count.intValue();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_template
     *
     * @ibatorgenerated Sat Mar 12 17:19:22 CST 2011
     */
    public int deleteByExample(TemplateExample example) {
        int rows = getSqlMapClientTemplate().delete("spreader_tb_template.ibatorgenerated_deleteByExample", example);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_template
     *
     * @ibatorgenerated Sat Mar 12 17:19:22 CST 2011
     */
    public int deleteByPrimaryKey(Integer templateId) {
        Template key = new Template();
        key.setTemplateId(templateId);
        int rows = getSqlMapClientTemplate().delete("spreader_tb_template.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_template
     *
     * @ibatorgenerated Sat Mar 12 17:19:22 CST 2011
     */
    public void insert(Template record) {
        getSqlMapClientTemplate().insert("spreader_tb_template.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_template
     *
     * @ibatorgenerated Sat Mar 12 17:19:22 CST 2011
     */
    public void insertSelective(Template record) {
        getSqlMapClientTemplate().insert("spreader_tb_template.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_template
     *
     * @ibatorgenerated Sat Mar 12 17:19:22 CST 2011
     */
    public List selectByExample(TemplateExample example) {
        List list = getSqlMapClientTemplate().queryForList("spreader_tb_template.ibatorgenerated_selectByExample", example);
        return list;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_template
     *
     * @ibatorgenerated Sat Mar 12 17:19:22 CST 2011
     */
    public Template selectByPrimaryKey(Integer templateId) {
        Template key = new Template();
        key.setTemplateId(templateId);
        Template record = (Template) getSqlMapClientTemplate().queryForObject("spreader_tb_template.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_template
     *
     * @ibatorgenerated Sat Mar 12 17:19:22 CST 2011
     */
    public int updateByExampleSelective(Template record, TemplateExample example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("spreader_tb_template.ibatorgenerated_updateByExampleSelective", parms);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_template
     *
     * @ibatorgenerated Sat Mar 12 17:19:22 CST 2011
     */
    public int updateByExample(Template record, TemplateExample example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("spreader_tb_template.ibatorgenerated_updateByExample", parms);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_template
     *
     * @ibatorgenerated Sat Mar 12 17:19:22 CST 2011
     */
    public int updateByPrimaryKeySelective(Template record) {
        int rows = getSqlMapClientTemplate().update("spreader_tb_template.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_template
     *
     * @ibatorgenerated Sat Mar 12 17:19:22 CST 2011
     */
    public int updateByPrimaryKey(Template record) {
        int rows = getSqlMapClientTemplate().update("spreader_tb_template.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_template
     *
     * @ibatorgenerated Sat Mar 12 17:19:22 CST 2011
     */
    public SqlMapClientTemplate getSqlMapClientTemplate() {
        return sqlMap;
    }

    /**
     * This class was generated by Apache iBATIS ibator.
     * This class corresponds to the database table spreader.tb_template
     *
     * @ibatorgenerated Sat Mar 12 17:19:22 CST 2011
     */
    private static class UpdateByExampleParms extends TemplateExample {
        private Object record;

        public UpdateByExampleParms(Object record, TemplateExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}