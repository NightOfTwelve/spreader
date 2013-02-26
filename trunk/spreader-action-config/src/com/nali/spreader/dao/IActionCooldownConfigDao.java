package com.nali.spreader.dao;

import java.util.List;

public interface IActionCooldownConfigDao {
	List<Double> getDownloadRate(int id);
	int getMaxDownloadPerHour(int id);
	void saveMaxDownloadPerHour(int id, int count);
	void saveDownloadRate(int id, List<Double> rates);
}
