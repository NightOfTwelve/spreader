package com.nali.spreader.client.ximalaya.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.nali.spreader.client.ximalaya.exception.AuthenticationException;

/**
 * 外部接口
 * 
 * @author xiefei
 * 
 */
public interface IRobotRemoteService {
	/**
	 * 注册喜马拉雅帐号
	 * 
	 * @param nickname
	 * @param email
	 * @param pwd
	 * @param gender
	 * @param province
	 * @param city
	 * @return
	 */
	Map<String, Object> registerRobot(String nickname, String email, String pwd, Integer gender,
			String province, String city, byte[] md5) throws AuthenticationException;

	/**
	 * 喜马拉雅帐号的加粉
	 * 
	 * @param fromWebsiteUid
	 * @param toWebsiteUid
	 * @return
	 */
	Boolean follow(Long fromWebsiteUid, Long toWebsiteUid, byte[] md5)
			throws AuthenticationException;

	/**
	 * 按条件筛选喜马拉雅帐号
	 * 
	 * @param keyword
	 * @param offset
	 * @param limit
	 * @param fansGte
	 * @param fansLte
	 * @param vType
	 * @param startCreateTime
	 * @param endCreateTime
	 * @param startUpdateTime
	 * @param endUpdateTime
	 * @return
	 */
	List<Map<String, Object>> queryUser(String keyword, int offset, int limit, Long fansGte,
			Long fansLte, Integer vType, Date startCreateTime, Date endCreateTime,
			Date startUpdateTime, Date endUpdateTime, byte[] md5) throws AuthenticationException;

	/**
	 * 根据昵称查找喜马拉雅帐号
	 * 
	 * @param nickName
	 * @return
	 */
	Map<String, Object> queryUserByNickname(String nickName, byte[] md5)
			throws AuthenticationException;

	/**
	 * 根据UID查询喜马拉雅帐号
	 * 
	 * @param uid
	 * @return
	 */
	Map<String, Object> queryUserByUid(Long uid, byte[] md5) throws AuthenticationException;
}
