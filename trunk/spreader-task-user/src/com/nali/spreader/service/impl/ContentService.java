package com.nali.spreader.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nali.spreader.config.ContentDto;
import com.nali.spreader.dao.ICrudContentDao;
import com.nali.spreader.dao.ICrudUserDao;
import com.nali.spreader.dao.ICrudUserTagDao;
import com.nali.spreader.dao.IUserDao;
import com.nali.spreader.data.Content;
import com.nali.spreader.data.ContentExample;
import com.nali.spreader.data.User;
import com.nali.spreader.data.UserExample;
import com.nali.spreader.data.UserTag;
import com.nali.spreader.data.UserTagExample;
import com.nali.spreader.service.IContentService;

@Service
public class ContentService implements IContentService {
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

	@Override
	public Content getMatchedContent(Long uid) {
		UserTagExample example = new UserTagExample();
		example.createCriteria().andUidEqualTo(uid);
		List<UserTag> userTags = crudUserTagDao.selectByExample(example);
		Collections.shuffle(userTags);
		Long contentId = null;
		for (UserTag userTag : userTags) {
			contentId = contentCategoryMatch.popContentId(userTag.getCategoryId());
			if(contentId!=null) {
				break;
			}
		}
		if(contentId==null) {
			contentId = contentCategoryMatch.popContentId(DEFAULT_CATEGORY_ID);
			if(contentId==null) {
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
		checkNotNull(content.getWebsiteId());
		checkNotNull(content.getWebsiteContentId());
		checkNotNull(content.getType());
		Long existId = findByUniqueKey(content.getType(), content.getWebsiteId(), content.getWebsiteContentId());
		if(existId!=null) {
			return;
		}
		if(content.getUid()==null) {
			UserExample example = new UserExample();
			example.createCriteria()
				.andWebsiteIdEqualTo(content.getWebsiteId())
				.andWebsiteUidEqualTo(content.getWebsiteUid());
			List<User> users = crudUserDao.selectByExample(example);
			if(users.size()==0) {
				logger.error("user not exists websiteId,websiteUid:" + content.getWebsiteId() + "," + content.getWebsiteUid());
				return;
			}
			content.setUid(users.get(0).getId());
		}
		List<Long> categoryIds;
		UserTagExample example = new UserTagExample();
		example.createCriteria().andUidEqualTo(content.getUid());
		List<UserTag> userTags = crudUserTagDao.selectByExample(example);
		if(userTags.size()==0) {
			categoryIds = Arrays.asList(DEFAULT_CATEGORY_ID);
		} else {
			categoryIds = new ArrayList<Long>(userTags.size());
			for (UserTag userTag : userTags) {
				categoryIds.add(userTag.getCategoryId());
			}
		}
		Long contentId = userDao.insertContent(content);
		contentCategoryMatch.registerContentId(contentId, categoryIds);
	}

	private Long findByUniqueKey(Integer type, Integer websiteId, Long websiteContentId) {
		ContentExample example = new ContentExample();
		example.createCriteria().andTypeEqualTo(type).andWebsiteIdEqualTo(websiteId).andWebsiteContentIdEqualTo(websiteContentId);
		List<Content> contents = crudContentDao.selectByExampleWithoutBLOBs(example);
		if(contents.size()==0) {
			return null;
		}
		return contents.get(0).getId();
	}

	private void checkNotNull(Object param) {
		if(param==null) {
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

}
