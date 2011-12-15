package com.nali.spreader.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nali.common.model.Limit;
import com.nali.common.pagination.PageResult;
import com.nali.spreader.dao.ICrudPhotoDao;
import com.nali.spreader.dao.IPhotoDao;
import com.nali.spreader.data.Photo;
import com.nali.spreader.data.PhotoExample;
import com.nali.spreader.data.PhotoExample.Criteria;
import com.nali.spreader.service.IPhotoLibraryService;

@Service
public class PhotoLibraryServiceImpl implements IPhotoLibraryService {
	@Autowired
	private IPhotoDao photoDao;
	@Autowired
	private ICrudPhotoDao crudPhotoDao;

	@Override
	public PageResult<Photo> findPhotoLibraryList(String picType,
			Boolean avatarflg, Boolean photolibflg, Integer page,
			Integer pageSize) {
		PhotoExample pe = new PhotoExample();
		Criteria cr = pe.createCriteria();
		if (StringUtils.isNotEmpty(picType)) {
			cr.andPicTypeEqualTo(picType);
		}
		if (avatarflg != null) {
			cr.andAvatarflgEqualTo(avatarflg);
		}
		if (photolibflg != null) {
			cr.andPhotolibflgEqualTo(photolibflg);
		}
		Limit limit = Limit.newInstanceForLimit(page, pageSize);
		pe.setLimit(limit);
		List<Photo> list = crudPhotoDao.selectByExampleWithoutBLOBs(pe);
		int count = crudPhotoDao.countByExample(pe);
		return new PageResult<Photo>(list, limit, count);
	}

}
