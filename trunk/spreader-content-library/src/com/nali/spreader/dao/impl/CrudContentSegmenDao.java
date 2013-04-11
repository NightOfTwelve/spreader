package com.nali.spreader.dao.impl;

import com.nali.spreader.dao.ICrudContentSegmenDao;
import com.nali.spreader.data.ContentSegmen;
import com.nali.spreader.data.ContentSegmenExample;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

@Repository
public class CrudContentSegmenDao implements ICrudContentSegmenDao {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_content_segmen
     *
     * @ibatorgenerated Tue Dec 25 18:22:20 CST 2012
     */
    public CrudContentSegmenDao() {
        super();
    }

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database table spreader.tb_content_segmen
     *
     * @ibatorgenerated Tue Dec 25 18:35:52 CST 2012
     */
    @Autowired
    private SqlMapClientTemplate sqlMap;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_content_segmen
     *
     * @ibatorgenerated Tue Dec 25 18:35:52 CST 2012
     */
    public int countByExample(ContentSegmenExample example) {
        Integer count = (Integer) getSqlMapClientTemplate().queryForObject("spreader_tb_content_segmen.ibatorgenerated_countByExample", example);
        return count;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_content_segmen
     *
     * @ibatorgenerated Tue Dec 25 18:35:52 CST 2012
     */
    public int deleteByExample(ContentSegmenExample example) {
        int rows = getSqlMapClientTemplate().delete("spreader_tb_content_segmen.ibatorgenerated_deleteByExample", example);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_content_segmen
     *
     * @ibatorgenerated Tue Dec 25 18:35:52 CST 2012
     */
    public int deleteByPrimaryKey(Long id) {
        ContentSegmen key = new ContentSegmen();
        key.setId(id);
        int rows = getSqlMapClientTemplate().delete("spreader_tb_content_segmen.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_content_segmen
     *
     * @ibatorgenerated Tue Dec 25 18:35:52 CST 2012
     */
    public Long insert(ContentSegmen record) {
        Object newKey = getSqlMapClientTemplate().insert("spreader_tb_content_segmen.ibatorgenerated_insert", record);
        return (Long) newKey;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_content_segmen
     *
     * @ibatorgenerated Tue Dec 25 18:35:52 CST 2012
     */
    public Long insertSelective(ContentSegmen record) {
        Object newKey = getSqlMapClientTemplate().insert("spreader_tb_content_segmen.ibatorgenerated_insertSelective", record);
        return (Long) newKey;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_content_segmen
     *
     * @ibatorgenerated Tue Dec 25 18:35:52 CST 2012
     */
    @SuppressWarnings("unchecked")
    public List<ContentSegmen> selectByExample(ContentSegmenExample example) {
        List<ContentSegmen> list = getSqlMapClientTemplate().queryForList("spreader_tb_content_segmen.ibatorgenerated_selectByExample", example);
        return list;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_content_segmen
     *
     * @ibatorgenerated Tue Dec 25 18:35:52 CST 2012
     */
    public ContentSegmen selectByPrimaryKey(Long id) {
        ContentSegmen key = new ContentSegmen();
        key.setId(id);
        ContentSegmen record = (ContentSegmen) getSqlMapClientTemplate().queryForObject("spreader_tb_content_segmen.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_content_segmen
     *
     * @ibatorgenerated Tue Dec 25 18:35:52 CST 2012
     */
    public int updateByExampleSelective(ContentSegmen record, ContentSegmenExample example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("spreader_tb_content_segmen.ibatorgenerated_updateByExampleSelective", parms);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_content_segmen
     *
     * @ibatorgenerated Tue Dec 25 18:35:52 CST 2012
     */
    public int updateByExample(ContentSegmen record, ContentSegmenExample example) {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = getSqlMapClientTemplate().update("spreader_tb_content_segmen.ibatorgenerated_updateByExample", parms);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_content_segmen
     *
     * @ibatorgenerated Tue Dec 25 18:35:52 CST 2012
     */
    public int updateByPrimaryKeySelective(ContentSegmen record) {
        int rows = getSqlMapClientTemplate().update("spreader_tb_content_segmen.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_content_segmen
     *
     * @ibatorgenerated Tue Dec 25 18:35:52 CST 2012
     */
    public int updateByPrimaryKey(ContentSegmen record) {
        int rows = getSqlMapClientTemplate().update("spreader_tb_content_segmen.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table spreader.tb_content_segmen
     *
     * @ibatorgenerated Tue Dec 25 18:35:52 CST 2012
     */
    public SqlMapClientTemplate getSqlMapClientTemplate() {
        return sqlMap;
    }

    /**
     * This class was generated by Apache iBATIS ibator.
     * This class corresponds to the database table spreader.tb_content_segmen
     *
     * @ibatorgenerated Tue Dec 25 18:35:52 CST 2012
     */
    private static class UpdateByExampleParms extends ContentSegmenExample {

        private Object record;

        public UpdateByExampleParms(Object record, ContentSegmenExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}