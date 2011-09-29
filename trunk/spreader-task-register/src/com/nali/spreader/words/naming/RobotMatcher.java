package com.nali.spreader.words.naming;

import com.nali.spreader.data.RobotRegister;
import com.nali.spreader.util.SpecialDateUtil;

public interface RobotMatcher {
	boolean match(RobotRegister robot);
	public static class AgeRobotMatcher implements RobotMatcher {
		private int start;
		private int end;
		
		public AgeRobotMatcher(int start, int end) {
			super();
			this.start = start;
			this.end = end;
		}

		@Override
		public boolean match(RobotRegister robot) {
			int age = SpecialDateUtil.getCachedThisYear() - robot.getBirthdayYear();
			return age >= start && age < end;
		}
		
	}
}
