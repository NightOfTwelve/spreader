package com.nali.spreader.words.naming.modes;

import java.util.Arrays;
import java.util.Iterator;

import com.nali.spreader.data.RobotRegister;
import com.nali.spreader.words.naming.Modes;

public class HomeModes implements Modes {

	@Override
	public Iterator<String> iterator(RobotRegister robot, String name) {
		return Arrays.asList(
				robot.getProvince() + name,
				robot.getCity() + name,
				robot.getProvince() + '人' + name,
				robot.getCity() + '人' + name,
				name + '在' + robot.getProvince(),
				name + '在' + robot.getCity()
			).iterator();
	}

}
