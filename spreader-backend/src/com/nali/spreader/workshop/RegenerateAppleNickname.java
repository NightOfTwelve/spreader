package com.nali.spreader.workshop;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nali.common.model.Limit;
import com.nali.spreader.constants.Website;
import com.nali.spreader.dao.ICrudRobotRegisterDao;
import com.nali.spreader.dao.ICrudRobotUserDao;
import com.nali.spreader.dao.ICrudUserDao;
import com.nali.spreader.data.RobotRegister;
import com.nali.spreader.data.User;
import com.nali.spreader.factory.config.SystemObject;
import com.nali.spreader.factory.config.desc.ClassDescription;
import com.nali.spreader.factory.regular.RegularAnalyzer;
import com.nali.spreader.model.RobotUser;
import com.nali.spreader.model.RobotUserExample;
import com.nali.spreader.service.IRobotRegisterService;
import com.nali.spreader.words.AppleNickname;

@Component
@ClassDescription("重新生成苹果昵称")
public class RegenerateAppleNickname implements RegularAnalyzer, SystemObject {
	@Autowired
	private ICrudRobotUserDao crudRobotUserDao;
	@Autowired
	private IRobotRegisterService robotRegisterService;
	@Autowired
	private ICrudRobotRegisterDao crudRobotRegisterDao;
	@Autowired
	private ICrudUserDao crudUserDao;

	@Override
	public String work() {
		RobotUserExample example = new RobotUserExample();
		int start=0;
		while(true) {
			Limit limit = Limit.newInstanceForLimit(start, 500);
			example.createCriteria().andWebsiteIdEqualTo(Website.apple.getId());
			example.setLimit(limit);
			List<RobotUser> list = crudRobotUserDao.selectByExample(example);
			if(list.size()==0) {
				break;
			}
			for (RobotUser r : list) {
				Long registerId = r.getRobotRegisterId();
				RobotRegister robotRegister = robotRegisterService.get(registerId);
				String nickname = AppleNickname.genNickname(robotRegister);
				
				RobotRegister record = new RobotRegister();
				record.setId(robotRegister.getId());
				record.setNickName(nickname);
				crudRobotRegisterDao.updateByPrimaryKeySelective(record);
				
				User record2 = new User();
				record2.setId(r.getUid());
				record2.setNickName(nickname);
				crudUserDao.updateByPrimaryKeySelective(record2);
			}
			start+=list.size();
		}
		return "重新生成昵称数量：" + start;
	}

}
