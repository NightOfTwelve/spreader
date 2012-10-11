package com.nali.spreader.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.nali.common.pagination.PageResult;
import com.nali.spreader.config.KeywordInfoQueryDto;
import com.nali.spreader.config.KeywordQueryParamsDto;
import com.nali.spreader.dao.ICrudCategoryDao;
import com.nali.spreader.dao.ICrudKeywordDao;
import com.nali.spreader.dao.IKeywordDao;
import com.nali.spreader.data.Category;
import com.nali.spreader.data.CategoryExample;
import com.nali.spreader.data.Keyword;
import com.nali.spreader.data.KeywordExample;
import com.nali.spreader.data.KeywordExample.Criteria;
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
	private ICrudCategoryDao crudCategoryDao;
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
	public void createKeyword(Keyword kw) {
		if (kw == null) {
			throw new IllegalArgumentException("创建关键字传入参数为null");
		} else {
			if (StringUtils.isBlank(kw.getName())) {
				throw new IllegalArgumentException("关键字名称为null");
			}
			String keyword = kw.getName();
			// 去掉前后空格
			kw.setName(StringUtils.strip(keyword));
			// 手工创建
			kw.setTag(isManual);
			kw.setCreateTime(new Date());
			this.curdKeywordDao.insertSelective(kw);
		}
	}

	@Override
	public boolean checkKeywordNameIsPresence(String keywordName) {
		boolean tag = false;
		if (StringUtils.isNotBlank(keywordName)) {
			KeywordExample exp = new KeywordExample();
			Criteria c = exp.createCriteria();
			c.andNameEqualTo(StringUtils.strip(keywordName));
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
			// categoryId为-1L表示无需绑定
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

	@Override
	public PageResult<Category> findCategoryPageData(KeywordQueryParamsDto param) {
		CategoryExample exp = new CategoryExample();
		com.nali.spreader.data.CategoryExample.Criteria c = exp.createCriteria();
		String categoryName = param.getCategoryName();
		Long categoryId = param.getCategoryId();
		if (StringUtils.isNotEmpty(categoryName)) {
			c.andNameEqualTo(categoryName);
		}
		if (categoryId != null) {
			c.andIdEqualTo(categoryId);
		}
		exp.setLimit(param.getLit());
		exp.setOrderByClause("id desc");
		List<Category> list = this.crudCategoryDao.selectByExample(exp);
		int count = this.crudCategoryDao.countByExample(exp);
		return new PageResult<Category>(list, param.getLit(), count);
	}

	@Override
	public PageResult<KeywordInfoQueryDto> findKeywordByNotEqualParams(KeywordQueryParamsDto param) {
		if (param == null) {
			throw new IllegalArgumentException("参数为null");
		} else {
			List<KeywordInfoQueryDto> list = this.keywordDao
					.getKeywordInfoQueryDtoListIsNotEqualCategoryId(param);
			int count = this.keywordDao.countKeywordInfoQueryDtoIsNotEqualCategoryId(param);
			return new PageResult<KeywordInfoQueryDto>(list, param.getLit(), count);
		}
	}

	@Override
	public void createCategory(Category c) {
		if (c.getId() == null) {
			this.crudCategoryDao.insertSelective(c);
		} else {
			this.crudCategoryDao.updateByPrimaryKeySelective(c);
		}
	}

	@Override
	public String keywordAndCategoryRollBackInfo(Long keywordId, Long oldCategoryId) {
		String log = "<------关键字取消绑定回滚信息";
		StringBuffer buff = new StringBuffer(log);
		buff.append("关键字编号:").append(keywordId);
		// int userTagRows = this.updateUserTagCategory(keywordId,
		// oldCategoryId);
		// buff.append(";tb_user_tag表回滚:").append(userTagRows).append("条数据;")
		buff.append(",tb_keyword表回滚状态:");
		boolean isSuccess = this.changeBinding(keywordId, oldCategoryId);
		buff.append(isSuccess);
		buff.append(",信息结束------>");
		return buff.toString();
	}

	@Override
	public int updateKeywordExecutableByRollback(Long keywordId) {
		Keyword k = new Keyword();
		k.setId(keywordId);
		k.setExecutable(true);
		return this.curdKeywordDao.updateByPrimaryKeySelective(k);
	}

	@Override
	public boolean checkCategoryName(String name) {
		CategoryExample exp = new CategoryExample();
		com.nali.spreader.data.CategoryExample.Criteria c = exp.createCriteria();
		c.andNameEqualTo(name);
		List<Category> list = this.crudCategoryDao.selectByExample(exp);
		return list.size() > 0;
	}

	@Override
	public int updateCategory(Category category) {
		Assert.notNull(category, "category is null");
		Long categoryId = category.getId();
		Assert.notNull(categoryId, "categoryId is null");
		String categoryName = category.getName();
		Assert.notNull(categoryName, "categoryName is null");
		return this.crudCategoryDao.updateByPrimaryKeySelective(category);
	}

	@Override
	public int deleteCategory(Long... ids) {
		int rows = 0;
		for (Long id : ids) {
			try {
				this.crudCategoryDao.deleteByPrimaryKey(id);
				this.keywordDao.cleanKeywordCategory(id);
				rows++;
			} catch (Exception e) {
				logger.error(e);
			}
		}
		return rows;
	}

	@Override
	public int cancelNoCategoryStatus(Long keywordId) {
		Assert.notNull(keywordId, "keywordId is null");
		return this.keywordDao.clearCategoryByKeywordId(keywordId);
	}
}