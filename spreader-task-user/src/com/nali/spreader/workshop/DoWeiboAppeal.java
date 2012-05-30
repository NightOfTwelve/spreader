package com.nali.spreader.workshop;

import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nali.common.util.CollectionUtils;
import com.nali.spreader.analyzer.AutoReplyWeibo;
import com.nali.spreader.constants.Channel;
import com.nali.spreader.constants.Website;
import com.nali.spreader.data.User;
import com.nali.spreader.data.WeiboAppeal;
import com.nali.spreader.factory.SimpleActionConfig;
import com.nali.spreader.factory.base.ContextMeta;
import com.nali.spreader.factory.base.SingleTaskMachineImpl;
import com.nali.spreader.factory.base.SingleTaskMeta;
import com.nali.spreader.factory.exporter.SingleTaskExporter;
import com.nali.spreader.factory.passive.Input;
import com.nali.spreader.factory.passive.SinglePassiveTaskProducer;
import com.nali.spreader.factory.result.ContextedResultProcessor;
import com.nali.spreader.model.ClientTask;
import com.nali.spreader.model.RobotUser;
import com.nali.spreader.service.IGlobalRobotUserService;
import com.nali.spreader.service.IGlobalUserService;
import com.nali.spreader.util.SpecialDateUtil;
import com.nali.spreader.util.TxtFileUtil;
import com.nali.spreader.util.random.AvgRandomer;
import com.nali.spreader.util.random.Randomer;

@Component
public class DoWeiboAppeal extends SingleTaskMachineImpl implements SinglePassiveTaskProducer<Long>, ContextedResultProcessor<Long, SingleTaskMeta> {
	private static final String FILE_APPEAL_FIRST = "txt/appeal-first.txt";
	private static final String FILE_APPEAL_MIDDLE = "txt/appeal-middle.txt";
	private static final String FILE_APPEAL_LAST = "txt/appeal-last.txt";
	private static final String PUNCTUATIONS_STRING=",.?!，。？！";
	private static Logger logger = Logger.getLogger(DoWeiboAppeal.class);
	private static final int basePriority = ClientTask.BASE_PRIORITY_MAX;
	private static Set<Character> punctuations;
	private static Randomer<String> appealFirsts;
	private static Randomer<String> appealMiddles;
	private static Randomer<String> appealLasts;
	@Autowired
	private IGlobalRobotUserService globalRobotUserService;
	@Autowired
	private IGlobalUserService globalUserService;
	private long lastTime = -1L;//TODO 如果改成可配置需要放在外部存储
	private long interval = 1000*60*10L;

	public DoWeiboAppeal() {
		super(SimpleActionConfig.weiboAppeal, Website.weibo, Channel.normal);
		ContextMeta contextMeta = new ContextMeta("uid");
		setContextMeta(contextMeta);
	}
	
	static {
		Set<String> datas;
		try {
			String[] chars = PUNCTUATIONS_STRING.split("(?=.)(?<=.)");
			punctuations = new HashSet<Character>(CollectionUtils.getMapSize(chars.length));
			for (String charString : chars) {
				punctuations.add(charString.charAt(0));
			}
			datas = TxtFileUtil.read(AutoReplyWeibo.class.getClassLoader().getResource(FILE_APPEAL_FIRST));
			appealFirsts = new AvgRandomer<String>(datas);
			datas = TxtFileUtil.read(AutoReplyWeibo.class.getClassLoader().getResource(FILE_APPEAL_MIDDLE));
			appealMiddles = new AvgRandomer<String>(datas);
			datas = TxtFileUtil.read(AutoReplyWeibo.class.getClassLoader().getResource(FILE_APPEAL_LAST));
			appealLasts = new AvgRandomer<String>(datas);
		} catch (IOException e) {
			throw new Error(e);
		}
	}
	
	@Override
	public void work(Long uid, SingleTaskExporter exporter) {
		work(null, uid, exporter);
	}
	
	@Input
	public void work(WeiboAppeal appeal, SingleTaskExporter exporter) {
		work(appeal, null, exporter);
	}
	
	private void work(WeiboAppeal appeal, Long uid, SingleTaskExporter exporter) {
		if(appeal!=null) {
			if(uid!=null && !uid.equals(appeal.getUid())) {
				throw new IllegalArgumentException("uid mismatch uid:"+uid+", appeal's uid:" + appeal.getUid());
			} else {
				if(appeal.getUid()==null) {
					throw new IllegalArgumentException("appeal.getUid()==null");
				}
				uid=appeal.getUid();
			}
		}
		RobotUser robotUser = globalRobotUserService.getRobotUser(uid);
		if(robotUser==null) {
			throw new IllegalArgumentException("not a robotUser:"+uid);
		}
		User user = globalUserService.getDeletedUser(uid);
		if(user.getNickName()==null) {
			logger.warn("missing nickname:"+uid);
			return;
		}
		if(appeal!=null) {
			appeal.setCreateTime(new Date());
			appeal.setStatus(WeiboAppeal.STATUS_INIT);
			globalUserService.mergeWeiboAppeal(appeal);
		} else {
			boolean initWeiboAppeal = globalUserService.initWeiboAppeal(uid, false);//force merge
			if(initWeiboAppeal==false) {
				logger.warn("initWeiboAppeal fail, uid:"+uid);
				return;
			}
		}
		exporter.setBasePriority(basePriority);
		exporter.setProperty("uid", uid);
		exporter.setProperty("nickName", user.getNickName());
		exporter.setProperty("loginName", robotUser.getLoginName());
		exporter.setProperty("loginPwd", robotUser.getLoginPwd());
		exporter.setProperty("appealWord", getAppealWord());
		Date startTime = getStartTime();
		exporter.setTimes(startTime, SpecialDateUtil.afterNow(30));
		exporter.setUid(User.UID_NOT_LOGIN);
		exporter.send();
	}

	private synchronized Date getStartTime() {
		Date startTime = new Date(Math.max(System.currentTimeMillis(), lastTime));
		lastTime = startTime.getTime() + interval;
		return startTime;
	}

	private String getAppealWord() {
		return checkPunctuations(appealFirsts.get(), "，")
				+ checkPunctuations(appealMiddles.get(), "，")
				+ appealLasts.get();
	}
	
	private String checkPunctuations(String word, String suffix) {
		if(word.length()==0) {
			return word;
		} else {
			return punctuations.contains(word.charAt(word.length()-1))?word:word+suffix;
		}
	}

	@Override
	public void handleResult(Date updateTime, Long resultObject, Map<String, Object> contextContents, Long taskUid) {
		Long uid = (Long) contextContents.get("uid");
		WeiboAppeal weiboAppeal = new WeiboAppeal();
		weiboAppeal.setUid(uid);
		weiboAppeal.setStartTime(updateTime);
		weiboAppeal.setStatus(WeiboAppeal.STATUS_START);
		globalUserService.mergeWeiboAppeal(weiboAppeal);
	}

}
