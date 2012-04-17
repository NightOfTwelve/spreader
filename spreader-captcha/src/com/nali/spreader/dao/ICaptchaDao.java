package com.nali.spreader.dao;

import java.util.List;
import java.util.Map;

import com.nali.spreader.model.Captcha;
import com.nali.spreader.model.CaptchaTimeDto;

public interface ICaptchaDao {

	Long insertCaptcha(Captcha captcha);

	List<Map<String, Object>> queryInputStat(CaptchaTimeDto captchaTimeDto);

}
