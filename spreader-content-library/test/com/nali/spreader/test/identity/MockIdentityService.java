/*
 *The code is written by NaLi, All rights is reserved.
 */
package com.nali.spreader.test.identity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.nali.center.service.IIdentityService;
import com.nali.common.util.Logger;

/**
 * 用文件来实现自增序列服务
 * 
 * @author morgan Created on 2010-6-22
 */
public class MockIdentityService implements IIdentityService {
	private static Logger log = Logger.getLogger(MockIdentityService.class);
	private int count;

	@Override
	synchronized public long getNextId(String appName) {
		File file = getFile(appName);
		if (!file.exists()) {
			// throw new UnregistedAppException(appName +
			// " has not been registed");
			register(appName);
		}
		String num;
		try {
			num = FileUtils.readFileToString(file);
			long number = Long.parseLong(num) + 1;
			FileUtils.writeStringToFile(file, "" + number);
			return number;
		} catch (IOException e) {
			log.error("Appname" + appName + " get next id fails", e);
			return -1;
		}
	}

	public void register(String appName) {
		File file = getFile(appName);
		System.out.println(file);
		if (file.exists()) {
			throw new IllegalStateException(appName + " has been registed");
		}
		try {
			file.getParentFile().mkdirs();
			file.createNewFile();
			FileUtils.writeStringToFile(file, "10000");
		} catch (IOException e) {
			log.error(e, e);
		}
	}

	/**
	 * @param appName
	 * @return
	 */
	private File getFile(String appName) {
		File file = new File(System.getProperty("user.home") + "/id_tmp", "sequence_" + appName + ".txt");
		return file;
	}

	@Override
	public List<Long> getNextIds(String appName, int count) {
		List<Long> list = new ArrayList<Long> (count);
		for(int i = 0; i < count; i++) {
			list.add(this.getNextId(appName));
		}
		return list;
	}

	@Override
	public Map<String, List<Long>> getMultiNextId(Map<String, Integer> appNameCntMap) {
		// TODO Auto-generated method stub
		return null;
	}

}
