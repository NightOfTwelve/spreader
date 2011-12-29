package com.nali.spreader.analyzer;

import com.nali.spreader.config.SeachRobotInfoConfig;
import com.nali.spreader.factory.config.Configable;
import com.nali.spreader.factory.config.desc.ClassDescription;
import com.nali.spreader.factory.regular.RegularAnalyzer;

@ClassDescription(value = "更新用户信息")
public class BatUpdateRobotInfo implements RegularAnalyzer,
		Configable<SeachRobotInfoConfig> {
	private SeachRobotInfoConfig cfg;

	@Override
	public void work() {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(SeachRobotInfoConfig cfg) {
		this.cfg = cfg;
	}
}
