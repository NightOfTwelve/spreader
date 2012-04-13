package com.nali.spreader.remote;

import java.util.Date;
import java.util.Map;

public interface ICaptchaResultBoard {
	/**
	 * 提交验证码，返回id
	 */
	Long postCaptcha(byte[] data, String typeStr, Long taskId, Integer seq);

	/**
	 * 提交id，返回验证结果，null表示还未处理
	 */
	String getResult(Long id);
	
	/**
	 * result: null no data
	 * result-key: pic_type:图片代号, s_cnt:成功数, a_cnt:总数, s_rate:成功率
	 */
	Map<String, Object> queryInputStat(Date from, Date to);
}
