package com.nali.spreader.service;

public interface IDownSettingService {

	int getMaxDownloadPerHour(int id);

	void setMaxDownloadPerHour(int id, int count);

}
