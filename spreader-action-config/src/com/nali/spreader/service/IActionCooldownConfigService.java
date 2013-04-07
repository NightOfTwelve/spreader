package com.nali.spreader.service;

import java.util.Date;

public interface IActionCooldownConfigService {

	int[] getHourCooldowns(Date date, int id);

	void reset();

}
