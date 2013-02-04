package com.nali.spreader.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nali.spreader.controller.basectrl.BaseController;
import com.nali.spreader.data.ReplySearch;
import com.nali.spreader.lucene.service.ISearchService;
import com.nali.spreader.remote.ISegmenAnalyzerService;

@Controller
@RequestMapping(value = "/reply")
public class ReplySearchController extends BaseController {
	@Autowired
	private ISegmenAnalyzerService segmenAnalyzerService;
	@Autowired
	private ISearchService searchService;

	@Override
	public String init() {
		return "/show/main/ReplySearchShow";
	}

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
}
