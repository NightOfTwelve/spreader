package com.nali.spreader.workshop;

import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nali.spreader.constants.Channel;
import com.nali.spreader.constants.Website;
import com.nali.spreader.data.User;
import com.nali.spreader.data.WeiboAppeal;
import com.nali.spreader.factory.SimpleActionConfig;
import com.nali.spreader.factory.base.ContextMeta;
import com.nali.spreader.factory.base.SingleTaskMachineImpl;
import com.nali.spreader.factory.base.SingleTaskMeta;
import com.nali.spreader.factory.exporter.SingleTaskExporter;
import com.nali.spreader.factory.passive.SinglePassiveTaskProducer;
import com.nali.spreader.factory.result.ContextedResultProcessor;
import com.nali.spreader.model.RobotUser;
import com.nali.spreader.service.IGlobalRobotUserService;
import com.nali.spreader.service.IGlobalUserService;
import com.nali.spreader.util.SpecialDateUtil;

@Component
public class CheckWeiboAppeal extends SingleTaskMachineImpl implements SinglePassiveTaskProducer<Long>, ContextedResultProcessor<Boolean, SingleTaskMeta> {
	private static Logger logger = Logger.getLogger(CheckWeiboAppeal.class);
	@Autowired
	private IGlobalRobotUserService globalRobotUserService;
	@Autowired
	private IGlobalUserService globalUserService;

	public CheckWeiboAppeal() {
		super(SimpleActionConfig.confirmWeiboAppeal, Website.weibo, Channel.normal);
		ContextMeta contextMeta = new ContextMeta("uid");
		setContextMeta(contextMeta);
	}
	
	@Override
	public void work(Long uid, SingleTaskExporter exporter) {
		RobotUser robotUser = globalRobotUserService.getRobotUser(uid);
		if(robotUser==null) {
			throw new IllegalArgumentException("not a robotUser:"+uid);
		}
		exporter.setProperty("uid", uid);
		exporter.setProperty("loginName", robotUser.getLoginName());
		exporter.setProperty("loginPwd", robotUser.getLoginPwd());
		exporter.send(User.UID_NOT_LOGIN, SpecialDateUtil.afterNow(10));
	}

	@Override
	public void handleResult(Date updateTime, Boolean result, Map<String, Object> contextContents, Long taskUid) {
		Long uid = (Long) contextContents.get("uid");
		if(result==null) {
			logger.info("appeal still waiting, uid:"+uid);
			return;
		}
		if(result==true) {
			User user = globalUserService.recoverDeletedUser(uid);
			if(user==null) {
				throw new IllegalArgumentException("user not exists:" + uid);
			}
			globalRobotUserService.resumeAccount(uid);
			globalUserService.removeWeiboAppeal(uid);
		} else {
			WeiboAppeal weiboAppeal = new WeiboAppeal();
			weiboAppeal.setUid(uid);
			weiboAppeal.setStatus(WeiboAppeal.STATUS_FAIL);
			globalUserService.mergeWeiboAppeal(weiboAppeal);
		}
	}

}
