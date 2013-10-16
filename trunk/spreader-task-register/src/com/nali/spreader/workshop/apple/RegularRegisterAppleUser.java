package com.nali.spreader.workshop.apple;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.nali.spreader.data.KeyValue;
import com.nali.spreader.factory.TaskProduceLine;
import com.nali.spreader.factory.config.Configable;
import com.nali.spreader.factory.config.desc.ClassDescription;
import com.nali.spreader.factory.config.desc.PropertyDescription;
import com.nali.spreader.factory.passive.AutowireProductLine;
import com.nali.spreader.factory.regular.RegularAnalyzer;
import com.nali.spreader.service.IAppRegisterService;
import com.nali.spreader.workshop.apple.RegularRegisterAppleUser.RegisterAppleConfig;

@Component
@ClassDescription("注册·苹果帐号")
public class RegularRegisterAppleUser implements RegularAnalyzer,
		Configable<RegisterAppleConfig> {
	@AutowireProductLine
	private TaskProduceLine<KeyValue<Long, Boolean>> generateCnAppleUserInfo;
	private Integer addCount;
	private Boolean isActive;

	@Autowired
	private IAppRegisterService appRegisterService;

	@Override
	public String work() {
		List<Long> registerIds = appRegisterService.assignRegisterIds(addCount);
		for (Long registerId : registerIds) {
			KeyValue<Long, Boolean> kv = new KeyValue<Long, Boolean>(
					registerId, isActive);
			generateCnAppleUserInfo.send(kv);
		}
		return "预计生成：" + addCount + "，实际生成：" + registerIds.size();
	}

	@Override
	public void init(RegisterAppleConfig cfg) {
		Integer genCount = cfg.getAddCount();
		Assert.notNull(genCount, "genCount is null");
		Boolean active = cfg.getIsActive();
		if (active == null) {
			active = true;
		}
		this.addCount = genCount;
		this.isActive = active;
	}

	public static class RegisterAppleConfig implements Serializable {
		private static final long serialVersionUID = 881779404989076071L;
		@PropertyDescription("需要生成的数量")
		private Integer addCount;
		@PropertyDescription("注册成功后是否激活")
		private Boolean isActive;

		public Integer getAddCount() {
			return addCount;
		}

		public void setAddCount(Integer addCount) {
			this.addCount = addCount;
		}

		public Boolean getIsActive() {
			return isActive;
		}

		public void setIsActive(Boolean isActive) {
			this.isActive = isActive;
		}
	}
}
