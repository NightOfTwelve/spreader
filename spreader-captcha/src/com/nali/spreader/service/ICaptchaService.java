package com.nali.spreader.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.nali.spreader.model.Captcha;

public interface ICaptchaService {
	String SKIPPED_RESULT = "^skip$";
	String EXPIRE_RESULT = "^expire$";

	List<Captcha> assignCaptcha(Long clientId, int count);

	void updateResult(Long id, String code, Long clientId);

	Captcha get(Long id);

	Long save(Captcha captcha);

	List<Map<String, Object>> queryInputStat(Long clientId, Date from, Date to);

}
