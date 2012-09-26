package com.nali.spreader.workshop.keyword;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nali.spreader.constants.Channel;
import com.nali.spreader.constants.Website;
import com.nali.spreader.data.KeyValue;
import com.nali.spreader.factory.PassiveWorkshop;
import com.nali.spreader.factory.SimpleActionConfig;
import com.nali.spreader.factory.TaskProduceLine;
import com.nali.spreader.factory.base.SingleTaskMachineImpl;
import com.nali.spreader.factory.exporter.SingleTaskExporter;
import com.nali.spreader.factory.passive.AutowireProductLine;
import com.nali.spreader.model.ClientTask;
import com.nali.spreader.util.SpecialDateUtil;
import com.nali.spreader.workshop.other.WeiboRobotUserHolder;

/**
 * 爬取微话题的入口
 * 
 * @author xiefei
 * 
 */
@Component
public class FetchKeywordEntrance extends SingleTaskMachineImpl implements
		PassiveWorkshop<Object, List<KeyValue<String, String>>> {
	@Autowired
	private WeiboRobotUserHolder weiboRobotUserHolder;
	@AutowireProductLine
	private TaskProduceLine<String> fetchKeyword;

	public FetchKeywordEntrance() {
		super(SimpleActionConfig.fetchKeywordEntrance, Website.weibo, Channel.fetch);
	}

	@Override
	public void work(Object data, SingleTaskExporter exporter) {
		exporter.setBasePriority(ClientTask.BASE_PRIORITY_MAX);
		exporter.send(weiboRobotUserHolder.getRobotUid(), SpecialDateUtil.afterNow(2));
	}

	@Override
	public void handleResult(Date updateTime, List<KeyValue<String, String>> result) {
		if (result.size() > 0) {
			for (KeyValue<String, String> kv : result) {
				String entrance = kv.getKey();
				if (StringUtils.isNotEmpty(entrance)) {
					fetchKeyword.send(entrance);
				}
			}
		}
	}
}