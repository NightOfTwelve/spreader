package com.nali.spreader.util;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * 载入JS时候加上时间戳防止JS缓存
 * 
 * @author xiefei
 * 
 */
public class UriCacheRefreshUtil {
	private static final Logger LOGGER = Logger
			.getLogger(UriCacheRefreshUtil.class);

	/**
	 * 给每个URL加上时间戳防止缓存
	 * 
	 * @param url
	 * @return
	 */
	public static String addTimestamp(String url) {
		if (StringUtils.isNotEmpty(url)) {
			StringBuffer buff = new StringBuffer(url);
			Long ts = new Date().getTime();
			buff.append("?_random=");
			buff.append(ts);
			String tUri = buff.toString();
			LOGGER.info("Refresh:" + tUri);
			return tUri;
		} else {
			return null;
		}
	}
}
