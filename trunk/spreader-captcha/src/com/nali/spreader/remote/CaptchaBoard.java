package com.nali.spreader.remote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nali.spreader.front.ClientContext;
import com.nali.spreader.model.Captcha;
import com.nali.spreader.service.ICaptchaService;

@Service
public class CaptchaBoard implements ICaptchaBoard {
	@Autowired
	private ICaptchaService captchaService;

	@Override
	public Captcha getCaptcha() {
		ClientContext context = ClientContext.getCurrentContext();
		return captchaService.assignCaptcha(context.getClientId());
	}

	@Override
	public void postResult(Long id, String code) {
		ClientContext context = ClientContext.getCurrentContext();
		captchaService.updateResult(id, code, context.getClientId());
	}

}
