package com.nali.spreader.workshop.ximalaya;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nali.spreader.factory.TaskProduceLine;
import com.nali.spreader.factory.passive.AutowireProductLine;
import com.nali.spreader.factory.passive.PassiveAnalyzer;
import com.nali.spreader.model.RobotUser;
import com.nali.spreader.service.IGlobalRobotUserService;
import com.nali.spreader.service.IRobotRegisterService;

@Component
public class GenXimalayaUsersByWeibo implements PassiveAnalyzer<List<Long>> {
	@Autowired
	private IGlobalRobotUserService globalRobotUserService;
	@Autowired
	private IRobotRegisterService robotRegisterService;
	@AutowireProductLine
	private TaskProduceLine<GenXimalayaParam> registerXimalayaAccount;

	@Override
	public void work(List<Long> data) {
		for (Long uid : data) {
			RobotUser robot = globalRobotUserService.getRobotUser(uid);
			Long websiteUid = robot.getWebsiteUid();
			if (!robotRegisterService.websiteUidIsExistMapping(websiteUid)) {
				Long regId = robot.getRobotRegisterId();
				// registerId为0的帐号要过滤
				if (regId > 0) {
					GenXimalayaParam param = new GenXimalayaParam();
					param.setRegId(regId);
					param.setWebsiteUid(websiteUid);
					param.setUid(uid);
					registerXimalayaAccount.send(param);
				}
			}
		}
	}

	public static class GenXimalayaParam implements Serializable {
		private static final long serialVersionUID = -3409225347800107160L;
		private Long uid;
		private Long regId;
		private Long websiteUid;

		public Long getUid() {
			return uid;
		}

		public void setUid(Long uid) {
			this.uid = uid;
		}

		public Long getRegId() {
			return regId;
		}

		public void setRegId(Long regId) {
			this.regId = regId;
		}

		public Long getWebsiteUid() {
			return websiteUid;
		}

		public void setWebsiteUid(Long websiteUid) {
			this.websiteUid = websiteUid;
		}
	}
}
