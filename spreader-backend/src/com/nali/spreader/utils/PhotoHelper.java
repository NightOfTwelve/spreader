package com.nali.spreader.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Random;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.nali.common.util.CollectionUtils;

/**
 * 图片处理帮助类
 * 
 * @author xiefei
 * 
 */
public class PhotoHelper {
	private static final Logger logger = Logger.getLogger(PhotoHelper.class);
	// 图片服务器信息配置文件
	public static final String WEBDAV_FILE = "/avatarconfig/webDavService.properties";
	public static final String MALE_FIEL = "/avatarconfig/malePhotoType.properties";
	public static final String FEMALE_FILE = "/avatarconfig/femalePhotoType.properties";
	public static final String GENERAL_FILE = "/avatarconfig/generalPhotoType.properties";
	public static final String TYPEWEIGHT_FILE = "/avatarconfig/AvatarTypeWeight.properties";
	private static Random rd = new Random();

	/**
	 * 获取某个配置文件的键值对
	 * 
	 * @param url
	 * @return
	 */
	public static Map<Object, Object> getPropertiesMap(String url) {
		Map<Object, Object> map = CollectionUtils.newHashMap(5);
		InputStream is = PhotoHelper.class.getResourceAsStream(url);
		try {
			if (is != null) {
				Properties prop = new Properties();
				prop.load(is);
				Set<Entry<Object, Object>> set = prop.entrySet();
				for (Entry<Object, Object> entry : set) {
					map.put(entry.getKey(), entry.getValue());
				}
			} else {
				logger.info("InputStream为空,请检查配置文件");
			}
		} catch (Exception e) {
			logger.info("未能读取头像类别配置文件", e);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					logger.info(e);
				}
			}
		}
		return map;
	}

	/**
	 * 获取随机整数
	 * 
	 * @param size
	 * @return
	 */
	public static int getRandomNum(int size) {
		int t = rd.nextInt(size);
		return t;
	}

	/**
	 * 去掉URL最后一个“/”
	 * 
	 * @param url
	 * @return
	 */
	public static String splitUrlEnd(String url) {
		String u = null;
		if (StringUtils.isNotEmpty(url)) {
			u = url.substring(0, url.length() - 1);
		}
		return u;
	}

	/**
	 * 构造一个真实的图片URL
	 * 
	 * @param fileUri
	 * @return
	 */
	public static String formatPhotoUrl(String fileUri) {
		String realUrl = null;
		if (StringUtils.isNotEmpty(fileUri)) {
			Map<Object, Object> map = getPropertiesMap(WEBDAV_FILE);
			if (map != null) {
				StringBuffer buff = new StringBuffer();
				// String http = map.get("url").toString();
				// 构造服务端使用的URL
				String http = map.get("imageUrl").toString();
				String tmp = splitUrlEnd(http);
				buff.append(tmp);
				buff.append(fileUri);
				realUrl = buff.toString();
			} else {
				logger.info("服务器地址参数获取失败,请检查配置文件");
			}
		} else {
			logger.info("文件地址为空,不能转换");
		}
		return realUrl;
	}

	public static void main(String arge[]) {
		System.out.println(PhotoHelper.formatPhotoUrl("/2011/02/11.jsp"));
	}
}
