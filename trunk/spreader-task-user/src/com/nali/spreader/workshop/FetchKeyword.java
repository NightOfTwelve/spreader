package com.nali.spreader.workshop;

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
import com.nali.spreader.factory.base.SingleTaskMachineImpl;
import com.nali.spreader.factory.exporter.SingleTaskExporter;
import com.nali.spreader.service.IKeywordService;
import com.nali.spreader.util.SpecialDateUtil;

/**
 * 根据返回的入口爬取关键字
 * 
 * @author xiefei
 * 
 */
@Component
public class FetchKeyword extends SingleTaskMachineImpl implements
		PassiveWorkshop<String, List<KeyValue<Long, String>>> {
	@Autowired
	private WeiboRobotUserHolder weiboRobotUserHolder;
	@Autowired
	private IKeywordService keywordService;

	public FetchKeyword() {
		super(SimpleActionConfig.fetchKeyword, Website.weibo, Channel.fetch);
	}

	@Override
	public void work(String entrance, SingleTaskExporter exporter) {
		if (StringUtils.isNotEmpty(entrance)) {
			exporter.setProperty("entrance", entrance);
			exporter.send(weiboRobotUserHolder.getRobotUid(), SpecialDateUtil.afterNow(2));
		}
	}

	@Override
	public void handleResult(Date updateTime, List<KeyValue<Long, String>> result) {
		if (result.size() > 0) {
			for (KeyValue<Long, String> kv : result) {
				String keywordName = kv.getValue();
				if (StringUtils.isNotEmpty(keywordName)) {
					keywordService.getOrAssignKeywordIdByName(keywordName);
				}
			}
		}
	}
}