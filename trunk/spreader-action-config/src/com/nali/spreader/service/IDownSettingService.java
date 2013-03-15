package com.nali.spreader.service;

import java.util.List;

public interface IDownSettingService {

	int getMaxDownloadPerHour(int id);

	void setMaxDownloadPerHour(int id, int count);

	void setDownloadRate(int id, List<Double> rates);
}
