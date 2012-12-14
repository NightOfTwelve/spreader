package com.nali.spreader.client.ximalaya.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 外部接口
 * 
 * @author xiefei
 * 
 */
public interface IRobotRemoteService {
	
	Map<String, Object> registerRobot(String nickname, String email, String pwd, Integer gender,
			String province, String city);

	Boolean follow(Long fromWebsiteUid, Long toWebsiteUid);

	List<Map<String, Object>> queryUser(String keyword, int offset, int limit, Long fansGte,
			Long fansLte, Integer vType, Date startCreateTime, Date endCreateTime,
			Date startUpdateTime, Date endUpdateTime);
}
