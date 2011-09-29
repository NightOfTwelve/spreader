package com.nali.spreader.words.naming;

import java.util.Arrays;
import java.util.List;

import com.nali.spreader.data.RobotRegister;

public interface Names {
	List<String> gets(RobotRegister robot);

	public static final Names NAMES_NONE=new Names() {
		@Override
		public List<String> gets(RobotRegister robot) {
			return Constants.EMPTY_NAMES;
		}
	};
	public static final Names NAMES_FULL_LAST_EN=new Names() {
		@Override
		public List<String> gets(RobotRegister robot) {
			return Arrays.asList(robot.getFullName(), robot.getLastName(), robot.getEnName());
		}
	};
	public static final Names NAMES_FULL_LAST=new Names() {
		@Override
		public List<String> gets(RobotRegister robot) {
			return Arrays.asList(robot.getFullName(), robot.getLastName());
		}
	};
	public static final Names NAMES_FULL_EN=new Names() {
		@Override
		public List<String> gets(RobotRegister robot) {
			return Arrays.asList(robot.getFullName(), robot.getEnName());
		}
	};
	public static final Names NAMES_FULL=new Names() {
		@Override
		public List<String> gets(RobotRegister robot) {
			return Arrays.asList(robot.getFullName());
		}
	};
	public static final Names NAMES_EN=new Names() {
		@Override
		public List<String> gets(RobotRegister robot) {
			return Arrays.asList(robot.getEnName());
		}
	};
	public static final Names NAMES_LAST=new Names() {
		@Override
		public List<String> gets(RobotRegister robot) {
			return Arrays.asList(robot.getLastName());
		}
	};
}
