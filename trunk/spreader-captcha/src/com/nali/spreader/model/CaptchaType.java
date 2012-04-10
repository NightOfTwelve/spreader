package com.nali.spreader.model;

public enum CaptchaType {
	eyou,
	weibo,
	chinaCN,
	netEase,
	weiboAppeal,
	;
	public final int type;
	
	private CaptchaType() {
		this.type = Captcha.TYPE_MANUAL;
	}

	private CaptchaType(int type) {
		if(type<=Captcha.TYPE_MANUAL) {
			throw new IllegalArgumentException();
		}
		this.type = type;
	}
	
	public boolean isManual() {
		return Captcha.TYPE_MANUAL==type;
	}
}
