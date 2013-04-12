package com.nali.spreader.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.dom4j.IllegalAddException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nali.common.model.Limit;
import com.nali.common.pagination.PageResult;
import com.nali.common.util.CollectionUtils;
import com.nali.spreader.controller.basectrl.BaseController;
import com.nali.spreader.dto.AppleAppCurrentTopDto;
import com.nali.spreader.dto.AppleAppHistoryDto;
import com.nali.spreader.spider.service.IAppleAppsTopService;

/**
 * apple app 排行榜
 * 
 * @author xiefei
 * 
 */
@Controller
@RequestMapping(value = "/appletop")
public class AppleAppTopController extends BaseController {
	@Autowired
	private IAppleAppsTopService appleAppsTopService;

	@Override
	public String init() {
		return "/show/main/AppleAppTopShow";
	}

	/**
	 * 查询历史排行曲线
	 * 
	 * @param appId
	 * @param genreId
	 * @param popId
	 * @param startCreateDate
	 * @param endCreateDate
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/histop")
	public String hisTopData(Long appId, Integer genreId, Integer popId,
			Date startCreateDate, Date endCreateDate) {
		String startDate = null;
		String endDate = null;
		Map<String, List<AppleAppHistoryDto>> m = CollectionUtils.newHashMap(1);

		if (startCreateDate != null) {
			startDate = formatDate(startCreateDate);
		}
		if (endCreateDate != null) {
			endDate = formatDate(endCreateDate);
		}
		if (StringUtils.isBlank(startDate)) {
			throw new IllegalAddException(" startCreateDate is null ");
		}
		if (StringUtils.isNotBlank(startDate) && StringUtils.isBlank(endDate)) {
			List<AppleAppHistoryDto> list = appleAppsTopService.getAppDayTop(
					genreId, appId, popId, startDate);
			m.put("list", list);
			return write(list);
		} else {
			List<AppleAppHistoryDto> list = appleAppsTopService.getAppDayTop(
					genreId, appId, popId, startDate, endDate);
			m.put("list", list);
			return write(list);
		}
	}

	/**
	 * 当前排行信息
	 * 
	 * @param appId
	 * @param genreId
	 * @param popId
	 * @param start
	 * @param limit
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/currtop")
	public String currTopData(Long appId, Integer genreId, Integer popId,
			Integer start, Integer limit) {
		Limit lit = initLimit(start, limit);
		PageResult<AppleAppCurrentTopDto> data = appleAppsTopService
				.getCurrentTopDto(appId, genreId, popId, lit);
		return write(data);
	}

	private String formatDate(Date date) {
		return DateFormatUtils.format(date, "yyyyMMdd");
	}
}
