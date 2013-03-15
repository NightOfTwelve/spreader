package com.nali.spreader.spider.service.impl;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import javax.annotation.PostConstruct;

import org.apache.commons.configuration.AbstractConfiguration;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.helper.HttpConnection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.nali.spreader.data.Content;
import com.nali.spreader.data.KeyValue;
import com.nali.spreader.dto.HotWeiboDto;
import com.nali.spreader.dto.WeiboAndComments;
import com.nali.spreader.service.impl.ContentService;
import com.nali.spreader.spider.exceptions.WeiboRevisionException;
import com.nali.spreader.spider.service.ICommentsService;
import com.nali.spreader.spider.utils.WeiboMsg;

@Service
public class CommentsService implements ICommentsService {
	private static Logger logger = Logger.getLogger(CommentsService.class);
	private final ReentrantLock lock = new ReentrantLock();
	private static final String DEFAULT_AGENT = "Mozilla/5.0 (Windows NT 6.1; rv:17.0) Gecko/20100101 Firefox/17.0 FirePHP/0.7.1";
	private static final String COMMENT_PAGE_URL = "http://weibo.com/aj/comment/big?";
	private static final String WEIBO_TEXT_TAG = "div.WB_text>p>em";
	private static final String WEIBO_MAX_PAGE_TAG = "div.W_pages_minibtn>a";
	private static final String COOKIES_KEY = "spreader_spider_weibo_cookies";
	private static final String HOT_WEIBO_URL = "http://hot.weibo.com/";
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	/**
	 * 登录网站的cookies
	 */
	public static String cookies;

	// public CommentsService() {
	// init();
	// }

	@PostConstruct
	public void init() {
		readCookieByRedis();
		if (cookies == null) {
			readCookieByProperties();
		}
	}

	/**
	 * 通过redis读取cookies
	 */
	private void readCookieByRedis() {
		cookies = (String) redisTemplate.opsForValue().get(COOKIES_KEY);
	}

	/**
	 * 配置文件方式读取cookies
	 */
	private void readCookieByProperties() {
		try {
			AbstractConfiguration.setDefaultListDelimiter('~');
			URL url = CommentsService.class
					.getResource("/spider/cookies.properties");
			Configuration cfg = new PropertiesConfiguration(url);
			cookies = cfg.getString("user.spider.Cookie");
			logger.info("cookies>>>" + cookies);
		} catch (ConfigurationException e) {
			logger.error("read properties fail", e);
		}
	}

	@Override
	public WeiboAndComments getWeiboAndComments(String weiboUrl, int page) {
		Assert.notNull(weiboUrl, "url is null");
		if (lock.tryLock()) {
			try {
				WeiboAndComments data = analysisWeiboAndComments(weiboUrl, page);
				return data;
			} catch (Exception e) {
				logger.error("getWeiboAndComments error", e);
			} finally {
				lock.unlock();
			}
		}
		return null;
	}

	/**
	 * 获取Response
	 * 
	 * @param url
	 * @return
	 */
	// TODO
	public Response getResponse(String url) {
		readCookieByProperties();// 用完去掉这行
		if (StringUtils.isBlank(url)) {
			throw new IllegalArgumentException(
					" getResponse error,weibo url is blank");
		}
		Response resp;
		try {
			resp = (Response) Jsoup.connect(url).userAgent(DEFAULT_AGENT)
					.header("Cookie", cookies).execute();
			return resp;
		} catch (Exception e) {
			logger.error(" get Document error,url:" + url, e);
		}
		return null;
	}

	/**
	 * 获取微博的内容
	 * 
	 * @param els
	 * @return
	 */
	private String getWeibo(Elements els) {
		if (els == null) {
			throw new IllegalArgumentException("get weibo Elements error");
		}
		if (els.size() > 0) {
			return els.get(0).childNode(0).toString();
		} else {
			throw new IllegalArgumentException("get weibo Elements size = 0");
		}
	}

	/**
	 * 分析微博页面获取微博的内容和评论
	 * 
	 * @param weiboUrl
	 * @param page
	 * @return
	 * @throws IOException
	 */
	private WeiboAndComments analysisWeiboAndComments(String weiboUrl, int page)
			throws IOException {
		WeiboAndComments data = new WeiboAndComments();
		Response response = getResponse(weiboUrl);
		if (response == null) {
			logger.error("url:" + weiboUrl + "can not get response");
			return data;
		}
		Document doc = response.parse();
		try {
			Elements scriptEls = getElementsByScriptTag(doc);
			Elements weiboEls = getWeiboElements(scriptEls, WEIBO_TEXT_TAG);
			Elements pageEls = getWeiboElements(scriptEls, WEIBO_MAX_PAGE_TAG);
			String weibo = getWeibo(weiboEls);
			int maxPage = getMaxPage(pageEls);
			if (page > maxPage) {
				page = maxPage;
			}
			String mid = getIdAndMid(pageEls);
			List<String> comments = getComments(mid, page);
			data.setComments(comments);
			data.setWeibo(weibo);
			data.setPage(page);
			return data;
		} catch (Exception e) {
			logger.error("get weibo and comment error,weibo url:" + weiboUrl);
		}
		return null;
	}

