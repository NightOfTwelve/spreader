package com.nali.spreader.words.naming.modes;

import java.util.Arrays;
import java.util.Iterator;

import com.nali.spreader.data.Constellation;
import com.nali.spreader.data.RobotRegister;
import com.nali.spreader.words.naming.Modes;

/**
 * ConstellationModes<br>&nbsp;
 * 星座 + 名字
 * @author sam Created on 2011-9-4
 */
public class ConstellationModes implements Modes {
	private static final Constellation[] constellations=Constellation.values();

	private String tranConstellation(Integer constellation) {
		return constellations[constellation].getName();
	}

	@Override
	public Iterator<String> iterator(RobotRegister robot, String name) {
		String constellation = tranConstellation(robot.getConstellation());
		return Arrays.asList(
					constellation + "座的" + name
				).iterator();
	}

}
