package com.nali.spreader.service;

import java.util.List;

import com.nali.spreader.model.Captcha;

public interface ICaptchaService {

	List<Captcha> assignCaptcha(Long clientId, int count);

	void updateResult(Long id, String code, Long clientId);

	Captcha get(Long id);

	Long save(Captcha captcha);

}
