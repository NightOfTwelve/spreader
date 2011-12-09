package com.nali.spreader.dao.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.nali.spreader.dao.IPhotoDao;

@Repository
public class PhotoDaoImpl implements IPhotoDao {
	@Autowired
    private SqlMapClientTemplate sqlMap;

	@Override
	public Date selectMaxCreateTime() {
		return (Date) sqlMap.queryForObject("spreader_photo.getMaxCreateTime");
	}
}
