package com.nali.spreader.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.nali.spreader.config.ContentDto;
import com.nali.spreader.config.KeywordQueryParamsDto;
import com.nali.spreader.constants.Website;
import com.nali.spreader.dao.ICrudContentDao;
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
	private ICrudContentDao crudContentDao;
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
		return crudContentDao.selectByPrimaryKey(contentId);
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
				Content record = new Content();
				record.setId(exist.getId());
				record.setWebsiteContentId(content.getWebsiteContentId());
				crudContentDao.updateByPrimaryKeySelective(record);
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
		Long contentId = userDao.insertContent(content);
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
		List<Content> contents = crudContentDao.selectByExampleWithoutBLOBs(example);
		if (contents.size() == 0) {
			return null;
		}
		return contents.get(0);
	}

	private void checkNotNull(Object param) {
		if (param == null) {
			throw new IllegalArgumentException("property is null");
		}
	}

	@Override
	public Content getContentById(Long contentId) {
		return crudContentDao.selectByPrimaryKey(contentId);
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
		try {
			crudContentDao.insertSelective(content);
		} catch (DuplicateKeyException e) {
			logger.info("double insert.");
		}
		return assignContent(websiteId, websiteUid, entry);
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
}
