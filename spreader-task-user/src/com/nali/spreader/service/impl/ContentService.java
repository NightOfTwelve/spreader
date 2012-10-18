package com.nali.spreader.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.nali.common.model.Limit;
import com.nali.common.pagination.PageResult;
import com.nali.common.util.CollectionUtils;
import com.nali.dal.exception.UniqueKeyException;
import com.nali.spreader.config.ContentDto;
import com.nali.spreader.config.ContentQueryParamsDto;
import com.nali.spreader.config.KeywordQueryParamsDto;
import com.nali.spreader.constants.Website;
import com.nali.spreader.dao.IContentMassDataDao;
import com.nali.spreader.dao.ICrudKeywordDao;
import com.nali.spreader.dao.ICrudUserDao;
import com.nali.spreader.dao.ICrudUserTagDao;
import com.nali.spreader.dao.IKeywordDao;
import com.nali.spreader.dao.IUserDao;
import com.nali.spreader.data.Content;
import com.nali.spreader.data.ContentExample;
import com.nali.spreader.data.Keyword;
import com.nali.spreader.data.User;
import com.nali.spreader.data.UserExample;
import com.nali.spreader.data.UserTag;
import com.nali.spreader.data.UserTagExample;
import com.nali.spreader.dto.ContentKeywordInfoDto;
import com.nali.spreader.dto.PostWeiboContentDto;
import com.nali.spreader.service.IContentService;
import com.nali.spreader.service.IGlobalUserService;

@Service
public class ContentService implements IContentService {
	private static final Pattern urlPattern = Pattern.compile("http://.*/(\\d*)/(\\w*)");
	private static final Long DEFAULT_CATEGORY_ID = 0L;
	private static Logger logger = Logger.getLogger(ContentService.class);
	@Autowired
	private ContentCategoryMatch contentCategoryMatch;
	@Autowired
	private PostContent postContent;
	@Autowired
	private ICrudUserTagDao crudUserTagDao;
	@Autowired
	private IContentMassDataDao contentMassDataDao;
	@Autowired
	private IUserDao userDao;
	@Autowired
	private ICrudUserDao crudUserDao;
	@Autowired
	private IGlobalUserService globalUserService;
	@Autowired
	private IKeywordDao keywordDao;
	@Autowired
	private ICrudKeywordDao crudKeywordDao;

	@Override
	public Content getMatchedContent(Long uid) {
		UserTagExample example = new UserTagExample();
		example.createCriteria().andUidEqualTo(uid);
		List<UserTag> userTags = crudUserTagDao.selectByExample(example);
		Collections.shuffle(userTags);
		Set<Long> categorySet = new HashSet<Long>();
		Long contentId = null;
		for (UserTag userTag : userTags) {
			Long keywordId = userTag.getTagId();
			Keyword kw = crudKeywordDao.selectByPrimaryKey(keywordId);
			if (kw != null) {
				Long categorydId = kw.getCategoryId();
				categorySet.add(categorydId);
			}
		}
		Iterator<Long> iterator = categorySet.iterator();
		while (iterator.hasNext()) {
			contentId = contentCategoryMatch.popContentId(iterator.next());
			if (contentId != null) {
				break;
			}
		}
		if (contentId == null) {
			contentId = contentCategoryMatch.popContentId(DEFAULT_CATEGORY_ID);
			if (contentId == null) {
				return null;
			}
		}
		return contentMassDataDao.selectByPrimaryKey(contentId);
	}

	@Override
	public Date getAndTouchLastFetchTime(Long uid) {
		return userDao.getAndTouchLastFetchTime(uid);
	}

