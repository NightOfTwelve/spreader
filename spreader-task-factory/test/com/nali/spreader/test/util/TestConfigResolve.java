package com.nali.spreader.test.util;

import com.nali.spreader.factory.config.desc.ConfigDefinition;
import com.nali.spreader.factory.config.desc.DescriptionResolve;
import com.nali.spreader.model.RobotUser;

public class TestConfigResolve {
	public static void main(String[] args) {
		ConfigDefinition configDefinition = DescriptionResolve.get(RobotUser.class);
		System.out.println(configDefinition);
	}
}
