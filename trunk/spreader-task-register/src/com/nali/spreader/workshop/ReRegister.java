package com.nali.spreader.workshop;

import org.springframework.stereotype.Component;

import com.nali.spreader.factory.TaskProduceLine;
import com.nali.spreader.factory.config.desc.ClassDescription;
import com.nali.spreader.factory.passive.AutowireProductLine;
import com.nali.spreader.factory.regular.RegularAnalyzer;

@Component
@ClassDescription("重新注册帐号")
public class ReRegister implements RegularAnalyzer {//TODO temp code
	@AutowireProductLine
	private TaskProduceLine<Object> reRegisterPassive;

	@Override
	public void work() {
		reRegisterPassive.send(null);
	}


}