	@Override
	public void saveContent(Content content) {
		checkNotNull(content.getType());
		checkNotNull(content.getWebsiteId());
		checkNotNull(content.getWebsiteUid());
		checkNotNull(content.getEntry());
		Content exist = findByUniqueKey(content.getType(), content.getWebsiteId(),
				content.getWebsiteUid(), content.getEntry());
		if (exist != null) {
			if (exist.getWebsiteContentId() == null) {
				exist.setWebsiteContentId(content.getWebsiteContentId());
				this.assignContentId(content);
				registerRecommandContent(exist.getId(), exist.getUid());
			}
			return;
		}
		if (content.getUid() == null) {
			UserExample example = new UserExample();
			example.createCriteria().andWebsiteIdEqualTo(content.getWebsiteId())
					.andWebsiteUidEqualTo(content.getWebsiteUid());
			List<User> users = crudUserDao.selectByExample(example);
			if (users.size() == 0) {
				logger.error("user not exists websiteId,websiteUid:" + content.getWebsiteId() + ","
						+ content.getWebsiteUid());
				return;
			}
			content.setUid(users.get(0).getId());
		}
		Long contentId = this.assignContentId(content);
		registerRecommandContent(contentId, content.getUid());
	}

	private void registerRecommandContent(Long contentId, Long contentUid) {
		List<Long> categoryIds;
		UserTagExample example = new UserTagExample();
		example.createCriteria().andUidEqualTo(contentUid);
		List<UserTag> userTags = crudUserTagDao.selectByExample(example);
		List<Long> tagIds = new ArrayList<Long>();
		if (userTags.size() == 0) {
			categoryIds = Arrays.asList(DEFAULT_CATEGORY_ID);
		} else {
			for (UserTag userTag : userTags) {
				Long tagId = userTag.getTagId();
				tagIds.add(tagId);
			}
			KeywordQueryParamsDto param = new KeywordQueryParamsDto();
			param.setKeywordIds(tagIds);
			categoryIds = keywordDao.selectCategoryIdsByKeywordIds(param);
		}
		contentCategoryMatch.registerContentId(contentId, categoryIds);
	}

	private Content findByUniqueKey(Integer type, Integer websiteId, Long websiteUid, String entry) {
		ContentExample example = new ContentExample();
		example.createCriteria().andTypeEqualTo(type).andWebsiteIdEqualTo(websiteId)
				.andWebsiteUidEqualTo(websiteUid).andEntryEqualTo(entry);
		Content content = contentMassDataDao.selectContentsByUniqueKey(type, websiteId, websiteUid,
				entry);
		return content;
	}

	private void checkNotNull(Object param) {
		if (param == null) {
			throw new IllegalArgumentException("property is null");
		}
	}

	@Override
	public Content getContentById(Long contentId) {
		return contentMassDataDao.selectByPrimaryKey(contentId);
	}

	@Override
	public List<Long> findContentIdByDto(ContentDto dto) {
		return userDao.findContentIdByDto(dto);
	}

	@Override
	public Set<Long> getPostContentIds(Long uid) {
		return postContent.getPostContentIds(uid);
	}

	@Override
	public void addPostContentId(Long uid, Long contentId) {
		postContent.addPostContentId(uid, contentId);
	}

	@Override
	public Content assignContent(Integer websiteId, Long websiteUid, String entry) {
		Content exist = findByUniqueKey(Content.TYPE_WEIBO, websiteId, websiteUid, entry);
		if (exist != null) {
			return exist;
		}
		Content content = new Content();
		content.setType(Content.TYPE_WEIBO);
		content.setWebsiteId(websiteId);
		content.setWebsiteUid(websiteUid);
		content.setEntry(entry);
		Long uid = globalUserService.getOrAssignUid(websiteId, websiteUid);
		content.setUid(uid);
		return assignContent(content);
	}

	@Override
	public Content parseUrl(String url) {
		Matcher matcher = urlPattern.matcher(url);
		if (matcher.matches()) {
			Long websiteUid = Long.valueOf(matcher.group(1));
			String entry = matcher.group(2);
			return assignContent(Website.weibo.getId(), websiteUid, entry);
		} else {
			return null;
		}
	}

