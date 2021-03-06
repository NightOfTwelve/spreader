package com.nali.spreader.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nali.common.model.Limit;
import com.nali.common.pagination.PageResult;
import com.nali.spreader.dao.ICrudPhotoDao;
import com.nali.spreader.data.Photo;
import com.nali.spreader.data.PhotoExample;
import com.nali.spreader.data.PhotoExample.Criteria;
import com.nali.spreader.service.IPhotoLibraryService;
import com.nali.spreader.utils.PhotoHelper;

@Service
public class PhotoLibraryServiceImpl implements IPhotoLibraryService {
	@Autowired
	private ICrudPhotoDao crudPhotoDao;

	@Override
	public PageResult<Photo> findPhotoLibraryList(String picType, Boolean avatarflg,
			Boolean photolibflg, Limit lit) {
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
		pe.setLimit(lit);
		List<Photo> list = crudPhotoDao.selectByExampleWithoutBLOBs(pe);
		if (list.size() > 0) {
			String serviceUri = getFileServiceUrl(PhotoHelper.WEBDAV_FILE);
			if (StringUtils.isNotEmpty(serviceUri)) {
				for (Photo p : list) {
					StringBuffer turl = new StringBuffer(serviceUri);
					turl.append(p.getPicUrl());
					p.setPicUrl(turl.toString());
				}
			}
		}
		int count = crudPhotoDao.countByExample(pe);
		return new PageResult<Photo>(list, lit, count);
	}

	/**
	 * 处理服务器的URL
	 * 
	 * @param uri
	 * @return
	 */
	private String getFileServiceUrl(String uri) {
		String u = null;
		if (StringUtils.isNotEmpty(uri)) {
			Map<Object, Object> map = PhotoHelper.getPropertiesMap(uri);
			String tu = map.get("url").toString();
			if (StringUtils.isNotEmpty(tu)) {
				u = tu.substring(0, tu.length() - 1);
			}
		}
		return u;
	}
}
