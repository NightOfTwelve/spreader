package com.nali.spreader.workshop.weibo.regular;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.nali.spreader.data.KeyValue;
import com.nali.spreader.data.RealMan;
import com.nali.spreader.factory.TaskProduceLine;
import com.nali.spreader.factory.config.Configable;
import com.nali.spreader.factory.config.desc.ClassDescription;
import com.nali.spreader.factory.config.desc.PropertyDescription;
import com.nali.spreader.factory.passive.AutowireProductLine;
import com.nali.spreader.factory.regular.RegularAnalyzer;
import com.nali.spreader.service.IRealManService;
import com.nali.spreader.workshop.weibo.SupportedEmails;
import com.nali.spreader.workshop.weibo.regular.RegularGenerateUser.ConfigDto;

@Component
@ClassDescription("注册·微博帐号")
public class RegularGenerateUser implements RegularAnalyzer, Configable<ConfigDto> {
	@Autowired
	private IRealManService realManService;
	@AutowireProductLine
	private TaskProduceLine<KeyValue<String, RealMan>> generateRobotUserInfo;
	private ConfigDto dto;

	@Override
	public String work() {
		int count=dto.getAddCount();
		boolean useRealId = dto.isUseRealId();
		String email = dto.getEmail();
		List<RealMan> realMans = null;
		if(useRealId) {
			realMans = realManService.getEffectiveRealMan(count);
			count = realMans.size();
		}
		for (int i = 0; i < count; i++) {
			KeyValue<String, RealMan> kv = new KeyValue<String, RealMan>(email, useRealId?realMans.get(i):null);
			generateRobotUserInfo.send(kv);
		}
		return "预计生成：" + dto.getAddCount() +
				"，实际生成：" + count;
	}

	@Override
	public void init(ConfigDto dto) {
		Assert.notNull(dto.getAddCount(), "email is null");
		Assert.notNull(dto.getEmail(), "email is null");
		Long actionId = SupportedEmails.getActionId(dto.getEmail());
		if(actionId==null) {
			throw new IllegalArgumentException("unsupported email:" + dto.getEmail());
		}
		this.dto = dto;
	}

	
	public static class ConfigDto implements Serializable {
		private static final long serialVersionUID = 5628056519224550146L;
		@PropertyDescription("注册数量")
		private Integer addCount;
		@PropertyDescription("注册邮箱（163,126,yeah,china,gmail,yahoo）")
		private String email;
		@PropertyDescription("是否采用真实身份证库")
		private boolean useRealId=true;
		public Integer getAddCount() {
			return addCount;
		}
		public void setAddCount(Integer addCount) {
			this.addCount = addCount;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public boolean isUseRealId() {
			return useRealId;
		}
		public void setUseRealId(boolean useRealId) {
			this.useRealId = useRealId;
		}
	}
}
