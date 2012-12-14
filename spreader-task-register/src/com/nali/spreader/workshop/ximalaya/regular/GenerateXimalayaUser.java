package com.nali.spreader.workshop.ximalaya.regular;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.nali.spreader.data.RobotRegister;
import com.nali.spreader.factory.TaskProduceLine;
import com.nali.spreader.factory.config.Configable;
import com.nali.spreader.factory.config.desc.ClassDescription;
import com.nali.spreader.factory.config.desc.PropertyDescription;
import com.nali.spreader.factory.passive.AutowireProductLine;
import com.nali.spreader.factory.regular.RegularAnalyzer;
import com.nali.spreader.service.IRobotRegisterService;
import com.nali.spreader.workshop.weibo.RobotUserInfoGenerator;

@Component
@ClassDescription("喜马拉雅·生成无邮箱的帐号")
public class GenerateXimalayaUser implements RegularAnalyzer,
		Configable<GenerateXimalayaUser.GenConfig> {
	private int addCount;
	@Autowired
	private RobotUserInfoGenerator robotUserInfoGenerator;
	@Autowired
	private IRobotRegisterService robotRegisterService;
	@AutowireProductLine
	private TaskProduceLine<Long> registerXimalayaAccount;

	@Override
	public String work() {
		for (int i = 0; i < addCount; i++) {
			RobotRegister robotRegister = robotUserInfoGenerator.createRobot(null);
			if (robotRegister != null) {
				Long regId = robotRegisterService.save(robotRegister);
				registerXimalayaAccount.send(regId);
			}
		}
		return "预计生成：" + addCount + "，实际生成：" + addCount;
	}

	@Override
	public void init(GenerateXimalayaUser.GenConfig config) {
		Assert.notNull(config.getAddCount(), "count is null");
		addCount = config.getAddCount();
	}

	public static class GenConfig implements Serializable {
		private static final long serialVersionUID = -3775650033464547731L;
		@PropertyDescription("注册数量")
		private Integer addCount;

		public Integer getAddCount() {
			return addCount;
		}

		public void setAddCount(Integer addCount) {
			this.addCount = addCount;
		}
	}
}
