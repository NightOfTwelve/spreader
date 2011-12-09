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
	void initCreatePhotoFileDirectory(String webDav, Date date, Integer k);

	/**
	 * 根据上次执行的截止日期和指定日期 执行该日期集合内的所有同步任务 指定日期默认位当天日期
	 * 
	 * @param serviceUrl
	 * @param lastTime
	 */
	void synAvatarFileDataBase(String serviceUrl, Date lastTime, Date specTime);

	/**
	 * 获取上次执行的时间戳和指定的时间创建目录
	 * 
	 * @param last
	 * @param curr
	 */
	void createLastTimeToCurrTimeDir(String serviceUri,Date last, Date curr);

}