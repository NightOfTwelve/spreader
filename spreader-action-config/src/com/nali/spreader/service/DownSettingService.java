package com.nali.spreader.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nali.spreader.dao.IActionCooldownConfigDao;

@Service
public class DownSettingService implements IDownSettingService {
	@Autowired
	private IActionCooldownConfigDao actionCooldownConfigDao;

	@Override
	public int getMaxDownloadPerHour(int id) {
		return actionCooldownConfigDao.getMaxDownloadPerHour(id);
	}

	@Override
	public void setMaxDownloadPerHour(int id, int count) {
		actionCooldownConfigDao.saveMaxDownloadPerHour(id, count);
	}

	@Override
	public void setDownloadRate(int id, List<Double> rates) {
		actionCooldownConfigDao.saveDownloadRate(id, rates);
	}
}
