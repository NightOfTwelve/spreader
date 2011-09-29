package com.nali.spreader.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.nali.spreader.dao.ICaptchaDao;
import com.nali.spreader.model.Captcha;

@Repository
public class CaptchaDao implements ICaptchaDao {
    @Autowired
    private SqlMapClientTemplate sqlMap;
	
    @Override
	public Long insertCaptcha(Captcha captcha) {
		return (Long) sqlMap.insert("spreader.insertCaptcha", captcha);
	}

}
