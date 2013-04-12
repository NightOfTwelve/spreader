package com.nali.spreader.spider.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.CharArrayBuffer;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nali.common.model.Limit;
import com.nali.common.pagination.PageResult;
import com.nali.spreader.dao.IAppleAppTopDao;
import com.nali.spreader.data.AppleAppCurrentTop;
import com.nali.spreader.data.AppleAppHistoryTop;
import com.nali.spreader.data.AppleAppInfo;
import com.nali.spreader.data.KeyValue;
import com.nali.spreader.dto.AppleAppCurrentTopDto;
import com.nali.spreader.dto.AppleAppHistoryDto;
import com.nali.spreader.spider.service.IAppleAppsTopService;

@Service
public class AppleAppsTopService implements IAppleAppsTopService {
	private static final Logger logger = Logger
			.getLogger(AppleAppsTopService.class);
	@Autowired
	private IAppleAppTopDao appleAppTopDao;
	private static final int DEFAULT_ROWS = 1000;
	private static final int DEFAULT_PAGESIZE = 100;
	private static final ObjectMapper objectMapper = new ObjectMapper();
	private static final DefaultHttpClient httpClient = new DefaultHttpClient();

	public static void main(String[] args) {
		// AppleAppsTopService as = new AppleAppsTopService();
		// List<Map<String, Object>> data = as.getTopData(6011, 10, 27, 0);
		// System.out.println(data);
		// String x = "1. 史上最牛的游戏2";
		// String[] b = StringUtils.split(x, '.');
		// System.out.println(Arrays.asList(b));
		// System.out.println(as.getPage(99));
		String x = DateFormatUtils.format(new Date(), "HH:mm");
		System.out.println(x);
	}

	private int getPage(int total) {
		if (total <= 0) {
			total = DEFAULT_ROWS;
		}
		int p = 1;
		if ((total % 100) == 0) {
			p = total / 100;
		} else {
			p = total / 100 + 1;
		}
		return p;
	}

	private void saveAppAndTop(List<Map<String, Object>> data,
			final int genreId, final int popId) {
		for (Map<String, Object> m : data) {
			Long appId = Long.parseLong((String) m.get("id"));
			String genre = (String) m.get("genre");
			String url = (String) m.get("url");
			String artworkUrl = (String) m.get("artwork_url");
			String name = (String) m.get("name");
			KeyValue<Integer, String> kv = getAppNameAndRanking(name);
			int ranking = kv.getKey();
			String appName = kv.getValue();
			if (genreId > 0) {
				appleAppTopDao.upsertAppInfo(appId, appName, url, artworkUrl,
						genre, genreId, popId);
			}
			Date rankTime = new Date();
			String createDate = DateFormatUtils.format(rankTime, "yyyyMMdd");
			appleAppTopDao.upsertHistoryTop(appId, genreId, createDate,
					rankTime, ranking, popId);
			appleAppTopDao.upsertCurrentTop(appId, genre, genreId, ranking,
					popId);
			logger.debug("ok,appId:" + appId);
		}
	}

	private KeyValue<Integer, String> getAppNameAndRanking(String appName) {
		String[] arr = StringUtils.split(appName, '.');
		int ranking = Integer.parseInt(arr[0].trim());
		String name = arr[1].trim();
		return new KeyValue<Integer, String>(ranking, name);
	}

	public List<Map<String, Object>> getTopData(int genreId, int pageSize,
			int popId, int pageNumbers) {
		String json = getTopJsonData(genreId, pageSize, popId, pageNumbers);
		Map<String, Object>[] maps = string2Array(json);
		if (ArrayUtils.isEmpty(maps)) {
			return new ArrayList<Map<String, Object>>();
		}
		Map<String, Object> map = maps[0];
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> list = (List<Map<String, Object>>) map
				.get("contentData");
		return list;
	}

	public String getTopJsonData(int genreId, int pageSize, int popId,
			int pageNumbers) {
		String url = getUrlByParams(genreId, pageSize, popId, pageNumbers);
		HttpPost post = getHttpPost(url);
		try {
			HttpResponse response = httpClient.execute(post);
			HttpEntity entity = response.getEntity();
			InputStream is = entity.getContent();
			return toString(is);
		} catch (IOException e) {
			logger.error("get response string error ", e);
		}
		return null;
	}

	private String toString(InputStream is) {
		try {
			Reader reader = new InputStreamReader(is, "utf-8");
			CharArrayBuffer buffer = new CharArrayBuffer(4096);
			char[] tmp = new char[1024];
			int l;
			while ((l = reader.read(tmp)) != -1) {
				buffer.append(tmp, 0, l);
			}
			return buffer.toString();
		} catch (IOException e) {
			logger.error(e, e);
		}
		return null;
	}

	private String getUrlByParams(int genreId, int pageSize, int popId,
			int pageNumbers) {
		StringBuilder builder = new StringBuilder(
				"https://itunes.apple.com/WebObjects/MZStore.woa/wa/topChartFragmentData?cc=cn");
		// genreId为0不限制分类
		if (genreId != AppleAppInfo.NO_GENRE) {
			builder.append("&genreId=");
			builder.append(genreId);
		}
		builder.append("&pageSize=");
		builder.append(pageSize);
		builder.append("&popId=");
		builder.append(popId);
		builder.append("&pageNumbers=");
		builder.append(pageNumbers);
		return builder.toString();
	}

