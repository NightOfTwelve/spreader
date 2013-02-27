package com.nali.spreader.service;

import java.util.List;

import com.nali.spreader.data.AppInfo;

public interface IAppDownlodService {
	Integer STATUS_START_DOWNLOAD=0;
	Integer STATUS_DOWNLOADED=1;
	
	List<Long> assignUids(Integer websiteId, String appSource, Long appId, int count);
	
	List<Long> assignUids(Integer websiteId, String appSource, Long appId, int count, int offset);

	void finishDownload(String appSource, Long appId, Long uid);

	AppInfo parseUrl(String url) throws IllegalArgumentException;

}
