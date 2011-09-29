package com.nali.spreader.service;

import com.nali.spreader.model.Captcha;

public interface ICaptchaService {

	Captcha assignCaptcha(Long clientId);

	void updateResult(Long id, String code, Long clientId);

	Captcha get(Long id);

	Long save(Captcha captcha);

}