	/**
	 * 获取DIV WeiboDetail 的页面元素
	 * 
	 * @param jsElements
	 * @return
	 */
	private String getWeiboDetailHtml(Elements jsElements) {
		for (Element el : jsElements) {
			String script = el.toString();
			if (script.indexOf("pl_content_weiboDetail") > 1) {
				String jsonStr = el.childNode(0).toString();
				String htmlStr = jsonStr.substring(
						jsonStr.indexOf("html\":\"") + 7,
						jsonStr.lastIndexOf("\""));
				return WeiboMsg.decode(htmlStr);
			}
		}
		return null;
	}

	/**
	 * 获取分页的URL参数
	 * 
	 * @param url
	 * @return
	 */
	private String getIdAndMid(Elements els) {
		if (!els.isEmpty() && els.size() > 0) {
			Element el = els.get(els.size() - 2);
			if (el != null) {
				String param = el.attr("action-data");
				if (StringUtils.isNotEmpty(param)) {
					return param.trim().substring(0, param.indexOf("&page"));
				}
			}
		}
		return null;
	}

	/**
	 * 获取最大分页数
	 * 
	 * @param url
	 * @return
	 */
	private int getMaxPage(Elements els) {
		if (els == null) {
			throw new IllegalArgumentException(
					" get max page error,els is null");
		}
		if (!els.isEmpty() && els.size() > 0) {
			Element el = els.get(els.size() - 2);
			if (el != null) {
				String maxPage = el.text();
				return Integer.parseInt(maxPage);
			}
		}
		return 1;
	}

	/**
	 * 获取所有的script
	 * 
	 * @param doc
	 * @return
	 */
	private Elements getElementsByScriptTag(Document doc) {
		if (doc == null) {
			throw new IllegalArgumentException("Document is null");
		}
		Elements els = doc.getElementsByTag("script");
		return els;
	}

	/**
	 * 获取某个特定标签的元素
	 * 
	 * @param els
	 * @param tag
	 * @return
	 */
	private Elements getWeiboElements(Elements els, String tag) {
		String weiboHtml = getWeiboDetailHtml(els);
		if (weiboHtml != null) {
			Document weiboDoc = Jsoup.parse(weiboHtml);
			return weiboDoc.select(tag);
		} else {
			throw new WeiboRevisionException("get weibo Elements error,tag:"
					+ tag);
		}
	}

	private String getContentWeiboDetailHtml(String jsonHtml) {
		String htmlStr = jsonHtml.substring(jsonHtml.indexOf("html\":\"") + 7,
				jsonHtml.lastIndexOf("\""));
		return WeiboMsg.decode(htmlStr);
	}

	/**
	 * 分页获取评论
	 * 
	 * @param url
	 * @param page
	 * @return
	 */

	private List<String> getComments(String mid, int page) {
		List<String> comments = new ArrayList<String>();
		for (int i = 1; i <= page; i++) {
			StringBuffer commentUrl = new StringBuffer(COMMENT_PAGE_URL);
			commentUrl.append(mid);
			commentUrl.append("&page=" + i);
			comments.addAll(getCommentByPage(commentUrl.toString()));
		}
		return comments;
	}

	private List<String> getCommentByPage(String commentPageUrl) {
		Response res = getResponse(commentPageUrl);
		String jsonData = res.body();
		String html = getContentWeiboDetailHtml(jsonData);
		List<String> list = read(html);
		return list;
	}

	private List<KeyValue<String, String>> getHotWeiboEntrance() {
		List<KeyValue<String, String>> entrance = new ArrayList<KeyValue<String, String>>();
		Response response = getResponse(HOT_WEIBO_URL);
		if (response != null) {
			Document doc;
			try {
				doc = response.parse();
				Elements hotLeft = doc.select("div.hot_leftnavbox>ul");
				for (Element li : hotLeft) {
					Elements hrefEls = li.select("a[href]");
					for (Element entrHref : hrefEls) {
						String href = entrHref.attr("href");
						List<Node> nodes = entrHref.childNodes();
						KeyValue<String, String> kv = new KeyValue<String, String>();
						if (nodes.size() > 0) {
							TextNode entrNode = (TextNode) nodes.get(1);
							if (entrNode != null) {
								String entrText = entrNode.text();
								kv.setKey(entrText);
								kv.setValue(href);
								entrance.add(kv);
							}
						}
					}
				}
			} catch (IOException e) {
				if (logger.isDebugEnabled()) {
					logger.debug(HOT_WEIBO_URL + ",parse error", e);
				}
			}
		} else {
			if (logger.isDebugEnabled()) {
				logger.debug(HOT_WEIBO_URL + ",response is null");
			}
		}
		return entrance;
	}

