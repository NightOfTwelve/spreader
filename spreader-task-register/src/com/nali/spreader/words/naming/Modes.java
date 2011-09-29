package com.nali.spreader.words.naming;

import java.util.Iterator;

import com.nali.spreader.data.RobotRegister;

public interface Modes {
	Iterator<String> iterator(RobotRegister robot, String name);

}
