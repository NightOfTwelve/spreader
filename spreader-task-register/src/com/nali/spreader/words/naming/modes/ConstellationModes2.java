package com.nali.spreader.words.naming.modes;

import java.util.Arrays;
import java.util.Iterator;

import com.nali.spreader.data.Constellation;
import com.nali.spreader.data.RobotRegister;
import com.nali.spreader.util.AvgRandomer;
import com.nali.spreader.util.Randomer;
import com.nali.spreader.words.Txt;
import com.nali.spreader.words.naming.Modes;

/**
 * ConstellationModes<br>&nbsp;
 * 星座 + 前后缀
 * @author sam Created on 2011-9-4
 */
public class ConstellationModes2 implements Modes {
	private static final Constellation[] constellations=Constellation.values();
	private Randomer<String> suffix = new AvgRandomer<String>(Txt.suffix);
	private Randomer<String> prefix = new AvgRandomer<String>(Txt.prefix);

	private String tranConstellation(Integer constellation) {
		return constellations[constellation].getName();
	}

	@Override
	public Iterator<String> iterator(RobotRegister robot, String name) {
		String constellation = tranConstellation(robot.getConstellation());
		return Arrays.asList(
					constellation + "座" + suffix.get(),
					prefix.get() + constellation + "座"
				).iterator();
	}

}
