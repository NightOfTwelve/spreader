package com.nali.spreader.job;

import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nali.lts.context.TaskExecuteContext;
import com.nali.lts.exceptions.TaskExecuteException;
import com.nali.lts.task.AbstractTask;
import com.nali.spreader.service.IAvatarFileManageService;
import com.nali.spreader.utils.PhotoHelper;
import com.nali.spreader.utils.TimeHelper;

@Component
public class SynchronAvatarFileDataJob extends AbstractTask {
	private Logger LOGGER = Logger.getLogger(SynchronAvatarFileDataJob.class);
	@Autowired
	private IAvatarFileManageService amService;

	@Override
	public void execute(TaskExecuteContext context) throws TaskExecuteException {
		Map<Object, Object> prop = PhotoHelper
				.getPropertiesMap("/avatarconfig/webDavService.properties");
		String serviceUri = prop.get("url").toString();
		LOGGER.info("获取WEBDAV服务器地址:" + serviceUri);
		Map<String, Date> lastTimeMap = context.getTaskExecutionParameters();
		Date lastTime = null;
		if (lastTimeMap != null && lastTimeMap.containsKey("lasttime")) {
			lastTime = lastTimeMap.get("lasttime");
			LOGGER.info("获取上次执行的时间戳:" + lastTime);
		}
		if (lastTime == null) {
			LOGGER.info("上次执行的时间戳为空，开始初始化创建目录");
			amService.initCreatePhotoFileDirectory(serviceUri, new Date(), 10);
		} else {
			LOGGER.info("开始检查文件目录");
			// 首先检查并创建上次结束时间到当前日期后一天的目录.防止文件目录遗漏为创建
			amService.createLastTimeToCurrTimeDir(serviceUri, lastTime,
					TimeHelper.findAfterDate(new Date()));
			LOGGER.info("检查文件目录结束，开始执行同步任务");
			long stime = System.currentTimeMillis();
			amService.synAvatarFileDataBase(serviceUri, lastTime, new Date());
			long etime = System.currentTimeMillis();
			LOGGER.info("同步任务执行结束,耗时:" + (etime - stime) / 1000 + "s");
		}
		lastTime = new Date();
		lastTimeMap.put("lasttime", lastTime);
	}
}
