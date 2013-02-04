package com.nali.spreader.workshop.system;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nali.spreader.constants.Channel;
import com.nali.spreader.constants.Website;
import com.nali.spreader.data.KeyValue;
import com.nali.spreader.dto.WeiboAndComments;
import com.nali.spreader.factory.PassiveWorkshop;
import com.nali.spreader.factory.SimpleActionConfig;
import com.nali.spreader.factory.base.SingleTaskMachineImpl;
import com.nali.spreader.factory.config.SystemObject;
import com.nali.spreader.factory.exporter.SingleTaskExporter;
import com.nali.spreader.remote.ISegmenAnalyzerService;
import com.nali.spreader.spider.service.ICommentsService;

@Component
public class FetchWeiboDetail extends SingleTaskMachineImpl
		implements
		PassiveWorkshop<KeyValue<String, Integer>, KeyValue<String, List<String>>>,
		SystemObject {
	@Autowired
	private ISegmenAnalyzerService segmenAnalyzerService;
	@Autowired
	private ICommentsService commentsService;

	public FetchWeiboDetail() {
		super(SimpleActionConfig.fetchWeiboDetail, Website.weibo,
				Channel.normal);
	}

	@Override
	public void work(KeyValue<String, Integer> data, SingleTaskExporter exporter) {
		String weiboUrl = data.getKey();
		Integer pages = data.getValue();
		WeiboAndComments wac = commentsService.getWeiboAndComments(weiboUrl,
				pages);
		if (wac != null) {
			String weibo = wac.getWeibo();
			List<String> comments = wac.getComments();
			// 保存回复
			for (String reply : comments) {
				segmenAnalyzerService.assignReplayId(reply);
			}
			// 分词建索引 放到线程池去做
			segmenAnalyzerService.execuAnalysisSegmen(weibo, comments);
		}
	}

	@Override
	public void handleResult(Date updateTime,
			KeyValue<String, List<String>> result) {

	}
}
