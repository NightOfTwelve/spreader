package com.nali.spreader.words.naming;

import java.util.Arrays;
import java.util.List;

import com.nali.spreader.data.RobotRegister;

class Constants {
	static final List<String> EMPTY_NAMES=Arrays.asList("");
	static final String[] EMPTY_RLTS=new String[0];
	static final RobotMatcher TRUE_MATCHER=new TrueRobotMatcher();
	static class TrueRobotMatcher implements RobotMatcher {
		@Override
		public boolean match(RobotRegister robot) {
			return true;
		}
	}
}
