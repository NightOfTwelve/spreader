package com.nali.spreader.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Component;

@Component
public class ContentCategoryMatch {//TODO 加同步。。
	private static final String CATEGORY_KEY_PREFIX = "CategoryContent_";
	private static final String CONTENT_KEY_PREFIX = "ContentCategory_";
	@Autowired
    private RedisTemplate<String, Long> redisTemplate;
	
	public void registerContentId(Long contentId, List<Long> categoryIds) {
		SetOperations<String, Long> op = redisTemplate.opsForSet();
		for (Long categoryId : categoryIds) {
			op.add(categoryKey(categoryId), contentId);
			op.add(contentKey(contentId), categoryId);
		}
	}
	
	public Long popContentId(Long categoryId) {
		SetOperations<String, Long> op = redisTemplate.opsForSet();
		Long contentId = op.pop(categoryKey(categoryId));
		if(contentId==null) {
			return null;
		}
		Set<Long> categoryIds = op.members(contentKey(contentId));
		for (Long cId : categoryIds) {
			op.remove(categoryKey(cId), contentId);
		}
		redisTemplate.delete(Arrays.asList(contentKey(contentId)));
		return contentId;
	}

	private String categoryKey(Long categoryId) {
		return CATEGORY_KEY_PREFIX + categoryId;
	}

	private String contentKey(Long contentId) {
		return CONTENT_KEY_PREFIX + contentId;
	}
}
