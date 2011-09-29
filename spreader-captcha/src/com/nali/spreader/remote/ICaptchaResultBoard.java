package com.nali.spreader.remote;


public interface ICaptchaResultBoard {
	/**
	 * 提交验证码，返回id
	 */
	Long postCaptcha(byte[] data, String typeStr);
	
	/**
	 * 提交id，返回验证结果，null表示还未处理
	 */
	String getResult(Long id);
}
