package com.nali.spreader.service;

import com.nali.spreader.data.RobotRegister;
import com.nali.spreader.data.RobotWeiboXimalaya;

public interface IRobotRegisterService {
	int countNoEmail();
	int countRegisteringAccount(Integer websiteId);
	void updateEmail(Long id, String email);
	/**
	 * 保存并设置id
	 */
	Long save(RobotRegister robotRegister);
	
	void updateSelective(RobotRegister robotRegister);

	void addRegisteringAccount(Integer websiteId, Long registerId, String nickname);

	void removeRegisteringAccount(Integer websiteId, Long registerId);
	String getRegisteringAccount(Integer websiteId, Long registerId);
	RobotRegister get(Long id);
	boolean addUsingEmail(String email);
	
	/**
	 * 保存一条微博帐号与喜马拉雅帐号对应关系
	 * 
	 * @param rwx
	 * @return
	 */
	Long saveXimalayaMapping(RobotWeiboXimalaya rwx);
	
	/**
	 * 唯一索引查询对应关系
	 * 
	 * @param websiteUid
	 * @param ximalayaUid
	 * @return
	 */
	RobotWeiboXimalaya getRobotWeiboXimalayaByUk(Long websiteUid, Long ximalayaUid);
	
	/**
	 * 判断是否存在对应关系
	 * 
	 * @param websiteUid
	 * @return
	 */
	boolean websiteUidIsExistMapping(Long websiteUid);
	
	/**
	 * 获取注册信息中的真实姓名 如果不存在，返回null
	 * 
	 * @param registerId
	 * @return
	 */
	String getRegisterRealMan(Long registerId);
}
