package com.nali.spreader.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 头像库管理相关Service
 * 
 * @author xiefei
 * 
 */
public interface IAvatarFileManageService {
	/**
	 * 获取某个配置文件的键值对
	 * 
	 * @param url
	 * @return
	 */
	Map<Object, Object> getPropertiesMap(String url);

	/**
	 * 初始化自定义天数的目录结构
	 * 
	 * @param date
	 * @param k
	 */
	void initCreatePhotoFileDirectory(Date date, Integer k);

}
