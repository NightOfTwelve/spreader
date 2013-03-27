package com.nali.spreader.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nali.common.util.CollectionUtils;
import com.nali.spreader.controller.basectrl.BaseController;
import com.nali.spreader.data.KeyValue;
import com.nali.spreader.data.ReplySearch;
import com.nali.spreader.dto.HotWeiboDto;
import com.nali.spreader.lucene.service.ISearchService;
import com.nali.spreader.remote.ISegmenAnalyzerService;
import com.nali.spreader.service.IContentService;
import com.nali.spreader.spider.service.ICommentsService;
import com.nali.spreader.util.PerformanceLogger;

@Controller
@RequestMapping(value = "/reply")
public class ReplySearchController extends BaseController {
	private static final Logger logger = Logger
			.getLogger(ReplySearchController.class);
	@Autowired
	private ISegmenAnalyzerService segmenAnalyzerService;
	@Autowired
	private ISearchService searchService;
	@Autowired
	private ICommentsService commentsService;
	@Autowired
	private IContentService contentService;
	private static AtomicBoolean isRunGetHotWei = new AtomicBoolean(false);

	@Override
	public String init() {
		return "/show/main/ReplySearchShow";
	}

	/**
	 * 根据微博搜索相关内容
	 * 
	 * @param weibo
	 * @param rows
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/search")
	public String search(String weibo, Integer rows) {
		List<ReplySearch> list = new ArrayList<ReplySearch>();
		Map<String, List<ReplySearch>> m = new HashMap<String, List<ReplySearch>>();
		if (rows == null) {
			rows = 10;
		}
		if (StringUtils.isNotBlank(weibo)) {
			String keywords = segmenAnalyzerService
					.getWeiboSearchKeywords(weibo);
			list = searchService.searchReply(keywords,
					new String[] { "content" }, rows);
		}
		m.put("list", list);
		return write(m);
	}

	/**
	 * 设置cookies
	 * 
	 * @param cookies
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/cookies")
	public String settingCookies(String cookies) {
		Map<String, Boolean> map = CollectionUtils.newHashMap(1);
		map.put("success", false);
		try {
			commentsService.settingCookies(cookies);
			map.put("success", true);
		} catch (Exception e) {
			logger.error("settingCookies error", e);
		}
		return write(map);
	}

	/**
	 * 清除最后的爬取记录
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/clearrecord")
	public String clearSpiderRecord() {
		contentService.clearLastFetchContentId();
		return null;
	}

	/**
	 * 手动建立索引
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/index")
	public String index() {
		Map<String, Boolean> m = CollectionUtils.newHashMap(1);
		m.put("lock", false);
		boolean lock = segmenAnalyzerService.isLock();
		if (!lock) {
			m.put("lock", true);
		} else {
			Thread t = new Thread(new Runnable() {
				@Override
				public void run() {
					PerformanceLogger.infoStart();
					segmenAnalyzerService.createReplyIndex();
					PerformanceLogger.info("建立索引完毕");
				}
			});
			t.setName("manualIndexThread");
			t.start();
		}
		return write(m);
	}

	/**
	 * 爬取并分析热门微博
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/hotweibo")
	public String hotWeibo() {
		Map<String, Boolean> m = CollectionUtils.newHashMap(1);
		if (isRunGetHotWei.compareAndSet(false, true)) {
			Thread t = new Thread(new Runnable() {
				@Override
				public void run() {
					getAndAnalysisHotWeibo();
				}
			});
			t.setName("getAndAnalysisHotWeiboThread");
			t.start();
			isRunGetHotWei.set(false);
			m.put("lock", false);
		} else {
			m.put("lock", true);
		}
		return write(m);
	}

	private void getAndAnalysisHotWeibo() {
		List<KeyValue<String, String>> entrance = commentsService
				.getHotWeiboEntrance();
		for (KeyValue<String, String> entr : entrance) {
			String title = entr.getKey();
			String entrUrl = entr.getValue();
			List<HotWeiboDto> hotWeibos = commentsService.getHotWeiboByTitle(
					entrUrl, title, 3);
			for (HotWeiboDto dto : hotWeibos) {
				String content = dto.getContent();
				String url = dto.getUrl();
				String weiboTitle = dto.getTitle();
				// 先保存微博
				contentService.assignContentId(url, content);
				segmenAnalyzerService.analysisHotWeiboSegmen(content,
						weiboTitle);
			}
		}
	}
}
