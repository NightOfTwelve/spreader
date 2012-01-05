package com.nali.spreader.remote;

import java.util.List;

import com.nali.spreader.model.Captcha;

public interface ICaptchaBoard {
	/**
	 * 获取验证码数据，包含id和图片的字节数组
	 */
	List<Captcha> getCaptcha(int count);

	/**
	 * 返回验证码识别结果
	 */
	void postResult(Long id, String code);
}
