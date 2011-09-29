package com.nali.spreader.words.naming.modes;

import java.util.Arrays;
import java.util.Iterator;

import com.nali.spreader.data.RobotRegister;
import com.nali.spreader.util.SpecialDateUtil;
import com.nali.spreader.words.naming.Modes;

public class DateModes implements Modes {

	@Override
	public Iterator<String> iterator(RobotRegister robot, String name) {
		return Arrays.asList(
				name + "_" + SpecialDateUtil.getCachedThisYear(),
				name + "_" + robot.getBirthdayYear(),
				(name + "_" + robot.getBirthdayMonth()) + robot.getBirthdayDay()
			).iterator();
	}

}
