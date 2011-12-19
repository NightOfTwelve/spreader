package com.nali.spreader.service;

import com.nali.common.pagination.PageResult;
import com.nali.spreader.data.Photo;

public interface IPhotoLibraryService {
	/**
	 * 查询出图片库的列表
	 * 
	 * @param picType
	 * @param avatarflg
	 * @param photolibflg
	 * @param page
	 * @param pageSize
	 * @return
	 */
	PageResult<Photo> findPhotoLibraryList(String picType, Boolean avatarflg,
			Boolean photolibflg, Integer page, Integer pageSize);
}
