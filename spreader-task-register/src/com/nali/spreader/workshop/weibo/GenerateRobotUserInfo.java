package com.nali.spreader.workshop.weibo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nali.spreader.data.KeyValue;
import com.nali.spreader.data.RealMan;
import com.nali.spreader.data.RobotRegister;
import com.nali.spreader.factory.TaskProduceLine;
import com.nali.spreader.factory.passive.AutowireProductLine;
import com.nali.spreader.factory.passive.PassiveAnalyzer;
import com.nali.spreader.service.IRobotRegisterService;

@Component
public class GenerateRobotUserInfo implements PassiveAnalyzer<KeyValue<String, RealMan>> {
	@Autowired
	private IRobotRegisterService robotRegisterService;
	@Autowired
	private RobotUserInfoGenerator robotUserInfoGenerator;
	
	@AutowireProductLine
	private TaskProduceLine<KeyValue<RobotRegister, String>> registerRobotUserEmail;

	@Override
	public void work(KeyValue<String, RealMan> data) {
		generateRobot(data.getKey(), data.getValue());
	}
	
	private void generateRobot(String email, RealMan realMan) {
		RobotRegister robot = robotUserInfoGenerator.createRobot(realMan);
		if(robot!=null) {
			robotRegisterService.save(robot);
			registerRobotUserEmail.send(new KeyValue<RobotRegister, String>(robot, email));
		}
	}

}
