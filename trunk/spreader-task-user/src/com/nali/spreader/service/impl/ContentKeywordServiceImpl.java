package com.nali.spreader.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.nali.spreader.dao.IContentKeywordDao;
import com.nali.spreader.data.ContentKeyword;
import com.nali.spreader.service.IContentKeywordService;

@Service
public class ContentKeywordServiceImpl implements IContentKeywordService {
	@Autowired
	private IContentKeywordDao contentKeywordDao;

	@Override
	public Long getOrAssignContentKeywordId(Long contentId, Long keywordId) {
		ContentKeyword param = new ContentKeyword();
		param.setContentId(contentId);
		param.setKeywordId(keywordId);
		ContentKeyword ck = this.contentKeywordDao.getContentKeywordByUk(param);
		if (ck != null) {
			return ck.getId();
		} else {
			try {
				return this.contentKeywordDao.insertContentKeyword(param);
			} catch (DuplicateKeyException e) {
				return getOrAssignContentKeywordId(contentId, keywordId);
			}
		}
	}
}
