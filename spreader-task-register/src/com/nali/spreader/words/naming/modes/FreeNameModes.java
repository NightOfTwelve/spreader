package com.nali.spreader.words.naming.modes;

import java.util.Arrays;
import java.util.Iterator;

import com.nali.spreader.data.RobotRegister;
import com.nali.spreader.words.naming.Modes;

/**
 * FreeNameModes<br>&nbsp;
 * 自由组合名字，配合Names.NAMES_NONE使用，并不考虑输入参数的name
 * @author sam Created on 2011-9-4
 */
public class FreeNameModes implements Modes {

	@Override
	public Iterator<String> iterator(RobotRegister robot, String name) {
		return Arrays.asList(
				robot.getFullName() + robot.getShortPinyinLower(),
				robot.getFullName() + "_" + robot.getShortPinyinUpper(),
				robot.getEnName() + robot.getFullName(),
				robot.getEnName() + "_" + robot.getLastName(),
				robot.getEnName() + "_" + robot.getFirstName()
				).iterator();
	}

}
