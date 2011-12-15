/*
 * CMS Framework Code
 * All rights reserved
 */
package com.nali.spreader.util.web;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.nali.common.util.PropertyConfig;

/**
 * @author kenny created on 2010-5-27
 */
public class StaticConfig {

	private static int isInitialized = 0;
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(StaticConfig.class);

	private static Properties configInfo = null;

	// 静态文件路径前缀
	private static String prefix_css = null;
	private static String prefix_js = null;
	private static String prefix_image = null;
	private static String prefix_swf = null;
	private static String prefix_static = null;
	private static String prefix_world = null;
	private static String prefix_www = null;

	static {
		init();
	}
	private synchronized static void init() {
		try {
			InputStream in = PropertyConfig.class.getClassLoader()
					.getResourceAsStream("staticConfig.properties");
			if (in == null) {
				throw new RuntimeException(
						"The file: staticConfig.properties can't be found in Classpath.");
			}

			Properties prop = new Properties();
			prop.load(in);

			configInfo = prop;

			prefix_css = prop.getProperty("prefix_css");
			prefix_js = prop.getProperty("prefix_js");
			prefix_image = prop.getProperty("prefix_image");
			prefix_swf = prop.getProperty("prefix_swf");
			prefix_static = prop.getProperty("prefix_static");
			prefix_world = prop.getProperty("prefix_world");
			prefix_www = prop.getProperty("prefix_www");
			isInitialized = 1;
		} catch (IOException e) {
			logger.error("Error occurs: " + e.getMessage(), e);
		}
	}

	//
	/**
	 * 转换静态文件的url为实际部署的路径.<br>
	 * '/js/scene/a.js' --> 'http://ddd.ccc.c/js/scene.js' <br>
	 * 'js/ss.js' --> 'js/ss.js'
	 * 
	 * @param key
	 * @return
	 */
	public static String u(String key) {
		if (key == null || "".equals(key)) {
			return key;
		}
		key = key.replace("\\", "/");
		int startIndex = key.indexOf("/");
		int endIndex = key.indexOf("/", startIndex + 1);
		if (startIndex != -1 && endIndex != -1
				&& startIndex != key.length() - 1 && startIndex < endIndex) {
			String suffix = key.substring(startIndex + 1, endIndex);
			if (suffix.equalsIgnoreCase("css")) {
				return prefix_css + key;
			} else if (suffix.equalsIgnoreCase("js")) {
				return prefix_js + key;// http://js.9nali.com
			} else if (suffix.equalsIgnoreCase("swf")) {
				return prefix_swf + key;
			} else if (suffix.equalsIgnoreCase("i")) {
				return prefix_image + key;
			} else {
				return prefix_static + key;
			}
		} else {
			// modified by morgan
			return configInfo.getProperty(key, key);
		}
	}

	public static String getPrefixCss() {
		return prefix_css;
	}

	public static String getPrefixJs() {
		return prefix_js;
	}

	public static String getPrefixSwf() {
		return prefix_swf;
	}

	public static String getPrefixStatic() {
		return prefix_static;
	}

	public static String getPrefixImage() {
		return prefix_image;
	}

	public StaticConfig() {
		if (isInitialized == 0) {
			init();
		}
	}
	
	/**
	 * @return 那里世界的域名地址:http://world.9nali.com
	 */
	public static String world() {
		return prefix_world;
	}
	
	/**
	 * @return 那里社区的域名地址
	 */
	public static String www() {
		return prefix_www;
	}
	

	/**
	 * @return 那里社区的域名地址
	 */
	public static String pay() {
		return prefix_www;
	}
}
