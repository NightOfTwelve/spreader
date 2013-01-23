package com.nali.spreader.remote;

import java.util.Date;

public interface IRemoteCooldownConfigService {

	int[] getHourCooldowns(Date date, int id);
}
