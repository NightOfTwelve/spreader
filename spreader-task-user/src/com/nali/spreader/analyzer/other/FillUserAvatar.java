package com.nali.spreader.analyzer.other;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nali.spreader.data.User;
import com.nali.spreader.factory.TaskProduceLine;
import com.nali.spreader.factory.config.SystemObject;
import com.nali.spreader.factory.config.desc.ClassDescription;
import com.nali.spreader.factory.passive.AutowireProductLine;
import com.nali.spreader.factory.regular.RegularAnalyzer;
import com.nali.spreader.service.IUserService;

@Component
@ClassDescription("填充头像")
public class FillUserAvatar implements RegularAnalyzer, SystemObject {
	@Autowired
	private IUserService userService;
	@AutowireProductLine
	private TaskProduceLine<Long> uploadUserAvatar;

	@Override
	public String work() {
		List<User> list = userService.findNoAvatarRobotUserList();
		if (list.size() > 0) {
			for (User u : list) {
				Long id = u.getId();
				uploadUserAvatar.send(id);
			}
		}
		return null;
	}
}
