package com.nali.spreader.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nali.common.model.Limit;
import com.nali.spreader.dao.IAppDownloadDao;
import com.nali.spreader.dao.ICrudRobotUserDao;
import com.nali.spreader.data.AppInfo;
import com.nali.spreader.model.RobotUser;
import com.nali.spreader.model.RobotUserExample;
import com.nali.spreader.service.IAppDownlodService;

@Service
public class AppDownlodService implements IAppDownlodService {
	private static final Pattern itunesUrlPattern = Pattern.compile("https?://itunes.apple.com/(\\w+)/app/.+?/id(\\d+)(\\?.*)?");

	private static Logger logger = Logger.getLogger(AppDownlodService.class);
	@Autowired
	private IAppDownloadDao appDownloadDao;
	@Autowired
	private ICrudRobotUserDao crudRobotUserDao;
	
	private Lock endPointOperateLock = new ReentrantLock();
	
	
	@Override
	public List<Long> assignUids(Integer websiteId, String appSource, Long appId, int count) {
		return assignUids(websiteId, appSource, appId, count, 0);
	}
	
	// 原始没有是否为付费账号的筛选条件的实现	
	@Override
	public List<Long> assignUids(Integer websiteId, String appSource, Long appId, int count, int offset) {
		List<RobotUser> robots;
		endPointOperateLock.lock();
		try {
			Long lastUid = appDownloadDao.getLastEndpoint(appSource, appId);
			
			RobotUserExample example = new RobotUserExample();
			example.setOrderByClause("uid");
			example.setLimit(Limit.newInstanceForLimit(offset, count));
			if(lastUid!=null) {
				example.createCriteria()
					.andAccountStateEqualTo(RobotUser.ACCOUNT_STATE_NORMAL)
					.andWebsiteIdEqualTo(websiteId)
					.andUidGreaterThan(lastUid);
			} else {
				example.createCriteria()
					.andAccountStateEqualTo(RobotUser.ACCOUNT_STATE_NORMAL)
					.andWebsiteIdEqualTo(websiteId);
			}
			robots = crudRobotUserDao.selectByExample(example);
			if(robots.size()==0) {
				return Collections.emptyList();
			}
			RobotUser last = robots.get(robots.size()-1);
			appDownloadDao.saveEndpoint(appSource, appId, last.getUid());
			if(logger.isInfoEnabled()) {
				logger.info("saveEndPoint <" + last.getUid() + ">");
			}
		} finally {
			endPointOperateLock.unlock();
		}
		List<Long> rlts = new ArrayList<Long>(robots.size());
		for (RobotUser robotUser : robots) {
			rlts.add(robotUser.getUid());
			appDownloadDao.setStatus(appSource, appId, robotUser.getUid(), STATUS_START_DOWNLOAD);
		}
		return rlts;
	}
	
	@Override
	public List<Long> assignUidsIsPay(Integer websiteId, String appSource, Long appId, boolean payingTag, int count, int offset) {
		List<RobotUser> robots;
		endPointOperateLock.lock();
		try {
			Long lastUid = appDownloadDao.getLastEndpoint(appSource, appId);

			RobotUserExample example = new RobotUserExample();
			example.setOrderByClause("uid");
			example.setLimit(Limit.newInstanceForLimit(offset, count));
			if (payingTag) {
				example.createCriteria().andPayingTagEqualTo(1);// 0:非付费 1:付费
			} else {
				example.createCriteria().andPayingTagEqualTo(0);// 0:非付费 1:付费
			}
			if (lastUid != null) {
				example.createCriteria().andAccountStateEqualTo(RobotUser.ACCOUNT_STATE_NORMAL).andWebsiteIdEqualTo(websiteId).andUidGreaterThan(lastUid);
			} else {
				example.createCriteria().andAccountStateEqualTo(RobotUser.ACCOUNT_STATE_NORMAL).andWebsiteIdEqualTo(websiteId);
			}
			robots = crudRobotUserDao.selectByExample(example);
			if (robots.size() == 0) {
				return Collections.emptyList();
			}
			RobotUser last = robots.get(robots.size() - 1);
			appDownloadDao.saveEndpoint(appSource, appId, last.getUid());
			if (logger.isInfoEnabled()) {
				logger.info("saveEndPoint <" + last.getUid() + ">");
			}
		} finally {
			endPointOperateLock.unlock();
		}
		List<Long> rlts = new ArrayList<Long>(robots.size());
		for (RobotUser robotUser : robots) {
			rlts.add(robotUser.getUid());
			appDownloadDao.setStatus(appSource, appId, robotUser.getUid(), STATUS_START_DOWNLOAD);
		}
		return rlts;
	}

	@Override
	public void finishDownload(String appSource, Long appId, Long uid) {
		appDownloadDao.updateStatus(appSource, appId, uid, STATUS_START_DOWNLOAD, STATUS_DOWNLOADED);
	}

	@Override
	public AppInfo parseUrl(String url) throws IllegalArgumentException {
		Matcher matcher = itunesUrlPattern.matcher(url);
		if(!matcher.matches()) {
			throw new IllegalArgumentException("url illegal:"+url);
		}
		String area = matcher.group(1);
		String appIdString = matcher.group(2);
		AppInfo appInfo = new AppInfo();
		appInfo.setUrl(url);
		appInfo.setAppId(Long.parseLong(appIdString));
		appInfo.setAppSource("itunes/"+area);
		return appInfo;
	}
}