	/**
	 * 匹配在一个字符串中指定的字符串出现的次数
	 * 
	 * @param sr
	 * @param ar
	 * @return
	 */
	private int charMatch(String sr, char ar) {
		int count = 0;
		int num = 0;
		int temp = 0;
		while ((sr.length() - temp) >= 1) {
			num = sr.indexOf(ar, temp);
			if (num == -1) {
				temp = sr.length();
			} else {
				temp = num + 1;
				count++;
			}
		}
		return count;
	}

	/**
	 * 保存并分配contentId的统一调用方法
	 * 
	 * @param content
	 * @return
	 */
	private Long saveOrassignContentId(Content content) {
		Integer contentType = content.getType();
		Integer websiteId = content.getWebsiteId();
		Long websiteUid = content.getWebsiteUid();
		String entry = content.getEntry();
		// 唯一键必须都不为空
		if (contentType != null && websiteId != null && websiteUid != null
				&& StringUtils.isNotEmpty(entry)) {
			String text = content.getContent();
			// 设置内容长度
			if (StringUtils.isEmpty(text)) {
				content.setAtCount(0);
				content.setContentLength(0);
			} else {
				// 设置@数
				int atCount = this.charMatch(text, Content.AT_STR.charAt(0));
				content.setAtCount(atCount);
				content.setContentLength(text.length());
			}
			if (content.getUid() == null) {
				Long uid = this.globalUserService.getOrAssignUid(websiteId, websiteUid);
				content.setUid(uid);
			}
			Content tmp = this.findByUniqueKey(contentType, websiteId, websiteUid, entry);
			if (tmp != null) {
				content.setId(tmp.getId());
				contentMassDataDao.updateContent(content);
				return tmp.getId();
			} else {
				try {
					return contentMassDataDao.insertContent(content);
				} catch (UniqueKeyException e) {
					return saveOrassignContentId(content);
				}
			}
		} else {
			throw new IllegalArgumentException("uniqueKey contains null value");
		}
	}

	@Override
	public Long assignContentId(Content content) {
		Assert.notNull(content, "content is null");
		Content refContent = content.getRefContent();
		Long refContentId = null;
		if (refContent != null) {
			refContentId = assignContentId(refContent);
		}
		content.setRefId(refContentId);
		return saveOrassignContentId(content);
	}

	@Override
	public Content assignContent(Content content) {
		Long cid = this.assignContentId(content);
		return this.getContentById(cid);
	}

	@Override
	public List<Long> findContentIdByPostContentDto(PostWeiboContentDto dto) {
		return this.contentMassDataDao.queryPostContentIds(dto);
	}

	@Override
	public int getContentLength(String content) {
		if (StringUtils.isEmpty(content)) {
			return 0;
		}
		return content.length();
	}

	@Override
	public int saveContentKeyword(Long contentId, Long... keywords) {
		Assert.notNull(contentId, "contentId is null");
		Assert.notNull(keywords, "keywordId is null");
		return this.contentMassDataDao.upsertContentKeyword(contentId, keywords);
	}

	@Override
	public PageResult<Content> findContentPageResult(ContentQueryParamsDto param) {
		Limit lit = param.getLit();
		List<Content> cList = this.contentMassDataDao.getContentLibrary(param);
		int cnt = this.contentMassDataDao.countContentLibraryRows(param);
		PageResult<Content> pr = new PageResult<Content>(cList, lit, cnt);
		return pr;
	}

	@Override
	public List<ContentKeywordInfoDto> findKeywordByContentId(Long contentId) {
		List<Long> keywordList = this.contentMassDataDao.selectContentKeywords(contentId);
		if (!CollectionUtils.isEmpty(keywordList)) {
			return this.keywordDao.selectContentKeywordByKids(keywordList);
		}
		return Collections.emptyList();
	}

	@Override
	public List<Map<String, Long>> findContentByPostContentDto(PostWeiboContentDto dto) {
		return this.contentMassDataDao.queryPostContents(dto);
	}
}