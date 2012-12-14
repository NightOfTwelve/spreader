package com.nali.spreader.client.ximalaya;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nali.spreader.client.task.ActionMethod;
import com.nali.spreader.client.ximalaya.service.IRobotRemoteService;
import com.nali.spreader.constants.Website;
import com.nali.spreader.data.User;

@Component
public class XimalayaImportUserActionMethod implements ActionMethod {
	@Autowired
	private IRobotRemoteService robotRemoteService;

	@Override
	public Object execute(Map<String, Object> params, Map<String, Object> userContext, Long uid) {
		String keyword = (String) params.get("keyword");
		int offset = (Integer) params.get("offset");
		int limit = (Integer) params.get("limit");
		Long fansGte = (Long) params.get("fansGte");
		Long fansLte = (Long) params.get("fansLte");
		Integer vType = (Integer) params.get("vType");
		Date startCreateTime = long2Date((Long) params.get("startCreateTime"));
		Date endCreateTime = long2Date((Long) params.get("endCreateTime"));
		Date startUpdateTime = long2Date((Long) params.get("startUpdateTime"));
		Date endUpdateTime = long2Date((Long) params.get("endUpdateTime"));
		List<Map<String, Object>> maps = robotRemoteService.queryUser(keyword, offset, limit,
				fansGte, fansLte, vType, startCreateTime, endCreateTime, startUpdateTime,
				endUpdateTime);
		return getUsers(maps);
	}

	@Override
	public Long getActionId() {
		return 3004L;
	}

	private List<User> getUsers(List<Map<String, Object>> maps) {
		List<User> users = new ArrayList<User>();
		for (Map<String, Object> map : maps) {
			Long websiteUid = (Long) map.get("websiteUid");
			String nickName = (String) map.get("nickName");
			Integer gender = (Integer) map.get("gender");
			String realName = (String) map.get("realName");
			String email = (String) map.get("email");
			String nationality = (String) map.get("nationality");
			String province = (String) map.get("province");
			String city = (String) map.get("city");
			String introduction = (String) map.get("introduction");
			Integer vType = (Integer) map.get("vType");
			Long attentions = (Long) map.get("attentions");
			Long fans = (Long) map.get("fans");
			User user = new User();
			user.setWebsiteId(Website.ximalaya.getId());
			user.setIsRobot(false);
			user.setWebsiteUid(websiteUid);
			user.setNickName(nickName);
			user.setGender(gender);
			user.setRealName(realName);
			user.setEmail(email);
			user.setNationality(nationality);
			user.setProvince(province);
			user.setCity(city);
			user.setIntroduction(introduction);
			user.setAttentions(attentions);
			user.setvType(vType);
			user.setFans(fans);
			users.add(user);
		}
		return users;
	}

	private Date long2Date(Long time) {
		if (time == null) {
			return null;
		}
		return new Date(time);
	}
}