	private List<String> read(String html) {
		Document doc = Jsoup.parse(html);
		Elements els = doc.select("dl.comment_list>dd");
		List<String> comments = new ArrayList<String>();
		for (Element e : els) {
			List<Node> cnode = e.childNodes();
			StringBuffer buff = new StringBuffer();
			for (Node n : cnode) {
				String nodeName = n.nodeName();
				String text = n.attr("text");
				if ("#text".equals(nodeName)) {
					if (StringUtils.isBlank(text)) {
						continue;
					} else {
						buff.append(text);
					}
				}
				if ("a".equals(nodeName)) {
					String atUser = readAtUser(n);
					String href = readHref(n);
					if (StringUtils.isNotEmpty(atUser)) {
						buff.append(atUser);
					}
					if (StringUtils.isNotEmpty(href)) {
						buff.append(href);
					}
				}
				String classType = n.attr("class");
				if ("S_txt2".equals(classType)) {
					break;
				}
				if ("img".equals(nodeName)) {
					String face = readFace(n);
					if (StringUtils.isNotEmpty(face)) {
						buff.append(face);
					}
				}
			}
			if (StringUtils.isNotBlank(buff.toString())) {
				comments.add(getString(buff.toString()));
			}
		}
		return comments;
	}

	private String readAtUser(Node n) {
		String usercard = n.attr("usercard");
		if (StringUtils.isNotBlank(usercard) && usercard.startsWith("name=")) {
			return n.childNode(0).toString();
		}
		return null;
	}

	private String readHref(Node n) {
		String actionType = n.attr("action-type");
		if (StringUtils.isNotEmpty(actionType)
				&& "feed_list_url".equals(actionType)) {
			return n.attr("href");
		}
		return null;
	}

	private String readFace(Node n) {
		String face = n.attr("type");
		String faceTitle = n.attr("title");
		if ("face".equals(face) && StringUtils.isNotEmpty(faceTitle)) {
			return faceTitle;
		}
		return null;
	}

	private String getString(String buff) {
		return buff.substring(buff.indexOf("：") + 1).trim();
	}

	@Override
	public void settingCookies(String cookies) {
		redisTemplate.opsForValue().set(COOKIES_KEY, cookies);
		readCookieByRedis();
	}

	private List<HotWeiboDto> getHotWeiboByTitle(String titleUrl, String title,
			int page) {
		if (page <= 0) {
			page = 1;
		}
		if (page > 10) {
			page = 10;
		}
		List<HotWeiboDto> list = new ArrayList<HotWeiboDto>();
		for (int i = 1; i <= page; i++) {
			StringBuffer buff = new StringBuffer(titleUrl).append("&page=");
			buff.append(i);
			Response response = getResponse(buff.toString());
			Document doc;
			try {
				doc = response.parse();
				Elements eles = doc.select("div.WB_feed_type");
				for (Element el : eles) {
					HotWeiboDto dto = new HotWeiboDto();
					Elements infoEls = el.select("div.WB_text");
					if (infoEls != null) {
						String content = infoEls.text();
						Elements urlEls = el.select("a.WB_time");
						if (urlEls != null) {
							String url = urlEls.attr("href");
							dto.setTitle(title);
							dto.setContent(content);
							dto.setUrl(url);
							list.add(dto);
						} else {
							logger.error("a.WB_time not found,url:"
									+ buff.toString());
						}
					} else {
						logger.error("div.WB_text not found,url:"
								+ buff.toString());
					}
				}
			} catch (IOException e) {
				logger.error("url:" + buff.toString() + ",parse error", e);
			}
		}
		return list;
	}

	public static void main(String[] args) throws IOException {
		// 分页 /?v=1299&page=2
		// CommentsService cs = new CommentsService();
		// Response response = cs.getResponse("http://hot.weibo.com/?v=1299");
		// Document doc = response.parse();
		// Elements eles = doc.select("div.WB_feed_type");
		// for (Element el : eles) {
		// Elements infoels = el.select("div.WB_text");
		// String content = infoels.text();
		// Elements urlels = el.select("a.WB_time");
		// String url = urlels.attr("href");
		// System.out.println(content);
		// System.out.println(url);
		// }
		ContentService cs = new ContentService();
		Content c = cs.parseUrl("http://weibo.com/1494759712/znmdSA4WM");
		System.out.println(c.toString());
		
	}
}
