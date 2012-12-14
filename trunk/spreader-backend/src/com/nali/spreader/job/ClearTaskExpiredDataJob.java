package com.nali.spreader.job;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.configuration.AbstractConfiguration;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.nali.common.util.CollectionUtils;
import com.nali.spreader.service.IClientTaskStatService;

/**
 * 定时清理Task相关表的过期任务
 * 
 * @author xiefei
 * 
 */
@Component
@Lazy(false)
public class ClearTaskExpiredDataJob {
	private static Logger logger = Logger.getLogger(ClearTaskExpiredDataJob.class);
	@Autowired
	private IClientTaskStatService clientTaskStatService;

	@Scheduled(cron = "0 45 16 ? * *")
	public void clear() {
		Map<Integer, String[]> prop = getProperties();
		Iterator<Entry<Integer, String[]>> iter = prop.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<Integer, String[]> entry = (Map.Entry<Integer, String[]>) iter.next();
			int days = entry.getKey();
			String[] tables = entry.getValue();
			for (String tab : tables) {
				try {
					String table = tab.substring(0, tab.indexOf(","));
					String column = tab.substring(tab.indexOf(",") + 1, tab.length());
					clientTaskStatService.clearTaskExpired(table, column, days);
					System.out.println("table :" + table + ",column:" + column + ",days:" + days);
				} catch (Exception e) {
					logger.error(" clearTaskExpired fail ", e);
				}
			}
		}
	}

	private Map<Integer, String[]> getProperties() {
		Map<Integer, String[]> map = CollectionUtils.newHashMap(2);
		String[] prop = ArrayUtils.EMPTY_STRING_ARRAY;
		// 默认是30天前
		int days = 30;
		AbstractConfiguration.setDefaultListDelimiter(';');
		Configuration cfg;
		try {
			cfg = new PropertiesConfiguration("clearTaskData.properties");
			prop = cfg.getStringArray("clearTask.table");
			days = cfg.getInt("clearTask.days");

		} catch (ConfigurationException e) {
			logger.error(" clearTaskData.properties not found ", e);
		}
		map.put(days, prop);
		return map;
	}
}
