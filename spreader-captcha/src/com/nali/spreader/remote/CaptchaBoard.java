package com.nali.spreader.remote;

import java.util.List;

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
	public void postResult(Long id, String code) {
		ClientContext context = ClientContext.getCurrentContext();
		captchaService.updateResult(id, code, context.getClientId());
	}

	@Override
	public List<Captcha> getCaptcha(int count) {
		ClientContext context = ClientContext.getCurrentContext();
		List<Captcha> captchas = captchaService.assignCaptcha(context.getClientId(), count);
		for (Captcha captcha : captchas) {
			captcha.setTimeLeftMillis(captcha.getExpireTime().getTime()-System.currentTimeMillis());
		}
		return captchas;
	}

}
