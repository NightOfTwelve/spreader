package com.nali.spreader.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nali.common.model.Limit;
import com.nali.spreader.dao.ICaptchaDao;
import com.nali.spreader.dao.ICrudCaptchaDao;
import com.nali.spreader.model.Captcha;
import com.nali.spreader.model.CaptchaExample;

@Service
public class CaptchaService implements ICaptchaService {
	@Autowired
	private ICrudCaptchaDao crudCaptchaDao;
	@Autowired
	private ICaptchaDao captchaDao;

	@Override
	public List<Captcha> assignCaptcha(Long clientId, int count) {//TODO 同步锁问题
		List<Captcha> captchas;
		synchronized (this) {
			CaptchaExample example = new CaptchaExample();
			example.createCriteria().andExpireTimeGreaterThan(new Date())
			.andTypeEqualTo(Captcha.TYPE_MANUAL);
			example.setLimit(Limit.newInstanceForLimit(0, count));
			example.setOrderByClause("expire_time asc");
			captchas = crudCaptchaDao.selectByExampleWithBLOBs(example);
			if(captchas.size()==0) {
				return Collections.emptyList();
			}
			List<Long> ids = new ArrayList<Long>(captchas.size());
			for (Captcha captcha : captchas) {
				ids.add(captcha.getId());
			}
			Captcha record = new Captcha();
			record.setHandleClient(clientId);
			record.setType(Captcha.TYPE_HANDLING);
			example = new CaptchaExample();
			example.createCriteria().andIdIn(ids);
			crudCaptchaDao.updateByExampleWithoutBLOBs(record, example);
		}
		return captchas;
	}

	@Override
	public Captcha get(Long id) {
		return crudCaptchaDao.selectByPrimaryKey(id);
	}

	@Override
	public Long save(Captcha captcha) {
		return captchaDao.insertCaptcha(captcha);
	}

	@Override
	public void updateResult(Long id, String code, Long clientId) {
		Captcha record = new Captcha();
		record.setId(id);
		record.setHandleTime(new Date());
		record.setType(Captcha.TYPE_FINISH);
		record.setResult(code);
		record.setHandleClient(clientId);
		crudCaptchaDao.updateByPrimaryKeySelective(record);
	}

}
