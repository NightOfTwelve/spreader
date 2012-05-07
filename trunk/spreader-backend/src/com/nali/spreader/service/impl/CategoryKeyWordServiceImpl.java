package com.nali.spreader.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nali.common.pagination.PageResult;
import com.nali.spreader.config.KeywordInfoQueryDto;
import com.nali.spreader.config.KeywordQueryParamsDto;
import com.nali.spreader.dao.ICrudKeywordDao;
import com.nali.spreader.dao.IKeywordDao;
import com.nali.spreader.dao.IUserTagDao;
import com.nali.spreader.data.Keyword;
import com.nali.spreader.data.KeywordExample;
import com.nali.spreader.data.KeywordExample.Criteria;
import com.nali.spreader.data.UserTag;
import com.nali.spreader.service.ICategoryKeyWordService;

/**
 * 分类与关键字实现类
 * 
 * @author xiefei
 * 
 */
@Service
public class CategoryKeyWordServiceImpl implements ICategoryKeyWordService {
	@Autowired
	private IKeywordDao keywordDao;
	@Autowired
	private ICrudKeywordDao curdKeywordDao;
	@Autowired
	private IUserTagDao userTagDao;
	private static final Boolean isManual = true;
	private static final Logger logger = Logger.getLogger(CategoryKeyWordServiceImpl.class);

	@Override
	public PageResult<KeywordInfoQueryDto> findKeywordByParams(KeywordQueryParamsDto param) {
		if (param == null) {
			throw new IllegalArgumentException("传入查询参数为null");
		} else {
			List<KeywordInfoQueryDto> list = this.keywordDao.getKeywordInfoQueryDtoList(param);
			int count = this.keywordDao.countKeywordInfoQueryDto(param);
			return new PageResult<KeywordInfoQueryDto>(list, param.getLit(), count);
		}
	}

	@Override
	public void createKeyword(String keywordName, Long categoryId) {
		Keyword kw = new Keyword();
		kw.setName(keywordName);
		kw.setTag(isManual);
		// if (categoryId == null) {
		// Long keyId = this.keywordDao.insertKeyword(kw);
		// kw.setId(keyId);
		// kw.setCategoryId(keyId);
		// kw.setCreateTime(new Date());
		// curdKeywordDao.insertSelective(kw);
		// } else {
		kw.setCategoryId(categoryId);
		kw.setCreateTime(new Date());
		this.curdKeywordDao.insertSelective(kw);
		// }
	}

	@Override
	public boolean checkKeywordNameIsPresence(String keywordName) {
		boolean tag = false;
		if (StringUtils.isNotEmpty(keywordName)) {
			KeywordExample exp = new KeywordExample();
			Criteria c = exp.createCriteria();
			c.andNameEqualTo(keywordName);
			List<Keyword> list = this.curdKeywordDao.selectByExample(exp);
			if (list.size() > 0) {
				tag = true;
			}
		}
		return tag;
	}

	/**
	 * 更新绑定关系
	 * 
	 * @param keywordId
	 * @param categoryId
	 * @return
	 */
	public boolean changeBinding(Long keywordId, Long categoryId) {
		boolean unstat = false;
		if (keywordId != null) {
			Keyword k = new Keyword();
			k.setId(keywordId);
			// categoryId为null表示取消绑定
			k.setCategoryId(categoryId);
			int i = this.keywordDao.updateCategory(k);
			if (i > 0) {
				unstat = true;
			} else {
				logger.debug("关键字取消绑定失败,ID:" + keywordId);
			}
		}
		return unstat;
	}

	@Override
	public int unBinding(Long... keywordIds) {
		int count = 0;
		for (Long keywordId : keywordIds) {
			boolean tag = this.changeBinding(keywordId, null);
			if (tag) {
				count++;
			}
		}
		return count;
	}

	@Override
	public int updateUserTagCategory(Long tagId, Long categoryId) {
		int rownum = 0;
		if (tagId == null) {
			throw new IllegalArgumentException("更新失败,tagId不能为null");
		} else {
			UserTag ut = new UserTag();
			ut.setTagId(tagId);
			ut.setCategoryId(categoryId);
			rownum = this.userTagDao.updateCategoryIdByTagId(ut);
		}
		return rownum;
	}

	@Override
	public int updateKeywordExecutable(Long keywordId, boolean status) {
		int rownum = 0;
		if (keywordId == null) {
			throw new IllegalArgumentException("更新失败,keywordId不能为null");
		} else {
			Keyword k = new Keyword();
			k.setId(keywordId);
			k.setExecutable(status);
			rownum = this.keywordDao.updateKeywordStatus(k);
		}
		return rownum;
	}

	@Override
	public boolean checkKeywordUpdateStatus(Long keywordId) {
		Keyword k = this.curdKeywordDao.selectByPrimaryKey(keywordId);
		if (k != null) {
			Boolean executable = k.getExecutable();
			return Boolean.TRUE.equals(executable);
		} else {
			return false;
		}
	}

	public static void main(String... arge) {
		System.out.println(Boolean.TRUE.equals(null));
	}
}
