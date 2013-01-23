package com.nali.spreader.remote;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nali.spreader.service.IActionCooldownConfigService;

@Service
public class RemoteCooldownConfigService implements IRemoteCooldownConfigService {
	@Autowired
	private IActionCooldownConfigService actionCooldownConfigService;

	@Override
	public int[] getHourCooldowns(Date date, int id) {
		return actionCooldownConfigService.getHourCooldowns(date, id);
	}

}
