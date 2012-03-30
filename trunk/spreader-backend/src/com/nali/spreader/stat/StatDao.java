package com.nali.spreader.stat;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class StatDao implements IStatDao {
    @Autowired
    private SqlMapClientTemplate sqlMap;
    
    @Override
    @SuppressWarnings("unchecked")
	public List<Map<String, Object>> select(String sqlmap, Object dto) {
    	return sqlMap.queryForList(sqlmap, dto);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> select(String sqlmap) {
    	return sqlMap.queryForList(sqlmap);
    }
}
