package com.nali.spreader.test;

import com.nali.spreader.data.RobotRegister;
import com.nali.spreader.workshop.GenerateRobotUserInfo;
import com.nali.spreader.workshop.RegisterWeiboAccount;


public class TestAccount {
	
	public static void main(String[] args) throws Exception {
		GenerateRobotUserInfo ui = new GenerateRobotUserInfo();
		while (true) {
			RobotRegister robot = ui.createRobot();
			RegisterWeiboAccount rwa = new RegisterWeiboAccount();
			System.out.println(rwa.getModifiedNames(robot));
			break;
		}
	}
}