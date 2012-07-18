package com.nali.spreader.dao;


public interface IAppDownloadDao {

	boolean checkStatus(String appSource, Long appId, Long uid, Integer status);

	void setStatus(String appSource, Long appId, Long uid, Integer status);
	
	void updateStatus(String appSource, Long appId, Long uid, Integer from, Integer to);

	Long getLastEndpoint(String appSource, Long appId);

	void saveEndpoint(String appSource, Long appId, Long uid);

}
