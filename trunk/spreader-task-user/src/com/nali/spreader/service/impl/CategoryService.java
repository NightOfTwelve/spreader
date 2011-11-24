package com.nali.spreader.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.nali.spreader.dao.ICrudCategoryDao;
import com.nali.spreader.data.Category;
import com.nali.spreader.data.CategoryExample;
import com.nali.spreader.service.ICategoryService;

@Service
public class CategoryService implements ICategoryService {
	@Autowired
	private ICrudCategoryDao crudCategoryDao;

	@Override
	public Long getIdByName(String name) {
		return getCategoryByName(name).getId();
	}

	@Override
	public Category getCategoryByName(String name) {
		CategoryExample example = new CategoryExample();
		example.createCriteria().andNameEqualTo(name);
		List<Category> rlt = crudCategoryDao.selectByExample(example);
		if(rlt.size()!=0) {
			return rlt.get(0);
		} else {
			Category record = new Category();
			record.setName(name);
			try {
				crudCategoryDao.insertSelective(record);
			} catch (DuplicateKeyException e) {
			}
			return getCategoryByName(name);
		}
	}

}