	private HttpPost getHttpPost(String url) {
		HttpPost post = new HttpPost(url);
		post.setHeader("User-Agent", "iTunes-iPad/5.1.1 (2; 16GB; dt:74)");
		post.setHeader("Accept", "*/*");
		post.setHeader("X-Apple-Client-Versions", "GameCenter/2.0");
		post.setHeader("X-Apple-Partner", "origin.0");
		post.setHeader("X-Apple-Connection-Type", "WiFi");
		post.setHeader(
				"Referer",
				"https://itunes.apple.com/WebObjects/MZStore.woa/wa/viewTop?cc=cn&genreId=6001&df=1");
		post.setHeader("X-Apple-Store-Front", "143465-19,9");
		post.setHeader("X-Apple-Client-Application", "Software");
		post.setHeader("Accept-Language", "zh-cn");
		post.setHeader("Accept-Encoding", "gzip, deflate");
		post.setHeader("Connection", "keep-alive");
		post.setHeader("Proxy-Connection", "keep-alive");
		return post;
	}

	private Map<String, Object>[] string2Array(String json) {
		TypeReference<Map<String, Object>[]> ref = new TypeReference<Map<String, Object>[]>() {
		};
		try {
			if (json != null) {
				return objectMapper.readValue(json, ref);
			}
		} catch (JsonParseException e) {
			logger.error(e, e);
		} catch (JsonMappingException e) {
			logger.error(e, e);
		} catch (IOException e) {
			logger.error(e, e);
		}
		return null;
	}

	@Override
	public void fetchAppsTopByGenre(int genreId, int popId, int rows) {
		int page = getPage(rows);
		for (int i = 0; i < page; i++) {
			List<Map<String, Object>> data = getTopData(genreId,
					DEFAULT_PAGESIZE, popId, i);
			saveAppAndTop(data, genreId, popId);
		}
	}

	@Override
	public List<AppleAppHistoryDto> getAppDayTop(int genreId, Long appId,
			int popId, String createDate) {
		AppleAppHistoryTop top = appleAppTopDao.getHistoryTopByGenreAndId(
				genreId, appId, createDate, popId);
		List<Map<String, Object>> ranks = new ArrayList<Map<String, Object>>();
		if (top != null) {
			ranks = top.getRanking();
		}
		return formatDayRanking(ranks, genreId, appId);
	}

	private List<AppleAppHistoryDto> formatDayRanking(
			List<Map<String, Object>> ranks, int genreId, Long appId) {
		List<AppleAppHistoryDto> list = new ArrayList<AppleAppHistoryDto>();
		if (ranks != null) {
			AppleAppInfo app = appleAppTopDao.getAppInfoByAppId(appId);
			String appName = app.getAppName();
			for (Map<String, Object> m : ranks) {
				int rank = (Integer) m.get("rank");
				Date d = (Date) m.get("time");
				String hhmm = DateFormatUtils.format(d, "HH:mm");
				AppleAppHistoryDto dto = new AppleAppHistoryDto();
				dto.setAppId(appId);
				dto.setAppName(appName);
				dto.setRanking(rank);
				dto.setRankTime(hhmm);
				list.add(dto);
			}
		}
		return list;
	}

	@Override
	public List<AppleAppHistoryDto> getAppDayTop(int genreId, Long appId,
			int popId, String startCreateDate, String endCreateDate) {
		List<AppleAppHistoryTop> list = appleAppTopDao
				.getHistoryTopByGenreAndId(genreId, appId, startCreateDate,
						endCreateDate, popId);
		return formatDaysRanking(list, appId);
	}

	private List<AppleAppHistoryDto> formatDaysRanking(
			List<AppleAppHistoryTop> tops, final Long appId) {
		List<AppleAppHistoryDto> data = new ArrayList<AppleAppHistoryDto>();
		AppleAppInfo app = appleAppTopDao.getAppInfoByAppId(appId);
		String appName = app.getAppName();
		for (AppleAppHistoryTop top : tops) {
			String date = top.getCreateDate();
			Long rCount = top.getRankCount();
			Long uCount = top.getUpdateCount();
			int rank = (int) (rCount / uCount);
			AppleAppHistoryDto dto = new AppleAppHistoryDto();
			dto.setAppId(appId);
			dto.setAppName(appName);
			dto.setRankTime(date);
			dto.setRanking(rank);
			data.add(dto);
		}
		return data;
	}

	@Override
	public PageResult<AppleAppCurrentTop> getCurrentTop(Long appId,
			Integer genreId, Integer popId, Limit limit) {
		List<AppleAppCurrentTop> tops = appleAppTopDao.getCurrentTop(appId,
				genreId, popId, limit.offset, limit.maxRows);
		int count = appleAppTopDao.countCurrentTop(appId, genreId, popId);
		return new PageResult<AppleAppCurrentTop>(tops, limit, count);
	}

	@Override
	public PageResult<AppleAppCurrentTopDto> getCurrentTopDto(Long appId,
			Integer genreId, Integer popId, Limit limit) {
		PageResult<AppleAppCurrentTop> tops = getCurrentTop(appId, genreId,
				popId, limit);
		List<AppleAppCurrentTopDto> list = getAppleAppCurrentTopDto(tops
				.getList());
		return new PageResult<AppleAppCurrentTopDto>(list, limit,
				tops.getTotalCount());
	}

	private List<AppleAppCurrentTopDto> getAppleAppCurrentTopDto(
			List<AppleAppCurrentTop> tops) {
		List<AppleAppCurrentTopDto> data = new ArrayList<AppleAppCurrentTopDto>();
		for (AppleAppCurrentTop top : tops) {
			Long appId = top.getAppId();
			AppleAppInfo info = appleAppTopDao.getAppInfoByAppId(appId);
			AppleAppCurrentTopDto dto = new AppleAppCurrentTopDto();
			dto.setAppId(appId);
			dto.setAppName(info.getAppName());
			dto.setGenre(top.getGenre());
			dto.setGenreId(top.getGenreId());
			dto.setPopId(top.getPopId());
			dto.setRanking(top.getRanking());
			data.add(dto);
		}
		return data;
	}
}
