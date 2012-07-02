package com.nali.spreader.workshop;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nali.spreader.constants.Channel;
import com.nali.spreader.constants.Website;
import com.nali.spreader.data.KeyValue;
import com.nali.spreader.data.User;
import com.nali.spreader.factory.PassiveWorkshop;
import com.nali.spreader.factory.SimpleActionConfig;
import com.nali.spreader.factory.base.SingleTaskMachineImpl;
import com.nali.spreader.factory.exporter.SingleTaskExporter;
import com.nali.spreader.factory.passive.Input;
import com.nali.spreader.service.IGlobalUserService;
import com.nali.spreader.util.SpecialDateUtil;

@Component
public class PostWeiboContent extends SingleTaskMachineImpl implements
		PassiveWorkshop<KeyValue<Long, String>, KeyValue<Long, Long>> {
	private static Logger logger = Logger.getLogger(PostWeiboContent.class);
	@Autowired
	private IGlobalUserService globalUserService;

	public PostWeiboContent() {
		super(SimpleActionConfig.postWeiboContent, Website.weibo, Channel.normal);
	}

	@Override
	public void work(KeyValue<Long, String> data, SingleTaskExporter exporter) {
		Long uid = data.getKey();
		String text = data.getValue();
		work(uid, text, null, exporter);
	}

	@Input
	public void work(SingleTaskExporter exporter, KeyValue<KeyValue<Long, String>, Date> data) {
		KeyValue<Long, String> param = data.getKey();
		Long uid = param.getKey();
		String text = param.getValue();
		Date startTime = data.getValue();
		work(uid, text, startTime, exporter);
	}

	private void work(Long uid, String text, Date startTime, SingleTaskExporter exporter) {
		exporter.setProperty("id", uid);
		exporter.setProperty("content", text);
		User user = globalUserService.getUserById(uid);
		exporter.setProperty("nickname", user.getNickName());
		if (startTime != null) {
			exporter.setTimes(startTime, SpecialDateUtil.afterToday(3));
			exporter.send();
		} else {
			exporter.send(uid, SpecialDateUtil.afterToday(3));
		}
	}

	@Override
	public void handleResult(Date updateTime, KeyValue<Long, Long> uidToContentIdMap) {
		// 目前只是打打酱油
		// TODO result改为 KeyValue<Long, KeyValue<Long, Long>>，记录发过的原帖id
		logger.info("user[" + uidToContentIdMap.getKey() + "] post a content["
				+ uidToContentIdMap.getValue() + "]");
	}

}
