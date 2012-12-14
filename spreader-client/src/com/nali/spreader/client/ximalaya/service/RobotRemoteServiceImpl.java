package com.nali.spreader.client.ximalaya.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.math.RandomUtils;
import org.springframework.stereotype.Service;

import com.nali.common.util.CollectionUtils;

public class RobotRemoteServiceImpl {


	public Map<String, Object> registerRobot(String nickname, String email, String pwd,
			Integer gender, String province, String city) {
		// TODO Auto-generated method stub
		Map<String, Object> map = CollectionUtils.newHashMap(3);
		map.put("nickName", "喜马拉雅帐号" + RandomUtils.nextLong());
		map.put("websiteUid", RandomUtils.nextLong());
		map.put("result", 1);
		return map;
	}


	public Boolean follow(Long fromWebsiteUid, Long toWebsiteUid) {
		// TODO Auto-generated method stub
		return true;
	}


	public List<Map<String, Object>> queryUser(String keyword, int offset, int limit, Long fansGte,
			Long fansLte, Integer vType, Date startCreateTime, Date endCreateTime,
			Date startUpdateTime, Date endUpdateTime) {
		// TODO Auto-generated method stub
		return null;
	}

}
