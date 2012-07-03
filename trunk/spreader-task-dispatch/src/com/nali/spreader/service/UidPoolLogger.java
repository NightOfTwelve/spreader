package com.nali.spreader.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.log4j.Logger;

import com.nali.spreader.constants.TaskType;
import com.nali.spreader.service.AbstractUidPool.CountTask;

public class UidPoolLogger {
	private static Logger logger = Logger.getLogger(UidPoolLogger.class);
	private List<String[]> cachedLogs = new ArrayList<String[]>();
	
	public boolean isLoggerEnabled() {
		return logger.isInfoEnabled();
	}
	
	public void log(Integer taskType, int priorityTasksSize, CountTask priorityCount, int normalTasksSize, CountTask normalCount) {
		StringBuilder sb = null;
		synchronized (cachedLogs) {
			cachedLogs.add(new String[] {
					""+taskType,
					"prior",
					""+priorityTasksSize,
					""+priorityCount.normalSize,
					""+priorityCount.anyoneActionCount,
					""+priorityCount.notLoginActionCount,
					""+priorityCount.normalActionCount,
			});
			cachedLogs.add(new String[] {
					""+taskType,
					"normal",
					""+normalTasksSize,
					""+normalCount.normalSize,
					""+normalCount.anyoneActionCount,
					""+normalCount.notLoginActionCount,
					""+normalCount.normalActionCount,
			});
			if(cachedLogs.size()>=TaskType.values().length*2) {
				Collections.sort(cachedLogs, new Comparator<String[]>() {
					@Override
					public int compare(String[] o1, String[] o2) {
						int compare0 = o1[0].compareTo(o2[0]);
						if(compare0!=0) {
							return compare0;
						} else {
							return -1 * o1[1].compareTo(o2[1]);
						}
					}
				});
				sb = new StringBuilder();
				sb.append("\r\n" +
						"\t\t\ttaskType" + 
						"\tuids" +
						"\tufs" +//user fetch size
						"\tanyone" +
						"\tnoLogin" +
						"\tnormal");
				for (String[] log : cachedLogs) {
					sb.append("\r\n" +
							"\t\t\t" + log[0] + 
							"\t" + log[1] +
							"\t"+log[2] +
							"\t"+log[3] +
							"\t"+log[4] +
							"\t"+log[5] +
							"\t"+log[6]
							);
				}
				cachedLogs.clear();
			}
		}
		if(sb!=null) {
			logger.info(sb.toString());
		}
	}
}
