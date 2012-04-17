package com.nali.spreader.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.nali.spreader.dao.ICaptchaDao;
import com.nali.spreader.model.Captcha;
import com.nali.spreader.model.CaptchaTimeDto;

@Repository
public class CaptchaDao implements ICaptchaDao {
    @Autowired
    private SqlMapClientTemplate sqlMap;
	
    @Override
	public Long insertCaptcha(Captcha captcha) {
		return (Long) sqlMap.insert("spreader.insertCaptcha", captcha);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> queryInputStat(CaptchaTimeDto captchaTimeDto) {
		return sqlMap.queryForList("spreader.captchaInput", captchaTimeDto);
	}

}
