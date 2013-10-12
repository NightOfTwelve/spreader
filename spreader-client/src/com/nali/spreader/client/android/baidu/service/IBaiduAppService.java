package com.nali.spreader.client.android.baidu.service;

public interface IBaiduAppService {

	/**
	 * 获取当前的java.library.path
	 * 
	 * @return
	 */
	String getJavaLibPath();

	/**
	 * 获取百度native加密后的数据
	 * 
	 * @param data
	 * @return
	 */
	String B64Encode(String data);

}
