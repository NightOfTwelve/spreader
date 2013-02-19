package com.nali.spreader.analyzer.system;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nali.common.model.Limit;
import com.nali.spreader.config.ContentQueryParamsDto;
import com.nali.spreader.data.Content;
import com.nali.spreader.data.KeyValue;
import com.nali.spreader.factory.TaskProduceLine;
import com.nali.spreader.factory.config.SystemObject;
import com.nali.spreader.factory.config.desc.ClassDescription;
import com.nali.spreader.factory.passive.AutowireProductLine;
import com.nali.spreader.factory.regular.RegularAnalyzer;
import com.nali.spreader.service.IContentService;

@Component
@ClassDescription("爬取微博内容及回复列表")
public class FetchWeiboDetailAnalyzer implements RegularAnalyzer, SystemObject {
	private static final Logger logger = Logger
			.getLogger(FetchWeiboDetailAnalyzer.class);
	@AutowireProductLine
	private TaskProduceLine<KeyValue<String, Integer>> fetchWeiboDetail;
	@Autowired
	private IContentService contentService;

	@Override
	public String work() {
		Long lastContentId = contentService.getLastFetchContentId();
		ContentQueryParamsDto dto = new ContentQueryParamsDto();
		dto.setLastId(lastContentId);
		Limit lit = Limit.newInstanceForLimit(0, 5);
		dto.setLit(lit);
		List<Content> list = contentService.findContentPageResult(dto)
				.getList();
		for (Content c : list) {
			String url = contentService.getContentUrl(c);
			try {
				if (StringUtils.isNotBlank(url)) {
					KeyValue<String, Integer> kv = new KeyValue<String, Integer>();
					kv.setKey(url);
					kv.setValue(5);
					Thread.sleep(5000);
					fetchWeiboDetail.send(kv);
				}
			} catch (Exception e) {
				logger.error(e);
			}
		}
		if (list.size() > 0) {
			contentService.recordLastFetchContentId(list.get(list.size() - 1)
					.getId());
		}
		// List<String> weibo = new ArrayList<String>();
		// weibo.add("http://weibo.com/1193491727/zfPQIpe1z");
		// weibo.add("http://weibo.com/1193491727/zf9VMeWC9");
		// weibo.add("http://weibo.com/1639498782/zfO8mdEys");
		// for (String url : weibo) {
		// try {
		// if (url != null) {
		// KeyValue<String, Integer> kv = new KeyValue<String, Integer>();
		// kv.setKey(url);
		// kv.setValue(5);
		// Thread.sleep(5000);
		// fetchWeiboDetail.send(kv);
		// }
		// } catch (Exception e) {
		// logger.error(e);
		// }
		// }
		return null;
	}
}
